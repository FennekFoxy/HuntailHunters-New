package com.fennekfoxy.huntailhunters.events;

import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerShootEvent implements Listener {

    private final GameManager gameManager;

    public PlayerShootEvent(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerShoot(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player player) {
            if (gameManager.isActiveGame()) {
                if (gameManager.isPlayerInArena(player, gameManager.getActiveArena())) {
                    ItemStack item = e.getConsumable();
                    ItemMeta meta = item.getItemMeta();
                    NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");
                    PersistentDataContainer container = meta.getPersistentDataContainer();
                    if (container.has(key, PersistentDataType.INTEGER)) {
                        int foundValue = container.get(key, PersistentDataType.INTEGER);
                        if (foundValue == 2) {
                            if (e.getProjectile() instanceof Arrow arrow) {
                                PersistentDataContainer arrowContainer = arrow.getPersistentDataContainer();
                                arrowContainer.set(key, PersistentDataType.INTEGER, 2);
                                ((Arrow) e.getProjectile()).setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
                            } else {
                                e.setCancelled(true);
                                player.sendMessage(ChatColor.RED + "You can only use the special event arrows!");
                            }
                        }
                    }
                }
            }
        }
    }
}
