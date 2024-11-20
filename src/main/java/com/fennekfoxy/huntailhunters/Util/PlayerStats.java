package com.fennekfoxy.huntailhunters.Util;

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

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
}
