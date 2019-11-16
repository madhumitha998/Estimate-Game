import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.player.Computer;
import estimate.player.Player;
import estimate.player.PlayerHand;
import estimate.player.SelectCard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSelectCard {
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

        assertEquals(testCard, selectCard.pickSmallestCard(computerHand, Suit.SPADES));
        assertEquals(testCard4, selectCard.pickSmallestCard(computerHand, Suit.CLUBS));
    }

    @Test
    public void pickLargestCardTest(){
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

        assertEquals(testCard2, selectCard.pickLargestCard(computerHand, Suit.SPADES));
        assertEquals(testCard3, selectCard.pickLargestCard(computerHand, Suit.HEARTS));
    }

    @Test
    public void pickBestCard1Test(){
        SelectCard selectCard = new SelectCard();
        PlayerHand computerHand = new PlayerHand();
        //normal card
        Card testCard = new Card(Suit.DIAMONDS, Rank.FIVE, null );
        //Trump and high card
        Card testCard2 = new Card(Suit.HEARTS, Rank.TEN, null );
        //high card
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        //trump card
        Card testCard4 = new Card(Suit.DIAMONDS, Rank.TEN, null );
        //highest card played so far
        Card testCard5 = new Card(Suit.DIAMONDS, Rank.EIGHT, null );

        computerHand.addCard(testCard);
        computerHand.addCard(testCard2);
        computerHand.addCard(testCard3);
        computerHand.addCard(testCard4);

        assertEquals(testCard4, selectCard.pickBestCard1(computerHand, Suit.CLUBS,Suit.DIAMONDS,testCard5) );
        
    }

    @Test
    public void pickBestCard2Test(){
        SelectCard selectCard = new SelectCard();
        PlayerHand computerHand = new PlayerHand();
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

        computerHand.addCard(testCard);
        computerHand.addCard(testCard2);
        computerHand.addCard(testCard3);
        computerHand.addCard(testCard4);

        assertEquals(testCard3, selectCard.pickBestCard2(computerHand, Suit.CLUBS,Suit.DIAMONDS,testCard5) );
        
    }


}