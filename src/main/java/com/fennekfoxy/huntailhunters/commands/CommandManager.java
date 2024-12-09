package com.fennekfoxy.huntailhunters.commands;

import com.fennekfoxy.huntailhunters.commands.subcommands.*;
import com.fennekfoxy.huntailhunters.GameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {

    private List<SubCommand> subcommands = new ArrayList<>();
    private GameManager gameManager;

    public CommandManager(GameManager gameManager){
        this.gameManager = gameManager;
        subcommands.add(new ConfirmDeleteCommand());
        subcommands.add(new CreateCommand());
        subcommands.add(new DeclineDeleteCommand());
        subcommands.add(new DeleteCommand());
        subcommands.add(new EndCommand(gameManager));
        subcommands.add(new GetQueueCommand(gameManager));
        subcommands.add(new HubCommand());
        subcommands.add(new JoinCommand(gameManager));
        subcommands.add(new PowerUpCommand());
        subcommands.add(new RoundCommand(gameManager));
        subcommands.add(new SpawnCommand());
        subcommands.add(new StartCommand(gameManager));
        subcommands.add(new StatsCommand(gameManager));
        subcommands.add(new StopCommand(gameManager));
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
            }else if (args.length == 0 || args[1].equalsIgnoreCase("help")){
                player.sendMessage("--------------------------------------");
                for(int i = 0; i < getSubcommands().size(); i++){
                    player.sendMessage(ChatColor.GREEN + getSubcommands().get(i).getSyntax() + " - " + getSubcommands().get(i).getDescription());
                }
                player.sendMessage("--------------------------------------");
            }
        }
        return true;
    }
    public List<SubCommand> getSubcommands(){
        return subcommands;
    }
}

