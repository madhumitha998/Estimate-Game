import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.player.Computer;
import estimate.player.PlayerHand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *
 * @author madhumitha
 * @author Abel.Wong.2017
 * @version 1.1
 */

public class TestComputer {
    @Test
    //1st trick of round 4, dealer, num <= 25%
    public void isDealer25PercentBid() {
        //dealer pos=3
        Computer com1 = new Computer(0,3);
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.TWO, null );
        //normal card
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.FIVE, null );
        //normal card
        Card testCard3 = new Card(Suit.SPADES, Rank.SIX, null );
        //trump card
        Card testCard4 = new Card(Suit.HEARTS, Rank.TWO, null );
        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        int totTricksInRound = 4;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;

        //totTricksInRound = max bid = 4
        //possible bids: [0, 1, 2, 3, 4]
        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);
        //isDealer so bid cannot be totTricksInRound - sumOfBidsInTrick : 4 - 2 = 2
        //final possible bids: [0,1,3,4]
        //median: 1
        //num <= 25%: Pick the possible bid 2 places to the left (or 0): 0
        assertEquals(0,com1.getBid());

    }

    @Test
    //1st trick of round 4, dealer, 25 < num <= 50%
    public void isDealer50PercentBid() {
        //dealer pos=3
        Computer com1 = new Computer(0,3);
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.TWO, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //normal card
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TWO, null );
        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        int totTricksInRound = 4;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;

        //totTricksInRound = max bid = 4
        //possible bids: [0, 1, 2, 3, 4]
        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);
        //isDealer so bid cannot be totTricksInRound - sumOfBidsInTrick : 4 - 2 = 2
        //final possible bids: [0,1,3,4]
        //median: 1
        //25 < num <= 50%: Pick the possible bid 1 places to the left (or 0): 0
        assertEquals(0,com1.getBid());

    }

    @Test
    //1st trick of round 4, dealer, 50 < num <= 75%
    public void isDealer75PercentBid() {
        //dealer pos=3
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

        int totTricksInRound = 4;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;

        //totTricksInRound = max bid = 4
        //possible bids: [0, 1, 2, 3, 4]
        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);
        //isDealer so bid cannot be totTricksInRound - sumOfBidsInTrick : 4 - 2 = 2
        //final possible bids: [0,1,3,4]
        //median: 1
        //num 75% Pick the possible bid 1 place to the right (or max possible): 3
        assertEquals(3,com1.getBid());

    }

    @Test
    //1st trick of round 4, dealer, 75 < num <= 100%
    public void isDealer100PercentBid() {
        //dealer pos=3
        Computer com1 = new Computer(0,3);
        //high card
        Card testCard = new Card(Suit.DIAMONDS, Rank.ACE, null );
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

        int totTricksInRound = 4;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;

        //totTricksInRound = max bid = 4
        //possible bids: [0, 1, 2, 3, 4]
        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);
        //isDealer so bid cannot be totTricksInRound - sumOfBidsInTrick : 4 - 2 = 2
        //final possible bids: [0,1,3,4]
        //median: 1
        //75 < num <= 100%: Pick the possible bid 2 places to the right (or max possible): 4
        assertEquals(4,com1.getBid());

    }

    // 1st trick of round 4, non-dealer, num <= 25%
    @Test
    public void nonDealer25PercentBid() {
        Computer com1 = new Computer(0,2);
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.TWO, null );
        //normal card
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.SEVEN, null );
        //normal card
        Card testCard3 = new Card(Suit.CLUBS, Rank.FOUR, null );
        //trump card
        Card testCard4 = new Card(Suit.HEARTS, Rank.TWO, null );
        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        int totTricksInRound = 4;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;

        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);

        //totTricksInRound = max bid = 4
        //possible bids: [0, 1, 2, 3, 4]
        //median: 2
        //num <= 25%: Pick the possible bid 2 places to the left (or 0): 0
        assertEquals(0,com1.getBid());

    }

    // 1st trick of round 4, non-dealer, 25 < num <= 50%
    @Test
    public void nonDealer50PercentBid() {
        Computer com1 = new Computer(0,1);
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.TWO, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //normal card
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TWO, null );
        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        int totTricksInRound = 4;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;

        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);

        //totTricksInRound = max bid = 4
        //possible bids: [0, 1, 2, 3, 4]
        //median: 2
        //25 < num <= 50%: Pick the possible bid 1 places to the left (or 0): 1
        assertEquals(1,com1.getBid());

    }

    // 1st trick of round 3, non-dealer, 25 < num <= 50%, total tricks in round off number
    @Test
    public void nonDealer50PercentBid_SecondTest() {
        Computer com1 = new Computer(0,3);
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.TWO, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //normal card
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TWO, null );
        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        int totTricksInRound = 5;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;

        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);

        //totTricksInRound = max bid = 3
        //possible bids: [0, 1, 2, 3, 4, 5]
        // actual bids: [0, 1, 2 ,4 ,5]
        //median: 2
        //25 < num <= 50%: Pick the possible bid 1 places to the left (or 0): 0
        assertEquals(1,com1.getBid());

    }

    // 1st trick of round 3, non-dealer, 50 < num <= 75%:
    @Test
    public void nonDealer75PercentBid() {
        Computer com1 = new Computer(0,1);
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


        int totTricksInRound = 4;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;
        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);

        //totTricksInRound = max bid = 4
        //possible bids: [0, 1, 2, 3, 4]
        //median: 2
        //50 < num <= 75%: Pick the possible bid 1 places to the right (or max possible): 3
        assertEquals(3,com1.getBid());

    }

    //1st trick of round 4, non-dealer, 75 < num <= 100%:
    @Test
    public void nonDealer100PercentBid() {
        Computer com1 = new Computer(0,2);
        //high card
        Card testCard = new Card(Suit.DIAMONDS, Rank.ACE, null );
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


        int totTricksInRound = 4;
        int sumOfBidsInTrick = 2;
        Suit trumpSuit = Suit.HEARTS;
        com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);

        //totTricksInRound = max bid = 4
        //possible bids: [0, 1, 2, 3, 4]
        //median: 2
        //75 < num <= 100%: Pick the possible bid 2 places to the right (or max possible): 4
        assertEquals(4,com1.getBid());

    }

        //1st trick of round 5, non-dealer, 75 < num <= 100%:
        @Test
        public void nonDealer80PercentBid() {
            Computer com1 = new Computer(0,2);
            //normal card
            Card testCard = new Card(Suit.DIAMONDS, Rank.THREE, null );
            //high card
            Card testCard1 = new Card(Suit.DIAMONDS, Rank.ACE, null );
            //Trump and high card
            Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
            //high card
            Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
            //trump card
            Card testCard4 = new Card(Suit.HEARTS, Rank.TWO, null );
            com1.setHand(testCard);
            com1.setHand(testCard1);
            com1.setHand(testCard2);
            com1.setHand(testCard3);
            com1.setHand(testCard4);
    
    
            int totTricksInRound = 5;
            int sumOfBidsInTrick = 2;
            Suit trumpSuit = Suit.HEARTS;
            com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);
    
            //totTricksInRound = max bid = 5
            //possible bids: [0, 1, 2, 3, 4, 5]
            //median: 2
            //75 < num <= 100%: Pick the possible bid 2 places to the right (or max possible): 4
            assertEquals(4,com1.getBid());
    
        }

        //1st trick of round 6, non-dealer, 25 < num <= 50%:
        @Test
        public void nonDealer33PercentBid() {
            Computer com1 = new Computer(0,2);
            //normal card
            Card testCard = new Card(Suit.DIAMONDS, Rank.THREE, null );
            //normal card
            Card testCard1 = new Card(Suit.DIAMONDS, Rank.TWO, null );
            //normal card
            Card testCard2 = new Card(Suit.SPADES, Rank.FOUR, null );
            //normal card
            Card testCard3 = new Card(Suit.CLUBS, Rank.FIVE, null );
            //high card
            Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );
            //trump card
            Card testCard5 = new Card(Suit.HEARTS, Rank.FIVE, null );
            com1.setHand(testCard);
            com1.setHand(testCard1);
            com1.setHand(testCard2);
            com1.setHand(testCard3);
            com1.setHand(testCard4);
            com1.setHand(testCard5);
    
    
            int totTricksInRound = 6;
            int sumOfBidsInTrick = 2;
            Suit trumpSuit = Suit.HEARTS;
            com1.bidWinningTricks(totTricksInRound, sumOfBidsInTrick, trumpSuit);
    
            //totTricksInRound = max bid = 6
            //possible bids: [0, 1, 2, 3, 4, 5, 6]
            //median: 3
            //25 < num <= 50%: Pick the possible bid 1 places to the left (or 0): 2
            assertEquals(2,com1.getBid());
    
        }
    

    @Test
    public void playCardTest() {
        Computer com1 = new Computer(0,3);

        Card testCard = new Card(Suit.DIAMONDS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
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

        assertEquals(testCard,com1.playCard(trumpSuit,null, null));

    }

    
    // test computer first player of round + bid positive + hand has trump and other cards
    @Test
    public void testFirstPlayer2() {
        Computer com1 = new Computer(2,0);
        com1.setBid(2);

        Card testCard1 = new Card(Suit.DIAMONDS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );

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
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.DIAMONDS, Rank.KING, null );
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

    // Bids predicted positive + trump + lead suit + normal cards
    @Test
    public void testComputerSecondPlayer() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);

        Card testCard = new Card(Suit.DIAMONDS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Card highestPlayedCard = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,highestPlayedCard.getSuit(), highestPlayedCard));
    }

    @Test // Bids predicted positive + trump highest card + different trump hands + lead suit
    public void testComputerSecondPlayerAndTrumpPlaying() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);

        Card testCard = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.SPADES, Rank.KING, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.EIGHT, null );

        assertEquals(testCard,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids predicted positive + No lead suit and trump suit higher + has trump in hand
    public void testComputerSecondPlayerAndNoChoice() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);

        Card testCard = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.SPADES, Rank.KING, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.KING, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    @Test // Bids predicted positive + No lead suit and trump suit higher + has trump in hand + No lead suit
    public void testComputerSecondPlayerAndNoChoiceNoLead() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);

        Card testCard = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.KING, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.KING, null );

        assertEquals(testCard,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids predicted positive + No lead suit and trump suit higher + has trump in hand + No lead suit + No trump
    @Test
    public void testComputerSecondPlayerAndNoChoiceNoLeadNoTrump() {
        Computer com1 = new Computer(4,1);
        com1.setBid(4);

        Card testCard = new Card(Suit.DIAMONDS, Rank.NINE, null );
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        Card testCard3 = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.ACE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.KING, null );

        assertEquals(testCard,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids Predicted == 0 + has multiple lead and lower than HPC + trump suit
    @Test
    public void testComputerSecondPlayerNormalTrynaLose() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.SPADES, Rank.KING, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        Card testCard3 = new Card(Suit.SPADES, Rank.NINE, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.ACE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.QUEEN, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

     // Bids Predicted == 0 + has multiple lead and lower than HPC + trump suit
     @Test
    public void testComputerSecondPlayerNormalTrynaLose2() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        Card testCard3 = new Card(Suit.SPADES, Rank.NINE, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.QUEEN, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids Predicted == 0 + has multiple lead and lower than HPC + trump suit + HPC Trump
    @Test
    public void testComputerSecondPlayerNormalTrynaLoseToTRUMP() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.QUEEN, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids Predicted == 0 + has multiple lead + trump suit + HPC Lead + Has Only larger
    // cards than trump
    @Test
    public void testComputerSecondPlayerNormalTrynaLoseToTrumpBUTCANT() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.THREE, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids Predicted == 0 + has multiple lead + trump suit + HPC Lead + Has Only larger
    // cards than trump
    @Test
    public void testComputerSecondPlayerNormalTrynaLoseToTrumpBUTCANT2() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.JACK, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.THREE, null );

        assertEquals(testCard4,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids Predicted == 0 + has multiple lead  + HPC Lead + Has Only larger
    // cards than trump
     @Test
    public void testComputerSecondPlayerNormalTrynaLoseToTrumpBUTCANTAndNoTrump() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        Card testCard3 = new Card(Suit.SPADES, Rank.NINE, null );
        Card testCard4 = new Card(Suit.SPADES, Rank.THREE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.THREE, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids Predicted == 0 + has multiple lead  + HPC Lead + Has Only larger
    // cards than trump
    @Test
    public void testComputerSecondPlayerNormalTrynaLoseToTrumpBUTCANTAndNoTrump2() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.JACK, null );
        Card testCard3 = new Card(Suit.SPADES, Rank.NINE, null );
        Card testCard4 = new Card(Suit.SPADES, Rank.THREE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.THREE, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids Predicted == 0 + has NO lead or trump  + HPC Lead + Has Only larger
    // cards than trump
    @Test
    public void testComputerSecondPlayerNormalTrynaLoseANDNOCHOICE() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.JACK, null );
        Card testCard3 = new Card(Suit.DIAMONDS, Rank.NINE, null );
        Card testCard4 = new Card(Suit.HEARTS, Rank.THREE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.SPADES, Rank.THREE, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

    // Bids Predicted == 0 + has NO lead or trump  + HPC Lead + Has Only larger
    // cards than trump
    @Test
    public void testComputerSecondPlayerNormalTrynaLoseANDNOCHOICE2() {
        Computer com1 = new Computer(4,1);
        com1.setBid(0);

        Card testCard = new Card(Suit.HEARTS, Rank.JACK, null );
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.JACK, null );
        Card testCard3 = new Card(Suit.DIAMONDS, Rank.NINE, null );
        Card testCard4 = new Card(Suit.HEARTS, Rank.THREE, null );

        com1.setHand(testCard);
        com1.setHand(testCard2);
        com1.setHand(testCard3);
        com1.setHand(testCard4);

        Suit trumpSuit = Suit.CLUBS;
        Suit leadSuit = Suit.SPADES;
        Card highestPlayedCard = new Card(Suit.CLUBS, Rank.THREE, null );

        assertEquals(testCard2,com1.playCard(trumpSuit,leadSuit, highestPlayedCard));
    }

}