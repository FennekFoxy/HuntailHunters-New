package com.fennekfoxy.huntailhunters.database;

import com.fennekfoxy.huntailhunters.HuntailHunters;
import org.bukkit.entity.Player;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class PlayerStatsService {

    public CompletableFuture<PlayerStats> getPlayerStats(Player player) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return HuntailHunters.getPlugin().getDatabase().findPlayerStatsByUUID(player.getUniqueId().toString());
            } catch (SQLException e) {
                HuntailHunters.getPlugin().getLogger().log(Level.SEVERE, "An error occurred while fetching stats for player: " + player.getName(), e);
                return null;
            }
        });
    }
}
