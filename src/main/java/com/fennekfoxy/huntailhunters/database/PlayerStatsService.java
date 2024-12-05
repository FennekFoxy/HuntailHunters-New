package com.fennekfoxy.huntailhunters.database;

import org.bukkit.entity.Player;
import java.util.concurrent.CompletableFuture;

public class PlayerStatsService {

    private final Database database;

    public PlayerStatsService(Database database) {
        this.database = database;
    }

    public CompletableFuture<PlayerStats> getPlayerStats(Player player) {
        // Directly return the CompletableFuture from the database method
        return database.findPlayerStatsByUUID(player.getUniqueId().toString());
    }
}
