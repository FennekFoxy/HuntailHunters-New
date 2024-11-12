package com.fennekfoxy.huntailhunters.Events;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerShotEvent implements Listener {
    @EventHandler
    public void onPlayerShot(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Projectile) {
            Projectile projectile = (Projectile) e.getDamager();
            if (projectile.getType() == EntityType.ARROW) {
                Player player = (Player) e.getEntity();
                player.setHealth(0);
            }
        }
    }
}
