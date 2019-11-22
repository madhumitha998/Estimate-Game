import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.player.PlayerHand;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests methods within the PlayerHand class
 *
 * @author  Madhumitha
 * @version 1.0
 */
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

    /**
     * Test the playable cards in player's hand
     */
    @Test
    public void testPlayableHand() {

        // When lead suit is null
        PlayerHand playerhand = new PlayerHand();
        Card testCard = new Card(Suit.HEARTS, Rank.ACE, null );
        Card testCard2 = new Card(Suit.SPADES, Rank.THREE, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.THREE, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.THREE, null );

        playerhand.addCard(testCard);
        playerhand.addCard(testCard2);
        playerhand.addCard(testCard3);
        playerhand.addCard(testCard4);

        ArrayList<Card>  testReturnArray = playerhand.getPlayableHand(null,Suit.DIAMONDS);
        System.out.println(testReturnArray);


        // when trumpsuit both leadSuit and trumpSuit is set
        playerhand = new PlayerHand();
        testCard = new Card(Suit.HEARTS, Rank.ACE, null );
        testCard2 = new Card(Suit.SPADES, Rank.THREE, null );
        testCard3 = new Card(Suit.CLUBS, Rank.THREE, null );
        testCard4 = new Card(Suit.CLUBS, Rank.THREE, null );

        playerhand.addCard(testCard);
        playerhand.addCard(testCard2);
        playerhand.addCard(testCard3);
        playerhand.addCard(testCard4);

        testReturnArray = playerhand.getPlayableHand(Suit.CLUBS,Suit.DIAMONDS);
        System.out.println(testReturnArray);

        // when both null
        playerhand = new PlayerHand();
        testCard = new Card(Suit.HEARTS, Rank.ACE, null );
        testCard2 = new Card(Suit.SPADES, Rank.THREE, null );
        testCard3 = new Card(Suit.CLUBS, Rank.THREE, null );
        testCard4 = new Card(Suit.DIAMONDS, Rank.THREE, null );

        playerhand.addCard(testCard);
        playerhand.addCard(testCard2);
        playerhand.addCard(testCard3);
        playerhand.addCard(testCard4);

        testReturnArray = playerhand.getPlayableHand(null,null);
        System.out.println(testReturnArray);
    }
}