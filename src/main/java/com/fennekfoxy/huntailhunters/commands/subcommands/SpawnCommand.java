package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.ArenasConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpawnCommand extends SubCommand {
    @Override
    public String getName() {
        return "spawn";
    }

    @Override
    public String getDescription() {
        return "Sets the spawn point for the specified arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters spawn <arena> <x> <y> <z>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("huntailhunters.admin.spawn")) {
            if (args.length < 5) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                String name = args[1];
                if (ArenasConfig.get().contains(name)) {
                    try {
                        Location spawn = new Location(player.getWorld(), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
                        String coords = spawn.getX() + " " + spawn.getY() + " " + spawn.getZ();
                        ArenasConfig.get().set(name + ".Spawn", coords);


                        ArenasConfig.save();
                        player.sendMessage(ChatColor.GREEN + name + " set to (" + spawn.getX() + ", " + spawn.getY() + ", " + spawn.getZ() + ").");
                    } catch (NumberFormatException e) {
                        player.sendMessage("Coordinates must be a number.");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "There is no arena with that name.");
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
