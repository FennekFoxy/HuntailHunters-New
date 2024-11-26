package com.fennekfoxy.huntailhunters.events;

import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerShotEvent implements Listener {

    private final GameManager gameManager;

    public PlayerShotEvent(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    //Check while an active game is going and the player is shot with an arrow while in the correct arena
    //if all checks are successfull sets the players health to 0 and removes them from the current players queue
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

