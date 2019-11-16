package estimate.scoreboard;
import java.util.*;

/**
 * Scoreboard2
 */
public class Scoreboard {
    private Map<Integer, Score> scoreboardDetails = new HashMap<>();
    private Map<Integer, Integer> totalScore = new HashMap<>();

    /**
     * Initialises the scoreboard for all rounds
     */
    public Scoreboard() {
        Score round1 = new Score();
        Score round2 = new Score();
        Score round3 = new Score();
        Score round4 = new Score();
        Score round5 = new Score();
        Score round6 = new Score();
        Score round7 = new Score();
        Score round8 = new Score();
        Score round9 = new Score();
        Score round10 = new Score();
        Score round11 = new Score();
        scoreboardDetails.put(1, round1);
        scoreboardDetails.put(2, round2);
        scoreboardDetails.put(3, round3);
        scoreboardDetails.put(4, round4);
        scoreboardDetails.put(5, round5);
        scoreboardDetails.put(6, round6);
        scoreboardDetails.put(7, round7);
        scoreboardDetails.put(8, round8);
        scoreboardDetails.put(9, round9);
        scoreboardDetails.put(10, round10);
        scoreboardDetails.put(11, round11);

    }

    /**
     * Prints score for given round
     * @param round
     */
    public void printScoreForRound(int round) {
        Score scoreDeets = scoreboardDetails.get(round);
        System.out.println("Round " + round);
        scoreDeets.printAllDetails();
    }

    /**
     * Prints Score for ALl Rounds
     */
    public void printScoreForAllRounds() {
        for (int i = 1 ; i <= 11 ; i++) {
            Score scoreDeets = scoreboardDetails.get(i);
            System.out.println("Round " + i);
            scoreDeets.printAllDetails();
            System.out.println("********************");
            System.out.println();
        }
    }


    /**
     * Adds predicted bid to player in given round
     * @param round starts from 1 to 11
     * @param playerId starts from 0 to 3
     * @param predictedBid
     */
    public void addPrediction(int round, int playerId, int predictedBid) {
        Score scoreDeets = scoreboardDetails.get(round);
        scoreDeets.addPrediction(playerId, predictedBid);
    }

    /**
     * Add trick won to winning player after each trick ends
     * @param round
     * @param playerId
     */
    public void addTricksWon(int round, int playerId) {
        Score scoreDeets = scoreboardDetails.get(round);
        scoreDeets.addTricksWon(playerId);
    }

    /**
     * Calculate the score for the current round. Use this at the end of each round
     * @param round
     */
    public void calculateRoundScore(int round) {
        Score scoreDeets = scoreboardDetails.get(round);
        scoreDeets.calculateScore();
    }

    /**
     * Calculate the total score thus far and saves it into class variable
     */
    public void calculateTotalScore() {
        int firstPlayer = 0;
        int secondPlayer = 0;
        int thirdPlayer = 0;
        int fourthPlayer = 0;

        for (int i = 1 ; i <= 11 ; i++) {
            Score scoreDeets = scoreboardDetails.get(i);
            Map<Integer, Integer> scores = scoreDeets.getScore();
            firstPlayer += scores.get(0);
            secondPlayer += scores.get(1);
            thirdPlayer += scores.get(2);
            fourthPlayer += scores.get(3);
        }
        totalScore.put(0, firstPlayer);
        totalScore.put(1, secondPlayer);
        totalScore.put(2, thirdPlayer);
        totalScore.put(3, fourthPlayer);
    }

    /**
     * returns totalScore Variable
     * @return
     */
    public Map<Integer, Integer> getTotalScore() {
        return this.totalScore;
    }

    /**
     * Returns winner in the form [playerId, score(int) ]
     * @param round
     * @return
     */
    public int[] getWinner(int round) {
        int highestScoringPlayerId = -1;
        int highestScoringPlayerScore = -1;
        for (Map.Entry<Integer,Integer> i : this.totalScore.entrySet()) {
            int playerId = i.getKey();
            int playerTotalScore = i.getValue();

            if (playerTotalScore == 100 && round != 11 ) {
                int[] winner = new int[]{playerId, playerTotalScore};
                return winner;
            } else if (round == 11 ) {
                if (playerTotalScore > highestScoringPlayerScore) {
                    highestScoringPlayerScore = playerTotalScore;
                    highestScoringPlayerId = playerId;
                }
            }
        }
        if (highestScoringPlayerId != -1) {
            return new int[]{highestScoringPlayerId, highestScoringPlayerScore};
        }
        return null;
    }

}