package com.fennekfoxy.huntailhunters;


import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class GameEvents implements Listener {

    @EventHandler
    public void onPlayerShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player /*&& is in the joined players queue*/) {
            Player player = (Player) e.getEntity();
            if (/*activeGame && Player is in the arena*/1 == 1) {
                //check item data of arrow
                if (/*!Event Arrow*/ 1 == 1) {
                    e.setCancelled(true);
                    player.sendMessage("§cYou can only use the special event arrows!");
                } else {
                    if (e.getProjectile() instanceof Arrow) {
                        ((Arrow) e.getProjectile()).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                    }
                }
            }
        }
    }

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

    /*@EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent e) {

    }*/

    /*@EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Player killer = killed.getKiller();

        if ( check if theres an active game && if both players are in the arena) {
            e.setDeathMessage(null);
        }

        if (killer != null && active game && joinedplayers size != /){
            if( killed with the proper weapons){
                //give killer a new event arrow
            }
        }
    }*/

}
