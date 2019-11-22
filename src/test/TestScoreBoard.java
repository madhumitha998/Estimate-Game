import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import estimate.scoreboard.*;

public class TestScoreboard {

    /**
     * Adds prediction for round to the overallscoreboard
     */
    @Test
    public void testPredictionToScoreboard() {
        Scoreboard testScoreboard = new Scoreboard();
        testScoreboard.printScoreForRound(1);
        testScoreboard.addPrediction(1, 0, 3);
        System.out.println();
        testScoreboard.printScoreForRound(1);
    }

    /**
     * Prints all the scores for all rounds
     */
    @Test
    public void testPrintAllScores() {
        Scoreboard testScoreboard = new Scoreboard();
        testScoreboard.printScoreForAllRounds();
    }

    /**
     * Test calculation for a score in a round
     */
    @Test
    public void testCalculateRoundScore() {
        Scoreboard testScoreboard = new Scoreboard();
        testScoreboard.printScoreForRound(1);
        testScoreboard.addPrediction(1,1, 1);
        testScoreboard.addPrediction(1,2, 2);
        testScoreboard.addTricksWon(1, 1);
        System.out.println();
        testScoreboard.calculateRoundScore(1);
        testScoreboard.printScoreForRound(1);
    }

    /**
     * Calculates the score base on all the results
     */
    @Test
    public void testCalculateTotalScore() {
        Scoreboard testScoreboard = new Scoreboard();
        testScoreboard.addPrediction(1,1, 1);
        testScoreboard.addPrediction(1,2, 2);
        testScoreboard.addTricksWon(1, 1);
        testScoreboard.addPrediction(2,1, 1);
        testScoreboard.addPrediction(2,2, 2);
        testScoreboard.addTricksWon(2, 1);
        System.out.println();
        testScoreboard.calculateRoundScore(1);
        testScoreboard.calculateRoundScore(2);
        System.out.println();
        testScoreboard.calculateTotalScore();
        System.out.println(testScoreboard.getTotalScore());
    }

    /**
     * returns the winner of the game, if any
     */
    @Test
    public void testGetWinner() {
        Scoreboard testScoreboard = new Scoreboard();
        testScoreboard.addPrediction(1,1, 40);
        testScoreboard.addPrediction(1,2, 2);
        for (int i = 0 ; i < 40; i ++) {
            testScoreboard.addTricksWon(1, 1);
        }
        testScoreboard.addPrediction(2,1, 50);
        testScoreboard.addPrediction(2,2, 2);
        for (int i = 0 ; i < 50; i ++) {
            testScoreboard.addTricksWon(2, 1);
        }
        System.out.println();
        testScoreboard.calculateRoundScore(1);
        testScoreboard.calculateRoundScore(2);
        System.out.println();
        testScoreboard.calculateTotalScore();
        System.out.println(testScoreboard.getTotalScore());

        System.out.println(testScoreboard.getWinner(11)[0]);
        System.out.println(testScoreboard.getWinner(11)[1]);

        Scoreboard testScoreboard2 = new Scoreboard();
        testScoreboard2.addPrediction(1,1, 40);
        testScoreboard2.addPrediction(1,2, 2);
        for (int i = 0 ; i < 40; i ++) {
            testScoreboard2.addTricksWon(1, 1);
        }
        testScoreboard2.addPrediction(2,1, 41);
        testScoreboard2.addPrediction(2,2, 2);
        for (int i = 0 ; i < 50; i ++) {
            testScoreboard2.addTricksWon(2, 1);
        }
        System.out.println();
        testScoreboard2.calculateRoundScore(1);
        testScoreboard2.calculateRoundScore(2);
        System.out.println();
        testScoreboard2.calculateTotalScore();
        System.out.println(testScoreboard2.getTotalScore());

        System.out.println(testScoreboard2.getWinner(4));
        System.out.println(testScoreboard2.getWinner(4));
    }

}
