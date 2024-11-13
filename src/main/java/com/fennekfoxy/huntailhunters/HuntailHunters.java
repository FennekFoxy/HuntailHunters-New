package com.fennekfoxy.huntailhunters;

import com.fennekfoxy.huntailhunters.Commands.CommandManager;
import com.fennekfoxy.huntailhunters.Events.PlayerConsumeEvent;
import com.fennekfoxy.huntailhunters.Events.PlayerKilledEvent;
import com.fennekfoxy.huntailhunters.Events.PlayerShootEvent;
import com.fennekfoxy.huntailhunters.Events.PlayerShotEvent;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class HuntailHunters extends JavaPlugin {

    private static HuntailHunters plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerConsumeEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerKilledEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerShootEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerShotEvent(), this);

        GameItems.init();
        getCommand("huntailhunters").setExecutor(new CommandManager());
        getLogger().info(ChatColor.AQUA + "HuntailHunters (version " + getDescription().getVersion() + ") loaded successfully");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "HuntailHunters (version " + getDescription().getVersion() + ") has been disabled");
    }

    public static HuntailHunters getPlugin() {
        return plugin;
    }
}
