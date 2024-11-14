package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.entity.Player;

public class RoundCommand extends SubCommand {

    GameManager gameManager = new GameManager();

    @Override
    public String getName() {
        return "round";
    }

    @Override
    public String getDescription() {
        return "Start a new round in a specified arena with given round number and power ups.";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters round <arena> <round_number> start <true/false>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.round")){
            gameManager.setActiveGame(true);
            player.sendMessage("§a");
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }

    }
}
