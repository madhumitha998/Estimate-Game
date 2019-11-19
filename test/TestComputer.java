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

        assertEquals(testCard4,com1.playCard(trumpSuit,null, null));

    }

    
    // test computer first player of round + bid positive + hand has trump and other cards
    @Test
    public void testFirstPlayer2() {
        Computer com1 = new Computer(2,0);
        com1.setBid(2);
        //normal card
        Card testCard1 = new Card(Suit.DIAMONDS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        //highest card played so far
        Card testCard5 = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        com1.setHand(testCard1);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;

        assertEquals(testCard1,com1.playCard(trumpSuit,null, null));

    }

    // test computer first player of round + bid positive + hand has only trump :
    @Test
    public void testFirstPlayer3() {
        Computer com1 = new Computer(3,0);
        com1.setBid(3);
        Card testCard = new Card(Suit.DIAMONDS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.DIAMONDS, Rank.KING, null );
        //trump card
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;

        assertEquals(testCard3,com1.playCard(trumpSuit,null, null));

    }

    // test computer first player of round + bid == 0  + random cards : returns the smallest ranking card of the smallest suit. Largest suit in descending == trump, lead, normal order
    @Test
    public void testFirstPlayer4() {
        Computer com1 = new Computer(4,0);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.DIAMONDS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        //highest card played so far

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;

        assertEquals(testCard4,com1.playCard(trumpSuit,null, null));
    }


    @Test
    public void testComputerSecondPlayer() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);
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

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Card highestPlayedCard = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,highestPlayedCard.getSuit(), highestPlayedCard));
    }

    @Test // Bids predicted positive + trump highest card + different trump hands + lead suit
    public void testComputerSecondPlayerAndTrumpPlaying() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.NINE, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.SPADES, Rank.KING, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.EIGHT, null );

        assertEquals(testCard,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids predicted positive + No lead suit and trump suit higher + has trump in hand
    public void testComputerSecondPlayerAndNoChoice() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.NINE, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.SPADES, Rank.KING, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.KING, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids predicted positive + No lead suit and trump suit higher + has trump in hand + No lead suit
    public void testComputerSecondPlayerAndNoChoiceNoLead() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.NINE, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.KING, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.KING, null );

        assertEquals(testCard,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids predicted positive + No lead suit and trump suit higher + has trump in hand + No lead suit + No trump
    public void testComputerSecondPlayerAndNoChoiceNoLeadNoTrump() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);
        //normal card
        Card testCard = new Card(Suit.DIAMONDS, Rank.NINE, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.HEARTS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.ACE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.KING, null );

        assertEquals(testCard,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has multiple lead and lower than HPC + trump suit
    public void testComputerSecondPlayerNormalTrynaLose() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.SPADES, Rank.KING, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.SPADES, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.CLUBS, Rank.ACE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.QUEEN, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has multiple lead and lower than HPC + trump suit
    public void testComputerSecondPlayerNormalTrynaLose2() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.SPADES, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.QUEEN, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has multiple lead and lower than HPC + trump suit + HPC Trump
    public void testComputerSecondPlayerNormalTrynaLoseToTRUMP() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.QUEEN, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has multiple lead + trump suit + HPC Lead + Has Only larger
    // cards than trump
    public void testComputerSecondPlayerNormalTrynaLoseToTrumpBUTCANT() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.THREE, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has multiple lead + trump suit + HPC Lead + Has Only larger
    // cards than trump
    public void testComputerSecondPlayerNormalTrynaLoseToTrumpBUTCANT2() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.THREE, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has multiple lead  + HPC Lead + Has Only larger
    // cards than trump
    public void testComputerSecondPlayerNormalTrynaLoseToTrumpBUTCANTAndNoTrump() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.SPADES, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.SPADES, Rank.THREE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.THREE, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has multiple lead  + HPC Lead + Has Only larger
    // cards than trump
    public void testComputerSecondPlayerNormalTrynaLoseToTrumpBUTCANTAndNoTrump2() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.SPADES, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.SPADES, Rank.THREE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.THREE, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has NO lead or trump  + HPC Lead + Has Only larger
    // cards than trump
    public void testComputerSecondPlayerNormalTrynaLoseANDNOCHOICE() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.DIAMONDS, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.HEARTS, Rank.THREE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.THREE, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids Predicted == 0 + has NO lead or trump  + HPC Lead + Has Only larger
    // cards than trump
    public void testComputerSecondPlayerNormalTrynaLoseANDNOCHOICE2() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);
        //normal card
        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.JACK, null );
        //high card
        Card testCard3 = new Card(Suit.DIAMONDS, Rank.NINE, null );
        //trump card
        Card testCard4 = new Card(Suit.HEARTS, Rank.THREE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        //com1 is now first player so no lead suit or highestplayedcard declared yet for the trick
        //pass in null values for these
        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.THREE, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

}