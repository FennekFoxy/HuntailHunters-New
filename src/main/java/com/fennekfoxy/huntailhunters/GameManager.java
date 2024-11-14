package com.fennekfoxy.huntailhunters;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class GameManager {

    private static boolean activeGame = false;
    private static ArrayList<Player> gameQueue = new ArrayList<>();
    private static ArrayList<Player> playedGame = new ArrayList<>();

    public boolean isActiveGame(){
        return activeGame;
    }
    public void setActiveGame(boolean activeGame){
        GameManager.activeGame = activeGame;
    }

    public void addPlayerToQueue(Player player){
        gameQueue.add(player);
    }
    public void removePlayerFromQueue(Player player){
        gameQueue.remove(player);
        playedGame.add(player);
    }
    public boolean isPlayerInQueue(Player player){
        return gameQueue.contains(player);
    }
    public boolean isPlayerInPlayed(Player player){
        return playedGame.contains(player);
    }
    public int getQueueSize(){
        return gameQueue.size();
    }
    public int getPlayedSize(){
        return playedGame.size();
    }
    public ArrayList<Player> getGameQueue() {
        return new ArrayList<>(gameQueue);
    }
    public ArrayList<Player> getPlayedGame() {
        return new ArrayList<>(playedGame);
    }
    public void announceMessage(String message) {
        Bukkit.broadcastMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', message));
    }
    public void cleanUpInventory(Player player){
        Inventory inventory = player.getPlayer().getInventory();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");

        for(ItemStack item : inventory.getContents()){
            if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)){
                PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
                int itemId = container.get(key,PersistentDataType.INTEGER);

                if (itemId == 1 || itemId == 2 || itemId == 3 || itemId == 4){
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

    //at the end of each round update player stats of the winner
}
