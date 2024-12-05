package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.Permissions;
import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class JoinCommand extends SubCommand {

    private final GameManager gameManager;

    public JoinCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }


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
        if (player.hasPermission(Permissions.JOIN_COMMAND)) {
            gameManager.addPlayerToQueue(player);

            String joinMessage = MessagesConfig.get().getString("join_message");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', joinMessage));
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
