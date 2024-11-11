package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import org.bukkit.Bukkit;
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
        if(player.hasPermission("huntailhunters.create")){
            try {
                String name = args[7];
                Location loc1 = new Location(Bukkit.getWorld("world"), + Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                Location loc2 = new Location(Bukkit.getWorld("world"), + Double.parseDouble(args[4]), Double.parseDouble(args[5]), Double.parseDouble(args[6]));
                //save to Arenas config
                player.sendMessage("§aArena " + name + "§a created successfully.");

            } catch (NumberFormatException e) {
                player.sendMessage("Coordinates must be a number.");
            }

        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
    }
}
