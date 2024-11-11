package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import org.bukkit.Bukkit;
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
        if (player.hasPermission("huntailhunters.spawn")){
            if (args.length < 5) {
                player.sendMessage("§aUsage: /huntailhunters spawn <arena> <x> <y> <z>");
            }
            String arena = args[1];
            //check if arena already exists
            try {
                double x = Double.parseDouble(args[2]);
                double y = Double.parseDouble(args[3]);
                double z = Double.parseDouble(args[4]);
                Location newSpawn = new Location(Bukkit.getWorld("world"), x, y, z);
                //save new spawnpoint to arena
                player.sendMessage("§aSpawn point for arena " + arena + " set to (" + x + ", " + y + ", " + z + ").");
            }catch (NumberFormatException e) {
                player.sendMessage("Coordinates must be a number.");
            }
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
    }
}
