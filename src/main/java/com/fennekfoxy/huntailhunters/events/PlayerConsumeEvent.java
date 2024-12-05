package com.fennekfoxy.huntailhunters.events;

import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameItems;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class PlayerConsumeEvent implements Listener {

    private final GameManager gameManager;

    public PlayerConsumeEvent(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @EventHandler
    public void onPlayerConsume(PlayerItemConsumeEvent e) {
        if (gameManager.isActiveGame() && gameManager.isPlayerInArena(e.getPlayer(), gameManager.getActiveArena())) {
            ItemStack consumedItem = e.getItem();
            ItemMeta meta = consumedItem.getItemMeta();
            NamespacedKey key = GameItems.getItemIdKey();
            PersistentDataContainer container = meta.getPersistentDataContainer();
            if (container.has(key, PersistentDataType.INTEGER)) {
                int foundValue = container.get(key, PersistentDataType.INTEGER);
                if (foundValue == 4) {
                    Player player = e.getPlayer();
                    Random random = new Random();
                    int number = random.nextInt(3);

                    if (number == 0) {
                        int duration = HuntailHunters.getPlugin().getConfig().getInt("power_ups.speed.duration", 600);
                        int amplifier = HuntailHunters.getPlugin().getConfig().getInt("power_ups.speed.amplifier", 1);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, duration, amplifier));
                        String speedBoost = MessagesConfig.get().getString("speed_boost");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', speedBoost));
                    } else if (number == 1) {
                        int duration = HuntailHunters.getPlugin().getConfig().getInt("power_ups.jump_boost.duration", 600);
                        int amplifier = HuntailHunters.getPlugin().getConfig().getInt("power_ups.jump_boost.amplifier", 1);
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, duration, amplifier));
                        String jumpBoost = MessagesConfig.get().getString("jump_boost");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', jumpBoost));
                    } else if (number == 2) {
                        player.getInventory().addItem(GameItems.newArrow());
                        String extraArrow = MessagesConfig.get().getString("extra_arrow");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', extraArrow));
                    }
                }

            }
        }
    }
}

