package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.Permissions;
import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.ArenasConfig;
import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameItems;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Set;

public class RoundCommand extends SubCommand {

    private final GameManager gameManager;

    public RoundCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

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
        if (player.hasPermission(Permissions.ROUND_COMMAND)) {
            if (args.length < 4) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                String name = args[1];
                gameManager.setActiveGame(true);
                gameManager.setActiveArena(name);
                Set<Player> queue = gameManager.getGameQueue();

                if (!ArenasConfig.get().contains(name)) {
                    player.sendMessage(ChatColor.RED + "There is no arena named " + name);
                } else {
                    Bukkit.getScheduler().runTask(HuntailHunters.getPlugin(), () -> {
                        for (Player p : queue) {
                            if (p != null) {
                                boolean hasEventBow = false;
                                boolean hasEventArrow = false;
                                boolean hasEventSword = false;

                                Inventory inventory = p.getPlayer().getInventory();
                                NamespacedKey key = GameItems.getItemIdKey();

                                for (ItemStack item : inventory.getContents()) {
                                    if (item != null && item.hasItemMeta() && item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.INTEGER)) {
                                        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
                                        int itemId = container.get(key, PersistentDataType.INTEGER);

                                        switch (itemId) {
                                            case 1 -> hasEventBow = true;
                                            case 2 -> hasEventArrow = true;
                                            case 3 -> hasEventSword = true;
                                            case 4 -> inventory.remove(item);
                                        }
                                    }
                                }
                                if (!hasEventBow) {
                                    p.getInventory().addItem(GameItems.newBow());
                                }
                                if (!hasEventArrow) {
                                    p.getInventory().addItem(GameItems.newArrow());
                                }
                                if (!hasEventSword) {
                                    p.getInventory().addItem(GameItems.newSword());
                                }
                            }
                        }
                    });
                }
                boolean powerUps = Boolean.parseBoolean(args[3]);
                if (powerUps) {
                    String powerupsEnabled = MessagesConfig.get().getString("power_ups_enabled");
                    gameManager.announceMessage(ChatColor.translateAlternateColorCodes('&', powerupsEnabled));
                    gameManager.spawnPowerups(name, player);
                } else {
                    String powerupsDisabled = MessagesConfig.get().getString("power_ups_disabled");
                    gameManager.announceMessage(ChatColor.translateAlternateColorCodes('&', powerupsDisabled));
                }
                String roundStarting = MessagesConfig.get().getString("round_starting");
                roundStarting = roundStarting.replace("{round}", args[2]);
                gameManager.announceMessage(roundStarting);
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }

    }
}
