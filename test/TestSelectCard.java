import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.player.PlayerHand;
import estimate.player.SelectCard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSelectCard {
    /**
     * Picks the smallest card depending on Trump
     */
    @Test
    public void pickSmallestCardTest(){
        SelectCard selectCard = new SelectCard();
        PlayerHand computerHand = new PlayerHand();
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.FIVE, null );
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

        assertEquals(testCard, selectCard.pickCardFirstPlayerNegative(computerHand, Suit.SPADES));
        assertEquals(testCard4, selectCard.pickCardFirstPlayerNegative(computerHand, Suit.CLUBS));
    }

    /**
     *
     */
    @Test
    public void pickLargestCardTest1(){
        SelectCard selectCard = new SelectCard();
        PlayerHand computerHand = new PlayerHand();
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.FIVE, null );
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

        assertEquals(testCard3, selectCard.pickCardFirstPlayerPositive(computerHand, Suit.SPADES));
        assertEquals(testCard2, selectCard.pickCardFirstPlayerPositive(computerHand, Suit.CLUBS));
    }

    /**
     *
     */
    @Test
    public void pickLargestCardTest2(){
        SelectCard selectCard = new SelectCard();
        PlayerHand computerHand = new PlayerHand();
        //normal card
        Card testCard = new Card(Suit.CLUBS, Rank.FIVE, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.CLUBS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.HEARTS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.CLUBS, Rank.TWO, null );
        computerHand.addCard(testCard);
        computerHand.addCard(testCard2);
        computerHand.addCard(testCard3);
        computerHand.addCard(testCard4);

        assertEquals(testCard2, selectCard.pickCardFirstPlayerPositive(computerHand, Suit.HEARTS));
        assertEquals(testCard3, selectCard.pickCardFirstPlayerPositive(computerHand, Suit.CLUBS));
    }

    @Test
    public void pickBestCard1Test(){
        SelectCard selectCard = new SelectCard();
        PlayerHand computerHand = new PlayerHand();
        //
        Card testCard = new Card(Suit.DIAMONDS, Rank.FIVE, null );
        //
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        //highest card played so far
        Card testCard5 = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        computerHand.addCard(testCard);
        computerHand.addCard(testCard2);
        computerHand.addCard(testCard3);
        computerHand.addCard(testCard4);

        assertEquals(testCard4, selectCard.pickCardNextPlayerPositive(computerHand, Suit.CLUBS,Suit.DIAMONDS,testCard5) );
        
    }

    @Test
    public void pickBestCard2Test(){
        SelectCard selectCard = new SelectCard();
        PlayerHand computerHand = new PlayerHand();
        //
        Card testCard = new Card(Suit.DIAMONDS, Rank.JACK, null );
        //
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        //highest card played so far
        Card testCard5 = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        computerHand.addCard(testCard);
        computerHand.addCard(testCard2);
        computerHand.addCard(testCard3);
        computerHand.addCard(testCard4);

        assertEquals(testCard3, selectCard.pickCardNextPlayerNegative(computerHand, Suit.CLUBS,Suit.DIAMONDS,testCard5) );
        
    }


}