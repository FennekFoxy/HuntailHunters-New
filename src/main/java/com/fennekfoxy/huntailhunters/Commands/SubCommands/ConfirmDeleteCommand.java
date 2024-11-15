package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.Configs.ArenasConfig;
import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ConfirmDeleteCommand extends SubCommand {
    @Override
    public String getName() {
        return "confirmdelete";
    }

    @Override
    public String getDescription() {
        return "Confirms the deletion of an arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters confirmdelete <arena>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.confirmdelete")){

            String name = args[1];
            if (ArenasConfig.get().contains(name)){
                String confirm = MessagesConfig.get().getString("delete_confirmed");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', confirm));
            }else{
                player.sendMessage(ChatColor.RED + "Arena " + name + " does not exist.");
            }
            //check if arena config contains the arena
            //if does remove the arena info from the config and send a delete successfull message
            //if not send a message that the arena does not exist


        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }

    }
}
