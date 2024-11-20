package com.fennekfoxy.huntailhunters.Events;

import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerShotEvent implements Listener {

    GameManager gameManager = new GameManager();

    @EventHandler
    public void onPlayerShot(EntityDamageByEntityEvent e) {
        if (gameManager.isActiveGame()) {
            Player player = (Player) e.getEntity();
            if (gameManager.isPlayerInQueue(player)) {
                if (e.getDamager() instanceof Projectile) {
                    Projectile projectile = (Projectile) e.getDamager();
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

