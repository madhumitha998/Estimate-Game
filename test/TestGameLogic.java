import cards.Card;
import cards.*;
import java.util.*;
import estimate.gamelogic.GameLogic;
import estimate.gamelogic.Round;
import estimate.player.ArrayOfPlayers;
import estimate.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameLogic {

    @Test
    public void testNewDeck(){
        GameLogic test = new GameLogic();
        assertEquals(test.getDeck().getSizeOfDeck(), 0);
    }

    /**
     * Test the initialising of deck
     */
    @Test
    public void testInitializeDeck() {
        GameLogic test = new GameLogic();
        test.initialiseDeck();
        assertEquals(test.getDeck().getSizeOfDeck(), 52);
//        Card cardType = new Card();
        assertTrue(test.getDeck().dealCard() instanceof Card);
        System.out.println(test.getDeck().dealCard());
        assertEquals(50, test.getDeck().getNumberOfCardsRemaining());
    }

    /**
     * Tests the setting of player hand
     */
    @Test
    public void testSetPlayerHand() {
//        Check that there are no cards at first
        GameLogic test = new GameLogic();
        test.initialisePlayers();
        test.initialiseDeck();
        ArrayOfPlayers arrayPlayersTest = test.getArrayOfPlayers();
        ArrayList<Card> player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        assertEquals(0, player1Cards.size());

//        Check the right number of cards in hand in 4th round
        test.setPlayersHand(new Round(4));
        arrayPlayersTest = test.getArrayOfPlayers();
        for (Player p : arrayPlayersTest.getArrayOfPlayers()){
            System.out.println(p);
        }
        System.out.println(arrayPlayersTest.getNumberOfPlayers());
        player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        ArrayList<Card> player4Cards = arrayPlayersTest.getPlayerByIndex(3).getHand().getHand();
        assertEquals(4, player1Cards.size());
        assertEquals(4, player4Cards.size());
        assertEquals((52-16),test.getDeck().getNumberOfCardsRemaining() );
    }

    /**
     * Tests the setting of dealer at the start of the round
     */
    @Test
    public void testSetDealerAtStartOfGame() {
        GameLogic test = new GameLogic();
        test.initialisePlayers();
        test.initialiseDeck();
        test.setDealerAtStartOfGame();
        // Output of sysout should always be changing from 0-3 (Dealer should be random based on highest card)
        System.out.println(test.getDealer());
    }

    /**
     *  Test Set Trump
     */
    @Test
    public void testSetTrump() {
        GameLogic test = new GameLogic();
        test.initialisePlayers();
        test.initialiseDeck();
        test.setRound(4);
        test.setPlayersHand(new Round(4));

        // Ensure no printed line in console
        test.setTrump();

        assertTrue(test.getTrumpSuit() instanceof Card);

        System.out.println(test.getTrumpSuit());
    }


    /**
     * Test StartNewGame()
     */

}
