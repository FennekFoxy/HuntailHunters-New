package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.ArenasConfig;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
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
        if (player.hasPermission("huntailhunters.admin.create")) {
            if (args.length < 8) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                try {
                    String name = args[7];
                    World world = player.getWorld();
                    Location loc1 = new Location(world, Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                    Location loc2 = new Location(world, Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]));

                    if (ArenasConfig.get().contains(name)) {
                        player.sendMessage(ChatColor.RED + "An Arena with the name " + name + " already exists.");
                    } else {
                        String firstCorner = loc1.getX() + " " + loc1.getY() + " " + loc1.getZ();
                        String secondCorner = loc2.getX() + " " + loc2.getY() + " " + loc2.getZ();
                        ArenasConfig.get().set(name + ".World", world.getName());
                        ArenasConfig.get().set(name + ".Loc1", firstCorner);
                        ArenasConfig.get().set(name + ".Loc2", secondCorner);

                        ArenasConfig.save();

                        player.sendMessage(ChatColor.GREEN + "Arena " + name + " created successfully.");
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Coordinates must be a number.");
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
