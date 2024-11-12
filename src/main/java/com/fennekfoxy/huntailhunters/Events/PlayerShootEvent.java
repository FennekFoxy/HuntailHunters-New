package com.fennekfoxy.huntailhunters.Events;

import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

public class PlayerShootEvent implements Listener {

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
}
