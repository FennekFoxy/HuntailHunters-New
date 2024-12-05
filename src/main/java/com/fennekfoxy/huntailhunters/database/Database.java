package com.fennekfoxy.huntailhunters.database;

import com.fennekfoxy.huntailhunters.HuntailHunters;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class Database {
    private final HuntailHunters plugin;
    private HikariDataSource dataSource;

    public Database(HuntailHunters plugin) {
        this.plugin = plugin;
    }

    /**
     * Initialize the connection pool.
     */
    public void initializeConnectionPool() {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(HuntailHunters.getPlugin().getConfig().getString("database.url"));
            config.setUsername(HuntailHunters.getPlugin().getConfig().getString("database.username"));
            config.setPassword(HuntailHunters.getPlugin().getConfig().getString("database.password"));
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setIdleTimeout(30000);
            config.setConnectionTimeout(30000);
            config.setMaxLifetime(1800000);

            dataSource = new HikariDataSource(config);

            plugin.getLogger().info("Database connection pool initialized successfully.");
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Error initializing database connection pool", e);
        }
    }

    /**
     * Close the connection pool.
     */
    public void closeConnectionPool() {
        if (dataSource != null) {
            dataSource.close();
            plugin.getLogger().info("Database connection pool closed.");
        }
    }

    /**
     * Get a connection from the pool.
     *
     * @return A database connection.
     * @throws SQLException If no connection is available or the pool is not initialized.
     */
    public Connection getConnection() throws SQLException {
        if (dataSource == null) {
            throw new SQLException("DataSource is not initialized.");
        }
        return dataSource.getConnection();
    }

    /**
     * Insert player stats into the database asynchronously.
     *
     * @param stats The PlayerStats object containing the player's information.
     */
    public void insertPlayerStats(PlayerStats stats) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            String query = "INSERT INTO player_stats (uuid, player_name, wins) VALUES (?, ?, ?)";
            try (Connection connection = getConnection();
                 var statement = connection.prepareStatement(query)) {

                statement.setString(1, stats.getUuid());
                statement.setString(2, stats.getPlayerName());
                statement.setInt(3, stats.getWins());
                statement.executeUpdate();

                plugin.getLogger().info("Inserted stats for player: " + stats.getPlayerName());
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Error inserting player stats", e);
            }
        });
    }

    /**
     * Update player stats in the database asynchronously.
     *
     * @param stats The PlayerStats object containing the updated player's information.
     */
    public void updatePlayerStats(PlayerStats stats) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            String query = "UPDATE player_stats SET player_name = ?, wins = ? WHERE uuid = ?";
            try (Connection connection = getConnection();
                 var statement = connection.prepareStatement(query)) {

                statement.setString(1, stats.getPlayerName());
                statement.setInt(2, stats.getWins());
                statement.setString(3, stats.getUuid());
                statement.executeUpdate();

                plugin.getLogger().info("Updated stats for player: " + stats.getPlayerName());
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Error updating player stats", e);
            }
        });
    }

    /**
     * Find player stats by UUID asynchronously.
     *
     * @param uuid The UUID of the player to find.
     * @return A CompletableFuture containing the PlayerStats object or null if not found.
     */
    public CompletableFuture<PlayerStats> findPlayerStatsByUUID(String uuid) {
        return CompletableFuture.supplyAsync(() -> {
            String query = "SELECT * FROM player_stats WHERE uuid = ?";
            try (Connection connection = getConnection();
                 var statement = connection.prepareStatement(query)) {

                statement.setString(1, uuid);
                var resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    return new PlayerStats(
                            uuid,
                            resultSet.getString("player_name"),
                            resultSet.getInt("wins")
                    );
                } else {
                    plugin.getLogger().info("No stats found for player with UUID: " + uuid);
                    return null;
                }
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Error fetching player stats", e);
                return null;
            }
        });
    }
}
