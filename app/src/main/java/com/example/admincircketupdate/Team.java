package com.example.admincircketupdate;

public class Team {

    private String score;
    private String overs;

    public Team() {
        // Default constructor required for calls to DataSnapshot.getValue(Team.class)
    }

    public Team(String teamName, String score, String overs) {

        this.score = score;
        this.overs = overs;
    }


    public String getScore() {
        return score;
    }

    public String getOvers() {
        return overs;
    }
}
