package com.fennekfoxy.huntailhunters.database;

public class PlayerStats {

    private String uuid;
    private String playerName;
    private int wins;

    public PlayerStats(String uuid, String playerName, int wins) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.wins = wins;
    }

    public String getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
