import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.player.Computer;
import estimate.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestComputer {
    @Test
    public void computerTest() {
        Computer com1 = new Computer(0,3);
        Card testCard = new Card(Suit.CLUBS, Rank.ACE, null );

        assertEquals(0, com1.getPlayerId());

        assertEquals(3, com1.getPosition());

        com1.setPosition(1);
        assertEquals(1, com1.getPosition());

        // Test Hand
        assertTrue(com1.getHand() != null);

    }
}