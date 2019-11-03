import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.player.Computer;
import estimate.player.Player;
import estimate.player.PlayerHand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestComputer {
    @Test
    public void percentOfTrumpAndHigherTest() {
        Computer com1 = new Computer(0,3);
        PlayerHand computerHand = new PlayerHand();
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.TWO, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.HEARTS, Rank.TWO, null );
        computerHand.addCard(testCard);
        computerHand.addCard(testCard2);
        computerHand.addCard(testCard3);
        computerHand.addCard(testCard4);

        assertEquals(0.75, com1.percentOfTrumpAndHigher(computerHand));

    }
    @Test
    public void indexOfBidTest() {
        Computer com1 = new Computer(0,3);
        assertEquals(0, com1.indexOfBid(0.25, 4, 2));
        assertEquals(2, com1.indexOfBid(0.65, 4, 1));
    }
    @Test
    public void bidWinningTricksTest() {
        Computer com1 = new Computer(0,3);

    }
}