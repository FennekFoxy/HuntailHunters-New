package com.fennekfoxy.huntailhunters.configs;

import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class MessagesConfig {
    private static File file;
    private static FileConfiguration messages;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HuntailHunters").getDataFolder(), "messages.yml");
        if (!file.exists()) {
            HuntailHunters.getPlugin().getLogger().warning("messages.yml could not be found.");
        }
        messages = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return messages;
    }

    public static void save() {
        try {
            messages.save(file);
        } catch (IOException e) {
            HuntailHunters.getPlugin().getLogger().log(Level.SEVERE, "Could not save messages.yml", e);
        }
    }

    public static void reload() {
        messages = YamlConfiguration.loadConfiguration(file);
    }
}