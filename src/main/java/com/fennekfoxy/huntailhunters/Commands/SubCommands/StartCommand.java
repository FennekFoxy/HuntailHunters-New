package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class StartCommand extends SubCommand {

    GameManager gameManager = new GameManager();

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Starts a game in a specified arena and teleports all players in the queue to the arena";
    }

    @Override
    public String getSyntax() {
        return "/huntailhunters start <arena>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("huntailhunters.admin.start")){
            if (args.length < 2) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            }
            ArrayList<Player> queue = gameManager.getGameQueue();
            gameManager.announceMessage(MessagesConfig.get().getString("game_starting"));
            for (Player p : queue) {
                if (p != null) {
                    // p.teleport(Arena spawn Point);
                    if(gameManager.countEmptySlots(p) <= 3){

                        String makeRoomMessage = MessagesConfig.get().getString("make_room");
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', makeRoomMessage));
                    }
                }
            }

            player.sendMessage(ChatColor.GREEN + "Players have been teleported into the arena.");
        }else{
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
        //look into listening for a server done event or add a delay to the teleport
        //look through all inventorys of the players that are teleported and make sure they have at least 3 empty inventory slots
        //if not advise them to make room
    }
}
