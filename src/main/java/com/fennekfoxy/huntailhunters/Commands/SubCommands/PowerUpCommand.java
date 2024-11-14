package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import org.bukkit.Bukkit;
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
        return "/huntailhunters powerup <arena> <x> <y> <z>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.powerup")){
            String arena = args[1];
            // check if arena exists

            try {
                double x = Double.parseDouble(args[2]);
                double y = Double.parseDouble(args[3]);
                double z = Double.parseDouble(args[4]);
                String powerup_name = args[5];
                Location powerupLoc = new Location(Bukkit.getWorld("world"), x, y, z);
                //save new powerup location
                player.sendMessage("§aPower-up location of " + powerup_name + " added to arena " + arena + ".");
            }catch (NumberFormatException e) {
                player.sendMessage("Coordinates must be a number.");
            }
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
    }
}
