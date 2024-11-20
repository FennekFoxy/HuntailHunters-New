package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class EndCommand extends SubCommand {

    GameManager gameManager = new GameManager();

    @Override
    public String getName() {
        return "end";
    }

    @Override
    public String getDescription() {
        return "Cleans up the event by removing all event items and ending all event functions";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters end <arena>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.end")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                gameManager.setActiveGame(false);
                gameManager.setActiveArena(null);

                HashSet<Player> queue = gameManager.getGameQueue();
                HashSet<Player> played = gameManager.getPlayedGame();
                for (Player p : queue) {
                    gameManager.cleanUpInventory(p);
                    gameManager.removePlayerFromGame(p);
                }
                for (Player p : played) {
                    gameManager.cleanUpInventory(p);
                    gameManager.removePlayerFromPlayed(p);
                }
                String endMessage = MessagesConfig.get().getString("end_message");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', endMessage));
            }
        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
