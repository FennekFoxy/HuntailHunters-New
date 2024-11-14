package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import org.bukkit.entity.Player;

public class HelpCommand extends SubCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Get info on the Huntail Hunters Command";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters help";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.help")){

            player.sendMessage("§a");
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
    }
}
