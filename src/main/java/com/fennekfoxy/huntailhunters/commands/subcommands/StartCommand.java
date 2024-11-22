package com.fennekfoxy.huntailhunters.commands.subcommands;

import com.fennekfoxy.huntailhunters.commands.SubCommand;
import com.fennekfoxy.huntailhunters.configs.ArenasConfig;
import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.GameManager;
import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

public class StartCommand extends SubCommand {

    private final GameManager gameManager;

    public StartCommand(GameManager gameManager) {
        this.gameManager = gameManager;
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
        if (player.hasPermission("huntailhunters.admin.start")) {
            if (args.length < 2) {
                player.sendMessage(ChatColor.GREEN + getSyntax());
            } else {
                HashSet<Player> queue = gameManager.getGameQueue();
                String name = args[1];
                gameManager.announceMessage(MessagesConfig.get().getString("game_starting"));
                if (!ArenasConfig.get().contains(name)) {
                    player.sendMessage(ChatColor.RED + "There is no arena with that name.");
                } else {
                    for (Player p : queue) {
                        if (p != null) {
                            String coords = ArenasConfig.get().getString(name + ".Spawn");
                            if (coords != null) {
                                String[] parts = coords.split(" ");
                                double x = Double.parseDouble(parts[0]);
                                double y = Double.parseDouble(parts[1]);
                                double z = Double.parseDouble(parts[2]);

                                new BukkitRunnable() {
                                    @Override
                                    public void run() {
                                        String worldName = ArenasConfig.get().getString(name + ".World");
                                        World world = Bukkit.getWorld(worldName);
                                        Location spawn = new Location(world, x, y, z);

                                        p.teleport(spawn);
                                    }
                                }.runTaskLater(HuntailHunters.getPlugin(), 100L);
                            }
                            if (gameManager.countEmptySlots(p) <= 3) {
                                String makeRoomMessage = MessagesConfig.get().getString("make_room");
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', makeRoomMessage));
                            }
                        }
                    }
                    String teleporting_soon = MessagesConfig.get().getString("teleporting_soon");
                    gameManager.announceMessage(ChatColor.translateAlternateColorCodes('&', teleporting_soon));
                }
            }
        } else {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
        }
    }
}
