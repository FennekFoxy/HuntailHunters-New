package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DeclineDeleteCommand extends SubCommand {
    @Override
    public String getName() {
        return "declinedelete";
    }

    @Override
    public String getDescription() {
        return "declines the deletion of an arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters declinedelete";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("huntailhunters.admin.declinedelete")) {

            String decline = MessagesConfig.get().getString("delete_cancelled");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', decline));
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }

    }
}

