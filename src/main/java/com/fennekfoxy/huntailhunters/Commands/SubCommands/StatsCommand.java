package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StatsCommand extends SubCommand {
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
        if(player.hasPermission("huntailhunters.stats")){
            if(args.length <= 1){
                //pull own stats
            }else{
                String playerName = args[1];
                Player target = Bukkit.getServer().getPlayerExact(playerName);
                if (target == null){
                    player.sendMessage("Player not found.");
                }else{
                    //pull target stats
                }
            }
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }

    }
}
