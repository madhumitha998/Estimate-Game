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
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.TWO, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.HEARTS, Rank.TWO, null );
        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);
        assertEquals(4,com1.getHand().getNumberOfCards());
        com1.setIsDealer(true);

        int totTricksInRound = 5;

        com1.bidWinningTricks(totTricksInRound);
        assertEquals(4,com1.getBid());

    }

    @Test
    public void playCardTest() {
        Computer com1 = new Computer(0,3);
        //normal card
        Card testCard = new Card(Suit.DIAMONDS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        //highest card played so far
        Card testCard5 = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.DIAMONDS;
        Card highestPlayedCard = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        assertEquals(testCard3,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));

    }
}