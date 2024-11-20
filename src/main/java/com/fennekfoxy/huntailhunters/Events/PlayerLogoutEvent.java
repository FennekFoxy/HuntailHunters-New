package com.fennekfoxy.huntailhunters.Events;

import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogoutEvent implements Listener {

    GameManager gameManager = new GameManager();

    @EventHandler
    public void onPlayerLogout (PlayerQuitEvent e){
        if (gameManager.isActiveGame()){
            Player player = e.getPlayer();
            gameManager.removePlayerFromQueue(player);
            gameManager.removePlayerFromPlayed(player);
            gameManager.cleanUpInventory(player);
        }
    }
}
