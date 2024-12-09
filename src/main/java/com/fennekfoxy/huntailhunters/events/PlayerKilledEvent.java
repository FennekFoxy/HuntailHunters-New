package com.fennekfoxy.huntailhunters.events;

import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.database.Database;
import com.fennekfoxy.huntailhunters.database.PlayerStats;
import com.fennekfoxy.huntailhunters.GameItems;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;
import java.util.logging.Level;

public class PlayerKilledEvent implements Listener {

    private final GameManager gameManager;
    private final Database database;

    public PlayerKilledEvent(GameManager gameManager, Database database) {
        this.gameManager = gameManager;
        this.database = database;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Player killer = killed.getKiller();

        if (gameManager.isActiveGame()) {
            if (killer != null && gameManager.isPlayerInArena(killed, gameManager.getActiveArena()) && gameManager.isPlayerInArena(killer, gameManager.getActiveArena())) {
                e.setDeathMessage(null);
                gameManager.removePlayerFromQueue(killed);
                gameManager.cleanUpInventory(killed);
                if (gameManager.getQueueSize() != 1) {
                    ItemStack weapon = killer.getInventory().getItemInMainHand();
                    ItemMeta meta = weapon.getItemMeta();
                    NamespacedKey key = GameItems.getItemIdKey();
                    PersistentDataContainer container = meta.getPersistentDataContainer();
                    if (container.has(key, PersistentDataType.INTEGER)) {
                        double foundValue = container.get(key, PersistentDataType.INTEGER);
                        if (foundValue == 1 || foundValue == 3) {
                            ItemStack arrow = GameItems.newArrow();
                            killer.getInventory().addItem(arrow);
                        }
                    }
                } else if (gameManager.getQueueSize() == 1) {
                    String worldName = HuntailHunters.getPlugin().getConfig().getString("Hub.world");
                    double x = HuntailHunters.getPlugin().getConfig().getInt("Hub.x");
                    double y = HuntailHunters.getPlugin().getConfig().getInt("Hub.y");
                    double z = HuntailHunters.getPlugin().getConfig().getInt("Hub.z");
                    World world = Bukkit.getWorld(worldName);
                    Location hub = new Location(world, x, y, z);
                    killer.teleport(hub);
                    gameManager.setActiveGame(false);
                    Set<Player> queue = gameManager.getGameQueue();
                    Set<Player> played = gameManager.getPlayedGame();
                    for (Player p : queue) {
                        gameManager.cleanUpInventory(p);
                    }
                    for (Player p : played) {
                        gameManager.cleanUpInventory(p);
                        gameManager.addPlayerToQueue(p);
                    }
                    String winner = MessagesConfig.get().getString("winner_announcement");
                    winner = winner.replace("{winner}", killer.getDisplayName());
                            database.findPlayerStatsByUUID(killer.getUniqueId().toString())
                            .thenAcceptAsync(stats -> {
                                if (stats == null) {
                                    PlayerStats newStats = new PlayerStats(killer.getUniqueId().toString(), killer.getName(), 0);
                                    newStats.setWins(1);
                                    database.insertPlayerStats(newStats);
                                } else {
                                    stats.setWins(stats.getWins() + 1);
                                    database.updatePlayerStats(stats);
                                }
                            }).exceptionally(ex -> {
                                // Log errors during the database operation
                                HuntailHunters.getPlugin().getLogger().log(Level.SEVERE,
                                        "An error occurred while updating stats for player: " + killer.getName(), ex);
                                return null;
                            });

                    gameManager.announceMessage(winner);
                }
            }
        }
    }
}