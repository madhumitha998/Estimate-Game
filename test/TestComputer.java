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

        Suit trumpSuit = Suit.HEARTS;
        assertEquals(0.75, com1.percentOfTrumpAndHigher(computerHand,trumpSuit));

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
        int sumOfBidsInTrick = 4;
        Suit trumpSuit = Suit.HEARTS;
        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);
        assertEquals(4,com1.getBid());

    }

    // Test available bid for round 3, when position 0 ; possible bids are 0 - total tricks 
    // Test available bid for round 3, when position 1 ; possible bids are 0 - total tricks 
    // Test available bid for round 3 when position 3 ; possible bids follow the rules. i + ii + iii 
    




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

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.DIAMONDS;
        Card highestPlayedCard = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        assertEquals(testCard3,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));

    }

    @Test
    // test computer first player of round + bid positive + hand has trump and other cards
    public void testFirstPlayer1() {
        Computer com1 = new Computer(1,0);
        //positive bid 1
        com1.setBid(1);
        Card testCard = new Card(Suit.DIAMONDS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Card highestPlayedCard = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,null, null));

    }

    
    // test computer first player of round + bid positive + hand has trump and other cards
    @Test
    public void testFirstPlayer2() {
        Computer com1 = new Computer(2,0);
        com1.setBid(2);
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

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Card highestPlayedCard = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,null, null));

    }

    // test computer first player of round + bid positive + hand has only trump :
    @Test
    public void testFirstPlayer3() {
        Computer com1 = new Computer(3,0);
        com1.setBid(3);
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

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Card highestPlayedCard = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,null, null));

    }

    // test computer first player of round + bid == 0  + random cards : returns the smallest ranking card of the smallest suit. Largest suit in descending == trump, lead, normal order
    @Test
    public void testFirstPlayer4() {
        Computer com1 = new Computer(4,0);
        com1.setBid(4);
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

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Card highestPlayedCard = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,null, null));
    }
    






    // test computer second player + bid positive + hand has lead suit only : choose the lead suit that is larger than highest on the table , but lowest in your hand
    // test computer second player + bid positive + hand has NO lead suit and only trump and other cards : choose the trump suit that is larger than highest on the table , but lowest in your hand
    // test computer second player + bid positive + hand has lead suit that is lower than the table and trump and other cards : choose the trump suit that is larger than highest on the table , but lowest in your hand. Should not choose lead suit because it does not fulfil the first condition
    // test computer second player + bid positive + hand has lead suit only but all lower than the table hand : choose the lowest rank card of the lead suit
    // test computer second player + bid positive + hand has trump suit only but all lower than the table hand : choose the lowest rank card of the trump suit
    // test computer second player + bid positive + hand has trump suit and other cards but all lower than the table hand : choose the lowest rank card of the trump suit
    // test computer second play + bid positive + hand has no trump or lead suit and lower than the table hand : choose the lowest of the ranked cards of the ranked suits (meaning diamond is the lowest, trump is the highest, lead is the second highest)

    // test computer second player + bid == 0 + lead suit only : throws the card that is smaller than the table card but largest in hand
    // test computer second player + bid == 0 + lead suit only but larger than table hand  : throws the largest of lead suit in hand
    // test computer second player + bid == 0 + lead suit and trump suit. Table hand is a trump.  : throws the largest of trump suit in hand that is smaller than the table
     // test computer second player + bid == 0 + both lead suit and trump suit larger than table hand : throw the largest trump card of trump suit in hand
    // test computer second player + bid == 0 +  lead suit only + larger than table hand : throw the largest lead card of lead suit in hand
    // test computer second player + bid == 0 + no lead + no trump cards in hand : throws the largest of suit in ascending order of rank


}