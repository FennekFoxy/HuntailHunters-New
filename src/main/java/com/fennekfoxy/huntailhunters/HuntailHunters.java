package com.fennekfoxy.huntailhunters;

import com.fennekfoxy.huntailhunters.Commands.CommandManager;
import com.fennekfoxy.huntailhunters.Configs.ArenasConfig;
import com.fennekfoxy.huntailhunters.Configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.Events.*;
import com.fennekfoxy.huntailhunters.Util.Database;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;


import java.sql.SQLException;


public class HuntailHunters extends JavaPlugin {

    private BukkitAudiences adventure;
    private Database database;

    public BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }


    private static HuntailHunters plugin;

    @Override
    public void onEnable() {
        try {
            this.database = new Database();
            database.initializeDatabase();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Unable to connect to database and create tables.");
        }
        plugin = this;
        this.adventure = BukkitAudiences.create(this);
        ArenasConfig.setup();
        ArenasConfig.get().options().copyDefaults(true);
        ArenasConfig.save();
        saveResource("messages.yml", false);
        MessagesConfig.setup();
        MessagesConfig.get().options().copyDefaults(true);
        MessagesConfig.save();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerConsumeEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerKilledEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerLogoutEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerShootEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerShotEvent(), this);

        getCommand("huntailhunters").setExecutor(new CommandManager());
        getLogger().info(ChatColor.AQUA + "HuntailHunters (version " + getDescription().getVersion() + ") loaded successfully");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "HuntailHunters (version " + getDescription().getVersion() + ") has been disabled");
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }

    public static HuntailHunters getPlugin() {
        return plugin;
    }

    public Database getDatabase() {
        return database;
    }
}
