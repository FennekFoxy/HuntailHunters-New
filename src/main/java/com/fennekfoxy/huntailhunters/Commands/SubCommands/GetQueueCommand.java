package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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
        ArrayList<Player> queue = gameManager.getGameQueue();
        StringBuilder playerNames = new StringBuilder("Players in queue: ");
        if(player.hasPermission("huntailhunters.admin.getqueue")){
            if (queue.isEmpty()) {
                player.sendMessage("There are no players in the queue.");
                return;
            }
            for (int i = 0; i < queue.size(); i++) {
                playerNames.append(queue.get(i).getName());
            }
            player.sendMessage(playerNames.toString());
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
    }
}
