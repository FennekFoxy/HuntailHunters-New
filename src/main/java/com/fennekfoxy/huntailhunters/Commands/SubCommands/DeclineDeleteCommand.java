package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import org.bukkit.entity.Player;

public class DeclineDeleteCommand extends SubCommand {
    @Override
    public String getName() {
        return "declinedelete";
    }

    @Override
    public String getDescription() {
        return "declines the deletion of an arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters declinedelete";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.declinedelete")){
            player.sendMessage("§a");
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
        //send a message from message config saying arena deletion has been canceled

    }
}

