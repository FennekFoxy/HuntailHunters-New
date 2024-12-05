package com.fennekfoxy.huntailhunters;

import com.fennekfoxy.huntailhunters.configs.ArenasConfig;
import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.database.PlayerStatsService;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class GameManager {

    private static boolean activeGame = false;
    private static String activeArena = null;
    public int taskId = -1;
    Random random = new Random();
    private final Set<Player> gameQueue = new HashSet<>();
    private final Set<Player> playedGame = new HashSet<>();
    private final PlayerStatsService playerStatsService;

    public GameManager(PlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    public boolean isActiveGame() {
        return activeGame;
    }

    public void setActiveGame(boolean activeGame) {
        GameManager.activeGame = activeGame;
    }

    public String getActiveArena() {
        return activeArena;
    }

    public void setActiveArena(String activeArena) {
        GameManager.activeArena = activeArena;
    }

    public void addPlayerToQueue(Player player) {
        gameQueue.add(player);
    }

    public void removePlayerFromQueue(Player player) {
        gameQueue.remove(player);
        playedGame.add(player);
    }

    public void removePlayerFromPlayed(Player player) {
        playedGame.remove(player);
    }

    public void removePlayerFromGame(Player player) {
        gameQueue.remove(player);
    }

    public boolean isPlayerInQueue(Player player) {
        return gameQueue.contains(player);
    }

    public int getQueueSize() {
        return gameQueue.size();
    }

    public Set<Player> getGameQueue() {
        return new HashSet<>(gameQueue);
    }

    public Set<Player> getPlayedGame() {
        return new HashSet<>(playedGame);
    }

    public void announceMessage(String message) {
        Bukkit.broadcastMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', message));
    }

    public void cleanUpInventory(Player player) {
        Inventory inventory = player.getPlayer().getInventory();
        NamespacedKey key = GameItems.getItemIdKey();

        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
                int itemId = container.get(key, PersistentDataType.INTEGER);

                if (itemId == 1 || itemId == 2 || itemId == 3 || itemId == 4) {
                    inventory.remove(item);
                }
            }
        }
    }

    public int countEmptySlots(Player player) {
        int emptySlots = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null || item.getType().isAir()) {
                emptySlots++;
            }
        }
        return emptySlots;
    }

    public void spawnPowerups(String name, Player player) {
        List<Location> powerupLocations = new ArrayList<>();
        if (ArenasConfig.get().contains(name + ".PowerUps")) {
            World world = Bukkit.getWorld(ArenasConfig.get().getString(name + ".World", "world"));

            if (world == null) {
                player.sendMessage(ChatColor.RED + "World not found: " + ArenasConfig.get().getString(name + ".World"));
                return;
            }

            for (String key : ArenasConfig.get().getConfigurationSection(name + ".PowerUps").getKeys(false)) {
                String locString = ArenasConfig.get().getString(name + ".PowerUps." + key);
                if (locString != null) {
                    String[] parts = locString.split(" ");
                    try {
                        double x = Double.parseDouble(parts[0]);
                        double y = Double.parseDouble(parts[1]);
                        double z = Double.parseDouble(parts[2]);
                        powerupLocations.add(new Location(world, x, y, z));
                    } catch (NumberFormatException e) {
                        player.sendMessage("Invalid coordinates defined in config");
                    }
                }
            }
        }
        if (powerupLocations.isEmpty()) {
            player.sendMessage(ChatColor.RED + "No PowerUp Locations available in that arena");
        } else {
            taskId = Bukkit.getScheduler().runTaskTimer(HuntailHunters.getPlugin(), () -> {
                if (!activeGame) {
                    cancelPowerUpTask();
                }
                int randomIndex = random.nextInt(powerupLocations.size());
                Location randomLocation = powerupLocations.get(randomIndex);
                randomLocation.getWorld().dropItem(randomLocation, GameItems.newPowerUp());

                String powerupSpawned = MessagesConfig.get().getString("power_up_spawned");
                announceMessage(ChatColor.translateAlternateColorCodes('&', powerupSpawned));
            }, 0L, 300L).getTaskId();
        }
    }

    public void cancelPowerUpTask() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }

    public boolean isPlayerInArena(Player player, String name) {

        if (!ArenasConfig.get().contains(name)) {
            return false;
        }
        World world = Bukkit.getWorld(ArenasConfig.get().getString(name + ".World"));
        if (world == null) {
            return false;
        }
        String loc1 = ArenasConfig.get().getString(name + ".Loc1");
        String loc2 = ArenasConfig.get().getString(name + ".Loc2");

        String[] parts1 = loc1.split(" ");
        String[] parts2 = loc2.split(" ");

        double x1 = Double.parseDouble(parts1[0]);
        double y1 = Double.parseDouble(parts1[1]);
        double z1 = Double.parseDouble(parts1[2]);

        double x2 = Double.parseDouble(parts2[0]);
        double y2 = Double.parseDouble(parts2[1]);
        double z2 = Double.parseDouble(parts2[2]);

        double minX = Math.min(x1, x2);
        double maxX = Math.max(x1, x2);
        double minY = Math.min(y1, y2);
        double maxY = Math.max(y1, y2);
        double minZ = Math.min(z1, z2);
        double maxZ = Math.max(z1, z2);

        Location playerLocation = player.getLocation();

        return playerLocation.getWorld().equals(world) && playerLocation.getX() >= minX && playerLocation.getX() <= maxX && playerLocation.getY() >= minY && playerLocation.getY() <= maxY && playerLocation.getZ() >= minZ && playerLocation.getZ() <= maxZ;
    }

    public void showPlayerStats(Player player) {
        playerStatsService.getPlayerStats(player).thenAcceptAsync(stats -> {
            Bukkit.getScheduler().runTask(HuntailHunters.getPlugin(), () -> {
                if (stats == null) {
                    player.sendMessage(ChatColor.RED + "No stats found for " + player.getName());
                } else {
                    String statsMessage = ChatColor.GREEN + stats.getPlayerName() + "'s Stats:\n" + ChatColor.YELLOW + "Wins - " + stats.getWins();
                    player.sendMessage(statsMessage);
                }
            });
        });
    }
}
