package com.fennekfoxy.huntailhunters;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class GameItems {

    public static ItemStack eventBow;
    public static ItemStack eventArrow;
    public static ItemStack eventSword;
    public static ItemStack eventPowerUp;

    public static void init() {
        newBow();
        newArrow();
        newSword();
        newPowerUp();
    }

    private static void newBow() {
        ItemStack item = new ItemStack(Material.BOW, 1);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
        meta.setDisplayName("§5Event Bow");
        item.setItemMeta(meta);
        eventBow = item;
    }
    private static void newArrow() {
        ItemStack item = new ItemStack(Material.ARROW, 1);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 2);
        meta.setDisplayName("§5Event Arrow");
        item.setItemMeta(meta);
        eventArrow = item;
    }
    private static void newSword() {
        ItemStack item = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 3);
        meta.setDisplayName("§5Event Sword");
        item.setItemMeta(meta);
        eventSword = item;
    }
    private static void newPowerUp(){
        ItemStack item = new ItemStack(Material.POTION);
        ItemMeta meta = item.getItemMeta();
        NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item id");
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 4);
        meta.setDisplayName("§6Power Up");
        List<String> lore = new ArrayList<>();
        lore.add("§dThis power up grants a random bonus!");
        meta.setLore(lore);
        item.setItemMeta(meta);
        eventPowerUp = item;
    }
}
