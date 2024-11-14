package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import org.bukkit.entity.Player;

public class StopCommand extends SubCommand {
    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stops the current game in the specified arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters stop <arena>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.stop")){

            player.sendMessage("§a");
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
        //set active game to false and disabled all event functions
        //get all players from joined list and remove all 3 event items
    }
}
