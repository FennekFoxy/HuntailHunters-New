package com.fennekfoxy.huntailhunters;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    private static boolean activeGame = false;
    private static ArrayList<Player> gameQueue = new ArrayList<Player>();

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
    }
    public boolean isPlayerInQueue(Player player){
        return gameQueue.contains(player);
    }
    public int getQueueSize(){
        return gameQueue.size();
    }
    public ArrayList<Player> getGameQueue() {
        return new ArrayList<>(gameQueue);
    }

    public void announceMessage(String message) {
        Bukkit.broadcastMessage(org.bukkit.ChatColor.translateAlternateColorCodes('&', message));
    }

    //at the end of each round update player stats of the winner
}
