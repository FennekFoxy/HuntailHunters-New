package com.fennekfoxy.huntailhunters.Commands;

import com.fennekfoxy.huntailhunters.Commands.SubCommands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();


    public CommandManager(){

        subcommands.add(new ConfirmDeleteCommand());
        subcommands.add(new CreateCommand());
        subcommands.add(new DeclineDeleteCommand());
        subcommands.add(new DeleteCommand());
        subcommands.add(new EndCommand());
        subcommands.add(new GetQueueCommand());
        subcommands.add(new JoinCommand());
        subcommands.add(new PowerUpCommand());
        subcommands.add(new RoundCommand());
        subcommands.add(new SpawnCommand());
        subcommands.add(new StartCommand());
        subcommands.add(new StatsCommand());
        subcommands.add(new StopCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length > 0){
                for(int i = 0; i < getSubcommands().size(); i++){
                    if(args[0].equalsIgnoreCase(getSubcommands().get(i).getName())){
                        getSubcommands().get(i).perform(player, args);
                    }
                }
            }else if (args.length == 0 || args[1] == "help"){
                player.sendMessage("--------------------------------------");
                for(int i = 0; i < getSubcommands().size(); i++){
                    player.sendMessage(ChatColor.GREEN + getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription());
                }
                player.sendMessage("--------------------------------------");
            }
        }



        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }
}

