import org.junit.jupiter.api.Test;
import estimate.scoreboard.Score;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestScore {

    @Test
    public void testAddPrediction() {
        Score score = new Score();
        score.printAllDetails();
        score.addPrediction(0, 1);
        System.out.println();
        score.printAllDetails();
    }

    @Test
    public void testAddTricksWon() {
        Score score = new Score();
        score.printAllDetails();
        score.addTricksWon(0);
        System.out.println();
        score.printAllDetails();
    }

    @Test
    public void testCalculateScore() {
        Score score = new Score();
        score.printAllDetails();
//        score.addPrediction(0, 1);
        score.addPrediction(1, 1);
        score.addPrediction(2, 2);
        score.addTricksWon(1);
        score.addTricksWon(0);
        System.out.println();
        score.calculateScore();
        score.printAllDetails();

        System.out.println(score.getScore());

    }


}
