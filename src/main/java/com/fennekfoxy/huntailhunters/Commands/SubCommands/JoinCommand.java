package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    GameManager gameManager = new GameManager();

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Joins the queue for the next game";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters join";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.join")){
            gameManager.addPlayerToQueue(player);

            String joinMessage = MessagesConfig.get().getString("join_message");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
