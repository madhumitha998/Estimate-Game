import cards.Card;
import cards.*;
import estimate.gamelogic.PlayerCardArray;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayerCardArray {
    /**
     * Test PlayerCardArray
     */
    @Test
    public void testPlayerCardArray() {
        PlayerCardArray testPCA = new PlayerCardArray(0, new Card(Suit.CLUBS, Rank.ACE,null));

        PlayerCardArray testPCA2 = new PlayerCardArray(1, new Card(Suit.SPADES, Rank.ACE,null));

        assertEquals(-3, testPCA.compareTo(testPCA2));
    }
}
