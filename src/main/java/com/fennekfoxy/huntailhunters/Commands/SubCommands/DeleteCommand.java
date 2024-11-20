package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class DeleteCommand extends SubCommand {
    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getDescription() {
        return "Deletes a specified arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters delete <arena>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.delete")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                String arenaName = args[1];
                Component confirmMessage = Component.text("Are you sure you want to delete the arena ")
                        .append(Component.text(arenaName).color(TextColor.color(0x00FF00)))
                        .append(Component.text("? "))
                        .append(
                                Component.text("[Confirm]")
                                        .color(TextColor.color(0x00FF00))
                                        .decorate(TextDecoration.BOLD)
                                        .clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/huntailhunters confirmdelete " + arenaName))
                        )
                        .append(Component.text(" "))
                        .append(
                                Component.text("[Decline]")
                                        .color(TextColor.color(0xFF0000))
                                        .decorate(TextDecoration.BOLD)
                                        .clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand("/huntailhunters declinedelete"))
                        );

                HuntailHunters.getPlugin().adventure().player(player).sendMessage(confirmMessage);
            }
        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
