package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class StatsCommand extends SubCommand {

    private final GameManager gameManager;

    public StatsCommand(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public String getName() {
        return "stats";
    }

    @Override
    public String getDescription() {
        return "Shows statistics for the specified player, or yourself if not given a IGN";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters stats <IGN>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("huntailhunters.stats")) {
            if (args.length == 1) {
                gameManager.showPlayerStats(player);
            } else {
                String playerName = args[1];
                Player target = Bukkit.getServer().getPlayerExact(playerName);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player not found or player has not won any games yet.");
                } else {
                    gameManager.showPlayerStats(target);
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }

    }
}
