package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.Permissions;
import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.ArenasConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PowerUpCommand extends SubCommand {
    @Override
    public String getName() {
        return "powerup";
    }

    @Override
    public String getDescription() {
        return "Adds a powerup spawn location to the specified arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters powerup <add/remove> <arena> <x> <y> <z> <powerup name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission(Permissions.POWERUP_COMMAND)) {
            if (args[1].equals("add")) {
                if (args.length < 7) {
                    player.sendMessage(ChatColor.GREEN + "/huntailhunters powerup add <arena> <x> <y> <z> <powerup name>");
                } else {
                    String name = args[2];
                    if (ArenasConfig.get().contains(name)) {
                        try {
                            Location powerupLoc = new Location(player.getWorld(), Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
                            String powerup_name = args[6];
                            String coords = powerupLoc.getX() + " " + powerupLoc.getY() + " " + powerupLoc.getZ();
                            ArenasConfig.get().set(name + ".PowerUps" + "." + powerup_name, coords);
                            ArenasConfig.save();
                            player.sendMessage(ChatColor.GREEN + powerup_name + " added to arena " + name + ".");
                        } catch (NumberFormatException e) {
                            player.sendMessage("Coordinates must be a number.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "There is no arena with that name.");
                    }
                }
            } else if (args[1].equals("remove")) {
                if (args.length < 4) {
                    player.sendMessage(ChatColor.GREEN + "/huntailhunters powerup remove <arena> <powerup name>");
                } else {
                    String name = args[2];
                    String powerupName = args[3];
                    if (!ArenasConfig.get().contains(name)) {
                        player.sendMessage(ChatColor.RED + "There is no arena with that name.");
                    } else {
                        if (!ArenasConfig.get().contains(name + ".PowerUps." + powerupName)) {
                            player.sendMessage(ChatColor.RED + "There is no powerup location with that name.");
                        } else {
                            ArenasConfig.get().set(name + ".PowerUps." + powerupName, null);
                            ArenasConfig.save();

                            player.sendMessage(ChatColor.GREEN + powerupName + " has been removed from the " + name + " arena.");
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
