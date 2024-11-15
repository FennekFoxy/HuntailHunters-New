package com.fennekfoxy.huntailhunters.Events;

import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameItems;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerKilledEvent implements Listener {

    GameManager gameManager = new GameManager();



    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Player killer = killed.getKiller();

        if(gameManager.isActiveGame()){
            if (killer !=null/* and both players are in the arena*/){
                e.setDeathMessage(null);
                if (gameManager.getQueueSize() != 1){
                    ItemStack weapon = killer.getInventory().getItemInMainHand();
                    ItemMeta meta = weapon.getItemMeta();
                    NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");
                    PersistentDataContainer container = meta.getPersistentDataContainer();
                    if (container.has(key, PersistentDataType.INTEGER)){
                        double foundValue = container.get(key, PersistentDataType.INTEGER);
                        if (foundValue == 1 || foundValue == 3) {
                            ItemStack arrow = GameItems.newArrow();
                            killer.getInventory().addItem(arrow);
                        }
                    }else if (gameManager.getQueueSize() == 1)
                        gameManager.setActiveGame(false);
                    String winner = MessagesConfig.get().getString("winner_announcement");
                    winner = winner.replace("{winner}", killer.getDisplayName());
                    gameManager.announceMessage(MessagesConfig.get().getString(winner));
                }
            }
        }
    }
}