package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashSet;

public class StopCommand extends SubCommand {

    private final GameManager gameManager;

    public StopCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public String getDescription() {
        return "Stops the current game in the specified arena without clearing the queue";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters stop <arena>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("huntailhunters.admin.stop")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                gameManager.setActiveGame(false);
                gameManager.announceMessage(MessagesConfig.get().getString("force_stop"));
                HashSet<Player> queue = gameManager.getGameQueue();
                HashSet<Player> played = gameManager.getPlayedGame();
                for (Player p : queue) {
                    gameManager.cleanUpInventory(p);
                }
                for (Player p : played) {
                    gameManager.cleanUpInventory(p);
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
