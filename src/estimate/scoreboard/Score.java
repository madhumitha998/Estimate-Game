
package estimate.scoreboard;
import java.util.*;

/**
 * Score2
 */
public class Score {

    private Map<String, Map<Integer, Integer>> scores = new HashMap<>();

    public Score() {
        Map<Integer, Integer> playerPredictions = new HashMap<>();
        playerPredictions.put(0,0);
        playerPredictions.put(1,0);
        playerPredictions.put(2,0);
        playerPredictions.put(3,0);
        scores.put("predictions", playerPredictions);

        Map<Integer, Integer> tricksWon = new HashMap<>();
        tricksWon.put(0,0);
        tricksWon.put(1,0);
        tricksWon.put(2,0);
        tricksWon.put(3,0);
        scores.put("tricksWon", tricksWon);

        Map<Integer, Integer> roundScore = new HashMap<>();
        roundScore.put(0,0);
        roundScore.put(1,0);
        roundScore.put(2,0);
        roundScore.put(3,0);
        scores.put("roundScore", roundScore);
    }

    /**
     * prints the hashmap in the following format
     * roundScore : {0:0, 1:0, 2:0, 3:0,  }
     * tricksWon : {0:0, 1:0, 2:0, 3:0,  }
     * predictions : {0:0, 1:0, 2:0, 3:0,  }
     */
    public void printAllDetails() {
        for (Map.Entry<String, Map<Integer, Integer>> i : scores.entrySet()) {
            String key = i.getKey();
            Map<Integer, Integer> value = i.getValue();

            String printedDeets = key + " : {";

            for (Map.Entry<Integer, Integer> j : value.entrySet()) {
                int playerID = j.getKey();
                int playerDeets = j.getValue();

                printedDeets +=  playerID + ":" + playerDeets + ", ";
            }
            printedDeets += " }";
            System.out.println(printedDeets);
        }

    }

    /**
     * Adds a predicted bid for a player. PlayerId starts from 0 to 3
     * @param playerId 0 -3
     * @param predictedBid
     */
    public void addPrediction(int playerId, int predictedBid) {
        Map<Integer, Integer> playerPredictions = scores.get("predictions");
        playerPredictions.put(playerId, predictedBid);
}

    /**
     * Adds 1 trick to the winner of the trick
     * @param playerId
     */
    public void addTricksWon(int playerId) {
        Map<Integer, Integer> playerTricks = scores.get("tricksWon");
        int existingTricks = playerTricks.get(playerId);
        existingTricks++;
        playerTricks.put(playerId, existingTricks);
    }

    /**
     * Calculates the score based on predicted bids and tricks won
     */
    public void calculateScore() {
        Map<Integer, Integer> playerPredictions = scores.get("predictions");
        Map<Integer, Integer> playerTricks = scores.get("tricksWon");
        Map<Integer, Integer> playerScore = scores.get("roundScore");

        for (int i = 0 ; i < 4 ; i ++) {
            int playerPred = playerPredictions.get(i);
            int playerTrick = playerTricks.get(i);

            int rawScore = 10 + playerPred;
            if (playerPred != playerTrick) {
                rawScore = -rawScore;
            }
            playerScore.put(i, rawScore);
        }
    }

    /**
     * Returns the score
     * @return key is playerId, value is score
     */
    public Map<Integer, Integer> getScore() {
        return scores.get("roundScore");
    }

    /**
     * Returns total bids for the round
     * @return
     */
    public int getTotalBids() {
        int totalBidsSoFar =0;
        for (int i = 0 ; i < 4 ; i ++) {
            int predictionForPlayer = scores.get("predictions").get(i);
            totalBidsSoFar += predictionForPlayer;
        }
        return totalBidsSoFar;

    }





}