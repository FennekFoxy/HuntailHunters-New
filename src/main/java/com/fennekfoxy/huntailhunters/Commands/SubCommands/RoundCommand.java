package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.Configs.ArenasConfig;
import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameItems;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;

public class RoundCommand extends SubCommand {

    GameManager gameManager = new GameManager();

    @Override
    public String getName() {
        return "round";
    }

    @Override
    public String getDescription() {
        return "Start a new round in a specified arena with given round number and power ups.";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters round <arena> <round_number> <true/false>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.round")) {
            if (args.length < 4) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                String name = args[1];
                gameManager.setActiveGame(true);
                gameManager.setActiveArena(name);
                HashSet<Player> queue = gameManager.getGameQueue();

                    if (!ArenasConfig.get().contains(name)) {
                        player.sendMessage(ChatColor.RED + "There is no arena named " + name);
                    } else {
                        for (Player p : queue) {
                            if (p != null) {
                                boolean hasEventBow = false;
                                boolean hasEventArrow = false;
                                boolean hasEventSword = false;

                                Inventory inventory = player.getPlayer().getInventory();
                                NamespacedKey key = new NamespacedKey(HuntailHunters.getPlugin(), "item_id");

                                for(ItemStack item : inventory.getContents()){
                                    if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)){
                                        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
                                        int itemId = container.get(key,PersistentDataType.INTEGER);

                                        if (itemId == 1){
                                            hasEventBow = true;
                                        }else if(itemId == 2){
                                            hasEventArrow = true;
                                        }else if(itemId == 3){
                                            hasEventSword = true;
                                        }else if(itemId == 4){
                                            inventory.remove(item);
                                        }
                                    }
                                }
                                if (!hasEventBow){
                                    p.getInventory().addItem(GameItems.newBow());
                                }
                                if (!hasEventArrow){
                                    p.getInventory().addItem(GameItems.newArrow());
                                }
                                if (!hasEventSword){
                                    p.getInventory().addItem(GameItems.newSword());
                                }
                            }
                        }
                    }
                boolean powerUps = Boolean.parseBoolean(args[3]);
                if (powerUps){
                    String powerupsEnabled = MessagesConfig.get().getString("power_ups_enabled");
                    gameManager.announceMessage(ChatColor.translateAlternateColorCodes('&', powerupsEnabled));
                    gameManager.spawnPowerups(name, player);
                }else{
                    String powerupsDisabled = MessagesConfig.get().getString("power_ups_disabled");
                    gameManager.announceMessage(ChatColor.translateAlternateColorCodes('&', powerupsDisabled));
                }
                String roundStarting = MessagesConfig.get().getString("round_starting");
                roundStarting = roundStarting.replace("{round}", args[2]);
                gameManager.announceMessage(roundStarting);
                }
        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }

    }
}
