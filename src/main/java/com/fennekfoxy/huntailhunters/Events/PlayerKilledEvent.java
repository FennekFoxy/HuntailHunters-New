package com.fennekfoxy.huntailhunters.Events;

import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameItems;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import com.fennekfoxy.huntailhunters.Util.PlayerStats;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.sql.SQLException;
import java.util.HashSet;

public class PlayerKilledEvent implements Listener {

    GameManager gameManager = new GameManager();


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
                    NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");
                    PersistentDataContainer container = meta.getPersistentDataContainer();
                    if (container.has(key, PersistentDataType.INTEGER)) {
                        double foundValue = container.get(key, PersistentDataType.INTEGER);
                        if (foundValue == 1 || foundValue == 3) {
                            ItemStack arrow = GameItems.newArrow();
                            killer.getInventory().addItem(arrow);
                        }
                    }
                } else if (gameManager.getQueueSize() == 1) {
                    gameManager.setActiveGame(false);
                    HashSet<Player> queue = gameManager.getGameQueue();
                    HashSet<Player> played = gameManager.getPlayedGame();
                    for (Player p : queue) {
                        gameManager.cleanUpInventory(p);
                    }
                    for (Player p : played) {
                        gameManager.cleanUpInventory(p);
                        gameManager.addPlayerToQueue(p);
                    }
                    String winner = MessagesConfig.get().getString("winner_announcement");
                    winner = winner.replace("{winner}", killer.getDisplayName());
                    try{
                        PlayerStats stats = HuntailHunters.getPlugin().getDatabase().findPlayerStatsByUUID(killer.getUniqueId().toString());
                        if (stats == null){
                            stats = new PlayerStats(killer.getUniqueId().toString(), killer.getName(), 0);

                            stats.setWins(stats.getWins() + 1);
                            HuntailHunters.getPlugin().getDatabase().insertPlayerStats(stats);
                        }else{
                            stats.setWins(stats.getWins() + 1);
                            HuntailHunters.getPlugin().getDatabase().updatePlayerStats(stats);
                        }
                    }catch (SQLException ex){
                        ex.printStackTrace();
                    }
                    gameManager.announceMessage(winner);
                }
            }
        }
    }
}