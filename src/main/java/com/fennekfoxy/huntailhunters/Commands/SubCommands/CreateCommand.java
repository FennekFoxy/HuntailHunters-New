package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.Configs.ArenasConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;


public class CreateCommand extends SubCommand {
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Creates a new arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters create <x1> <y1> <z1> <x2> <y2> <z2> <name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.create")){
            if (args.length < 8) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            }
                        try {
                String name = args[7];
                Location loc1 = new Location(Bukkit.getWorld("world"), + Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                Location loc2 = new Location(Bukkit.getWorld("world"), + Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]));

                if (ArenasConfig.get().contains(name)){
                    player.sendMessage(ChatColor.RED + "An Arena with the name " + name + " already exists.");
                }else{
                    ArenasConfig.get().set(name + ".Loc1.X", loc1.getX());
                    ArenasConfig.get().set(name + ".Loc1.Y", loc1.getY());
                    ArenasConfig.get().set(name + ".Loc1.Z", loc1.getZ());

                    ArenasConfig.get().set(name + ".Loc2.X", loc2.getX());
                    ArenasConfig.get().set(name + ".Loc2.Y", loc2.getY());
                    ArenasConfig.get().set(name + ".Loc2.Z", loc2.getZ());

                    ArenasConfig.save();

                    player.sendMessage(ChatColor.GREEN + "Arena " + name + " created successfully.");
                }
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Coordinates must be a number.");
            }

        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
