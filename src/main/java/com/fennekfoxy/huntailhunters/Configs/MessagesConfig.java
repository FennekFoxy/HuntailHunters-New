package com.fennekfoxy.huntailhunters.Configs;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MessagesConfig {
    private static File file;
    private static FileConfiguration messages;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("HuntailHunters").getDataFolder(), "messages.yml");
        if (!file.exists()) {
            System.out.println("messages.yml could not be found.");
        }
        messages = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration get(){
        return messages;
    }

    public static void save(){
        try{
            messages.save(file);
        } catch (IOException e) {
            System.out.println("File could not be saved");
        }
    }

    public static void reload(){
        messages = YamlConfiguration.loadConfiguration(file);
    }
}