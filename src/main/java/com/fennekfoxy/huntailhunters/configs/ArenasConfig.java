package com.fennekfoxy.huntailhunters.configs;

import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class ArenasConfig {
    private static File file;
    private static FileConfiguration arenas;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HuntailHunters").getDataFolder(), "arenas.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                HuntailHunters.getPlugin().getLogger().log(Level.SEVERE, "File could not be created", e);
            }
        }
        arenas = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get() {
        return arenas;
    }

    public static void save() {
        try {
            arenas.save(file);
        } catch (IOException e) {
            HuntailHunters.getPlugin().getLogger().log(Level.SEVERE, "File could not be saved", e);
        }
    }

    public static void reload() {
        arenas = YamlConfiguration.loadConfiguration(file);
    }
}
