package com.fennekfoxy.huntailhunters;

import com.fennekfoxy.huntailhunters.Commands.CommandManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class HuntailHunters extends JavaPlugin {

    @Override
    public void onEnable() {

        //getServer().getPluginManager().registerEvents(new GameEvents(this), this);

        GameItems.init();

        getCommand("huntailhunters").setExecutor(new CommandManager());
        getLogger().info(ChatColor.AQUA + "HuntailHunters (version " + getDescription().getVersion() + ") loaded successfully");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "HuntailHunters (version " + getDescription().getVersion() + ") has been disabled");
    }
}
