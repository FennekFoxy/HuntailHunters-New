package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
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
            if (args.length < 5) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            }
            gameManager.setActiveGame(true);

            //check player inventory if they have any of the event items then give them what they dont have

            String roundStarting = MessagesConfig.get().getString("round_starting");
            roundStarting = roundStarting.replace("{round}", args[2]);
            gameManager.announceMessage(roundStarting);
        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }

    }
}
