package com.fennekfoxy.huntailhunters.Commands.SubCommands;

import com.fennekfoxy.huntailhunters.Commands.SubCommand;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class StartCommand extends SubCommand {

    private GameManager gameManager;

    public GameManager getGameManager(){
        return gameManager;
    }


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
        if(player.hasPermission("huntailhunters.start")){
            ArrayList<Player> queue = gameManager.getGameQueue();
            for (Player p : queue) {
                //teleport player to arena and check inventory

            /* check inventorys
            public int getEmptySlots(Player player) {
                PlayerInventory inventory = player.getInventory();
                ItemStack[] cont = inventory.getContents();
                int i = 0;
                for (ItemStack item : cont)
                    if (item != null && item.getType() != Material.AIR) {
                        i++;
                    }
                return 36 - i;
            }
            7 for 3 inv slots and 4 armor slots
           if (getEmptySlots(Player) <= 7)){
           player.sendMessage("Please make room for the event items in your inventory");
           }*/
            }

            player.sendMessage("§aPlayers have been teleported into the arena.");
        }else{
            player.sendMessage("§cYou do not have permission to use this command.");
        }
        //teleport all players in the join queue to the arena
        //look into listening for a server done event or add a delay to the teleport
        //look through all inventorys of the players that are teleported and make sure they have at least 3 empty inventory slots
        //if not advise them to make room
    }
}
