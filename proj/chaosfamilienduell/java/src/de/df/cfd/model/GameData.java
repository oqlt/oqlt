package de.df.cfd.model;

/**
 *
 * @author Marcel / DreamFlasher
 */
public class GameData {

    private String playersAndTeams[][]; //the first value is the team name
//    private int numberOfTeams = 2;
//    private int numberOfPlayersPerTeam = 5;
    private float roundWeights[];
    private int currentRound = 0;
    private int currentTeam = 0;

    public int getCurrentTeam() {
        return currentTeam;
    }


    public int switchTeam(){
        currentTeam = 1-currentTeam;
        return currentTeam;
    }

    public float[] getRoundWeights() {
        return roundWeights;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void nextRound() {
        currentRound++;
    }

    public String getTeamMemberName(int team, int member) {
        return playersAndTeams[team][member];
    }

    public GameData() {
//        init();
    }

//    public GameData(int numberOfTeams, int numberOfPlayersPerTeam){
//        this.numberOfTeams = numberOfTeams;
//        this.numberOfPlayersPerTeam = numberOfPlayersPerTeam;
//        init();
//    }
    /**
     * rounds is of type float as there will be the weighing of the points be saved
     * @param playersAndTeams
     * @param rounds
     */
    public GameData(String playersAndTeams[][], float rounds[]) {
        this.playersAndTeams = playersAndTeams;
        this.roundWeights = rounds;
    }

//    public void init(){
//        playersAndTeams = new String[numberOfTeams][numberOfPlayersPerTeam];
//    }
    public void setPlayer(int teamNumber, int playerNumber, String playerName) {
        playersAndTeams[teamNumber][playerNumber] = playerName;
    }

    @Override
    public String toString() {
        return "Team1: " + playersAndTeams[0][0] + " Team2: " + playersAndTeams[1][0] + " currentRound: " + currentRound;
    }
}
