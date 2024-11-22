package com.fennekfoxy.huntailhunters.events;

import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLogoutEvent implements Listener {

    private final GameManager gameManager;

    public PlayerLogoutEvent(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent e) {
        if (gameManager.isActiveGame()) {
            Player player = e.getPlayer();
            gameManager.removePlayerFromQueue(player);
            gameManager.removePlayerFromPlayed(player);
            gameManager.cleanUpInventory(player);
        }
    }
}
