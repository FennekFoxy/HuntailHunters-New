package com.fennekfoxy.huntailhunters.database;

import com.fennekfoxy.huntailhunters.HuntailHunters;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;

public class Database {

    private final HikariDataSource dataSource;

    public Database() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(HuntailHunters.getPlugin().getConfig().getString("database.url"));
        config.setUsername(HuntailHunters.getPlugin().getConfig().getString("database.username"));
        config.setPassword(HuntailHunters.getPlugin().getConfig().getString("database.password"));
        config.setMaximumPoolSize(10);
        config.setIdleTimeout(30000);

        this.dataSource = new HikariDataSource(config);
    }

    // Get a connection from the pool
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void initializeDatabase() throws SQLException {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS player(uuid varchar(36) primary key, playerName text, wins int)";
            statement.execute(sql);
        }
    }

    public PlayerStats findPlayerStatsByUUID(String uuid) throws SQLException {
        String sql = "SELECT * FROM player WHERE uuid = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, uuid);
            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    String playerName = results.getString("playerName");
                    int wins = results.getInt("wins");
                    return new PlayerStats(uuid, playerName, wins);
                }
            }
        }
        return null;
    }

    public void insertPlayerStats(PlayerStats stats) throws SQLException {
        String sql = "INSERT INTO player(uuid, playerName, wins) VALUES (?,?,?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, stats.getUuid());
            statement.setString(2, stats.getPlayerName());
            statement.setInt(3, stats.getWins());
            statement.executeUpdate();
        }
    }

    public void updatePlayerStats(PlayerStats stats) throws SQLException {
        String sql = "UPDATE player SET playerName = ?, wins = ? WHERE uuid = ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, stats.getPlayerName());
            statement.setInt(2, stats.getWins());
            statement.setString(3, stats.getUuid());
            statement.executeUpdate();
        }
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
