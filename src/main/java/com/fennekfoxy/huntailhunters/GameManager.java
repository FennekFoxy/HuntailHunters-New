package com.fennekfoxy.huntailhunters;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    private boolean activeGame = false;
    private ArrayList<Player> gameQueue = new ArrayList<Player>();

    public boolean isActiveGame(){
        return activeGame;
    }
    public void setActiveGame(boolean activeGame){
        this.activeGame = activeGame;
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
}
