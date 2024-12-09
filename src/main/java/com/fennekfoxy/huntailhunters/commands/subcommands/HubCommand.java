package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.HuntailHunters;
import com.fennekfoxy.huntailhunters.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class HubCommand extends SubCommand {
    @Override
    public String getName() {
        return "hub";
    }

    @Override
    public String getDescription() {
        return "Sets coordinates for hub where player is standing";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters hub";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.hub")){
            Location location = player.getLocation();
            World world = player.getWorld();

            HuntailHunters.getPlugin().getConfig().set("Hub.world", world.getName());
            HuntailHunters.getPlugin().getConfig().set("Hub.x", location.getX());
            HuntailHunters.getPlugin().getConfig().set("Hub.y", location.getY());
            HuntailHunters.getPlugin().getConfig().set("Hub.z", location.getZ());

            player.sendMessage(ChatColor.GREEN + "Hub location has been set to your current location.");
        }

    }
}
