package com.fennekfoxy.huntailhunters;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class GameItems {

    public static ItemStack newBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Event Bow");
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack newArrow() {
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 2);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Event Arrow");
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack newSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 3);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Event Sword");
        item.setItemMeta(meta);
        return item;
    }
    public static ItemStack newPowerUp(){
        ItemStack item = new ItemStack(Material.POTION,1);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 4);
        meta.setDisplayName(ChatColor.GOLD + "Power Up");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.LIGHT_PURPLE + "This power up grants a random bonus!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
