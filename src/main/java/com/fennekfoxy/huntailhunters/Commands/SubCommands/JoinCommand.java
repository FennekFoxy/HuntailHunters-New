package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.GameItems;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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

            player.sendMessage("§aYou have joined the queue for the next round of Huntail Hunter!");
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
    }
}
