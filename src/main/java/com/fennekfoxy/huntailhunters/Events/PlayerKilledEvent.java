package com.fennekfoxy.huntailhunters.Events;

import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.Material;
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

    private GameManager gameManager;

    public GameManager getGameManager(){
        return gameManager;
    }


    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player killed = e.getEntity();
        Player killer = killed.getKiller();

        if(gameManager.isActiveGame()/* and both players are in the arean*/){
            e.setDeathMessage(null);
        }
            if (killer != null && gameManager.isActiveGame() && gameManager.getQueueSize() != 1){
                ItemStack weapon = killer.getInventory().getItemInMainHand();
                ItemMeta meta = weapon.getItemMeta();
                NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "Custom Event Arrow");
                PersistentDataContainer container = meta.getPersistentDataContainer();
                if (container.has(key, PersistentDataType.INTEGER)){
                    double foundValue = container.get(key, PersistentDataType.INTEGER);
                    if (foundValue == 1 || foundValue == 3) {
                        //give killer a new event arrow
                    }
            }
        }
    }
}