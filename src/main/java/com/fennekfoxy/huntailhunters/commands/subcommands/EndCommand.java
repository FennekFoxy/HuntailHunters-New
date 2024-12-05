package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.Permissions;
import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Set;

public class EndCommand extends SubCommand {

    private final GameManager gameManager;

    public EndCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

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
        if (player.hasPermission(Permissions.END_COMMAND)) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                gameManager.setActiveGame(false);
                gameManager.setActiveArena(null);

                Set<Player> queue = gameManager.getGameQueue();
                Set<Player> played = gameManager.getPlayedGame();
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
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
