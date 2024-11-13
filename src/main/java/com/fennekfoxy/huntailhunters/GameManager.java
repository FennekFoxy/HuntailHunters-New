package com.fennekfoxy.huntailhunters;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<Player> gameQueue = new ArrayList<Player>();

    public void addPlayerToQueue(Player player){
        gameQueue.add(player);
    }
    public void removePlayerFromQueue(Player player){
        gameQueue.remove(player);
    }
    public int getQueueSize(){
        return gameQueue.size();
    }
    public ArrayList<Player> getGameQueue() {
        return new ArrayList<>(gameQueue);
    }
}
