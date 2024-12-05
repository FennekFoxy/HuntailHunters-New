package com.fennekfoxy.huntailhunters;

import com.fennekfoxy.huntailhunters.commands.CommandManager;
import com.fennekfoxy.huntailhunters.configs.ArenasConfig;
import com.fennekfoxy.huntailhunters.configs.MessagesConfig;
import com.fennekfoxy.huntailhunters.database.Database;
import com.fennekfoxy.huntailhunters.database.PlayerStatsService;
import com.fennekfoxy.huntailhunters.events.*;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class HuntailHunters extends JavaPlugin {

    private static HuntailHunters plugin;
    private BukkitAudiences adventure;
    private Database database;
    private GameManager gameManager;
    private PlayerStatsService playerStatsService;

    public static HuntailHunters getPlugin() {
        return plugin;
    }

    public BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        plugin = this;
        database = new Database(this);
        database.initializeConnectionPool();
        this.adventure = BukkitAudiences.create(this);
        this.playerStatsService = new PlayerStatsService(database);
        gameManager = new GameManager(playerStatsService);
        ArenasConfig.setup();
        ArenasConfig.get().options().copyDefaults(true);
        ArenasConfig.save();
        saveResource("messages.yml", false);
        MessagesConfig.setup();
        MessagesConfig.get().options().copyDefaults(true);
        MessagesConfig.save();
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new PlayerConsumeEvent(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerKilledEvent(gameManager, database), this);
        getServer().getPluginManager().registerEvents(new PlayerLogoutEvent(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerShootEvent(gameManager), this);
        getServer().getPluginManager().registerEvents(new PlayerShotEvent(gameManager), this);

        getCommand("huntailhunters").setExecutor(new CommandManager(gameManager));
        getLogger().info(ChatColor.AQUA + "HuntailHunters (version " + getDescription().getVersion() + ") loaded successfully");
    }

    @Override
    public void onDisable() {
        getLogger().info(ChatColor.RED + "HuntailHunters (version " + getDescription().getVersion() + ") has been disabled");
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        if (database != null) {
            database.closeConnectionPool();
        }
    }
}
