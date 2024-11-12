package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import org.bukkit.entity.Player;

public class StartCommand extends SubCommand {
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Starts a game in a specified arena and teleports all players in the queue to the arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters start <arena>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.start")){

            player.sendMessage("§a");
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
        //teleport all players in the join queue to the arena
        //look into listening for a server done event or add a delay to the teleport
        //look through all inventorys of the players that are teleported and make sure they have at least 3 empty inventory slots
        //if not advise them to make room
    }
}
