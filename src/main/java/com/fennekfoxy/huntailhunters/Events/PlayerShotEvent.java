package com.fennekfoxy.huntailhunters.Events;

import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerShotEvent implements Listener {
    private GameManager gameManager;

    public GameManager getGameManager() {
        return gameManager;
    }

    @EventHandler
    public void onPlayerShot(EntityDamageByEntityEvent e) {
        if (gameManager.isActiveGame()) {
            if (e.getDamager() instanceof Projectile) {
                Projectile projectile = (Projectile) e.getDamager();
                if (projectile.getType() == EntityType.ARROW) {
                    ItemStack item = new ItemStack(Material.ARROW, 1);
                    ItemMeta meta = item.getItemMeta();
                    NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");
                    PersistentDataContainer container = meta.getPersistentDataContainer();
                    if (container.has(key, PersistentDataType.INTEGER)){
                    int foundValue = container.get(key, PersistentDataType.INTEGER);
                        if (foundValue == 2) {
                            Player player = (Player) e.getEntity();
                            player.setHealth(0);
                            gameManager.removePlayerFromQueue(player);
                        }
                    }
                }
            }
        }
    }
}
