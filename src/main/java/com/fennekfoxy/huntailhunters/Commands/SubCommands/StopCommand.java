package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.entity.Player;

public class StopCommand extends SubCommand {

    GameManager gameManager = new GameManager();

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

            gameManager.setActiveGame(false);
            gameManager.announceMessage(MessagesConfig.get().getString("force_stop"));
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
        //set active game to false and disabled all event functions
        //get all players from joined list and remove all 3 event items
    }
}
