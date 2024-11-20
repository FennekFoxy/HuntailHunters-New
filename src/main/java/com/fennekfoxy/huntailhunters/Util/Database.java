package com.fennekfoxy.huntailhunters.Util;

import java.sql.*;

public class Database {

    private Connection connection;

    public Connection getConnection() throws SQLException {

        if (connection != null){
            return connection;
        }
        String url = "jdbc:mysql://localhost/stats_tracker";
        String user = "root";
        String password = "";

        this.connection = DriverManager.getConnection(url, user, password);

        System.out.println("Connected to the Stat Tracker Database.");

        return this.connection;
    }

    public void initializeDatabase()throws SQLException{

        Statement statement = getConnection().createStatement();
        String sql = "CREATE TABLE IF NOT EXISTS player(uuid varchar(36) primary key, playerName varchar(25), wins int)";
        statement.execute(sql);

        statement.close();

        System.out.println("Created the stats table");
    }
    public PlayerStats findPlayerStatsByUUID(String uuid) throws SQLException{

        PreparedStatement statement = getConnection().prepareStatement("SELECT * FROM player WHERE uuid = ?");
        statement.setString(1, uuid);

        ResultSet results = statement.executeQuery();

        if (results.next()){

            String playerName = results.getString("playerName");
            int wins = results.getInt("wins");

            PlayerStats playerStats = new PlayerStats(uuid, playerName, wins);

            statement.close();

            return playerStats;
        }

        statement.close();

        return null;
    }

    public void insertPlayerStats(PlayerStats stats) throws SQLException{

        PreparedStatement statement = getConnection().prepareStatement("INSERT INTO player(uuid, playerName, wins) VALUES (?,?,?)");

        statement.setString(1,stats.getUuid());
        statement.setString(2,stats.getPlayerName());
        statement.setInt(3, stats.getWins());

        statement.executeUpdate();
        statement.close();
    }
    public void updatePlayerStats(PlayerStats stats) throws SQLException{

        PreparedStatement statement = getConnection()
                .prepareStatement("UPDATE player SET playerName = ?, wins = ? WHERE uuid = ?");

        statement.setString(1,stats.getPlayerName());
        statement.setInt(2, stats.getWins());
        statement.setString(3,stats.getUuid());

        statement.executeUpdate();
        statement.close();
    }
}
