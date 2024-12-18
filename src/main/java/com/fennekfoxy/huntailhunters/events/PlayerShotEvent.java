package com.fennekfoxy.huntailhunters.events;

import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerShotEvent implements Listener {

    private final GameManager gameManager;

    public PlayerShotEvent(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @EventHandler
    public void onPlayerShot(EntityDamageByEntityEvent e) {
        if (gameManager.isActiveGame()) {
            Player player = (Player) e.getEntity();
            if (gameManager.isPlayerInQueue(player)) {
                if (e.getDamager() instanceof Projectile projectile) {
                    if (projectile.getType() == EntityType.ARROW) {
                        if (gameManager.isPlayerInArena(player, gameManager.getActiveArena())) {
                            player.setHealth(0);
                            gameManager.removePlayerFromQueue(player);
                        }
                    }
                }
            }
        }
    }
}

