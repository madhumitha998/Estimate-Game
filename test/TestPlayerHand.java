import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.player.PlayerHand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPlayerHand {
    @Test
    public void playerHandTest() {
        PlayerHand playerhand = new PlayerHand();
        Card testCard = new Card(Suit.CLUBS, Rank.ACE, null );
        Card testCard2 = new Card(Suit.CLUBS, Rank.THREE, null );
        assertEquals(0, playerhand.getNumberOfCards());
        playerhand.addCard(testCard);
        playerhand.addCard(testCard2);
        assertEquals(2, playerhand.getNumberOfCards());

        //getHand
        ArrayList<Card> testArrList = new ArrayList<>();
        testArrList.add(testCard);
        testArrList.add(testCard2);
        assertEquals(testArrList, playerhand.getHand());
        assertEquals("Three of Clubs", playerhand.getHand().get(1).toString());

        //get specific card from playerHand
        assertEquals(testCard, playerhand.getCard(0));
        assertEquals("Ace of Clubs", playerhand.getCard(0).toString());



    }
}