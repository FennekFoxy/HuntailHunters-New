package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class GetQueueCommand extends SubCommand {

    GameManager gameManager = new GameManager();

    @Override
    public String getName() {
        return "getqueue";
    }

    @Override
    public String getDescription() {
        return "gets a list of players in queue";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters getqueue";
    }

    @Override
    public void perform(Player player, String[] args) {
        HashSet<Player> queue = gameManager.getGameQueue();
        int numOfPlayer = gameManager.getQueueSize();
        StringBuilder num = new StringBuilder("Number of players in queue: " + numOfPlayer);
        StringBuilder playerNames = new StringBuilder("Players in queue: ");
        if(player.hasPermission("huntailhunters.admin.getqueue")){
            if (queue.isEmpty()) {
                player.sendMessage("There are no players in the queue.");
                return;
            }
            for (Player queuedPlayer : queue) {
                playerNames.append(queuedPlayer.getName()).append(" ");
            }
            player.sendMessage(num.toString());
            player.sendMessage(playerNames.toString());
        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
