package com.fennekfoxy.huntailhunters.Events;

import com.fennekfoxy.huntailhunters.GameItems;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PlayerConsumeEvent implements Listener {

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent e) {
        ItemStack consumedItem = e.getItem();
        ItemMeta meta = consumedItem.getItemMeta();
        if (meta != null && meta.hasDisplayName() && meta.getDisplayName().equals("§6Power-Up"))/*Player is in the active arena && is in the join queue && the item has the meta/nbt data of the power up*/{
            //check if meta/nbt matches the power up && if there's an active game && the player is in the arena
            Player player = e.getPlayer();
            Random random = new Random();
            int number = random.nextInt(3);

            if (number == 0){
                int duration = HuntailHunters.getPlugin().getConfig().getInt("power_ups.speed.duration",600);
                int amplifier = HuntailHunters.getPlugin().getConfig().getInt("power_ups.speed.amplifier",1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, amplifier));
                player.sendMessage(/*Messages config messages.speed_boost*/"hi");
            }else if(number == 1){
                int duration = HuntailHunters.getPlugin().getConfig().getInt("power_ups.speed.duration",600);
                int amplifier = HuntailHunters.getPlugin().getConfig().getInt("power_ups.speed.amplifier",1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration, amplifier));
                player.sendMessage(/*Messages config messages.jump_boost*/"hi");
            }else if(number == 2){
                player.getInventory().addItem(GameItems.eventArrow);
                player.sendMessage(/*Messages config messages.extra_arrow*/"hi");
            }
        }

    }
}

