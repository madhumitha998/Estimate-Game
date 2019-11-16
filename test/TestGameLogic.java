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
        test.setDealer(1);

        test.initialiseDeck();
        ArrayOfPlayers arrayPlayersTest = test.getArrayOfPlayers();
        ArrayList<Card> player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        assertEquals(0, player1Cards.size());

//        Check the right number of cards in hand in 4th round
        test.setPlayersHand(new Round(1));

        test.setPlayerOrder(1);

        arrayPlayersTest = test.getArrayOfPlayers();
        for (Player p : arrayPlayersTest.getArrayOfPlayers()){
            System.out.println(p);
        }
        System.out.println(arrayPlayersTest.getNumberOfPlayers());
        player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        ArrayList<Card> player4Cards = arrayPlayersTest.getPlayerByIndex(3).getHand().getHand();
        assertEquals(1, player1Cards.size());
        assertEquals(1, player4Cards.size());
        assertEquals((52-4),test.getDeck().getNumberOfCardsRemaining() );

        System.out.println(Arrays.deepToString(arrayPlayersTest.getArrayOfPlayers().toArray()));
    }

    /**
     * Tests the setting of dealer at the start of the round
     */
    @Test
    public void testSetDealerAtStartOfGame() {
        GameLogic test = new GameLogic();
        test.initialisePlayers();
        test.initialiseDeck();
        test.setDealer(1);
        // Output of sysout should always be changing from 0-3 (Dealer should be random based on highest card)
        System.out.println(test.getDealer());
        test.setDealer(3);
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
        test.setRound(1);
        test.setPlayersHand(new Round(1));

        // Ensure no printed line in console
        test.setTrump();

        assertTrue(test.getTrumpSuit() instanceof Card);

        System.out.println(test.getTrumpSuit());
    }


    /**
     * Test StartNewGame()
     */
    @Test
    public void testStartNewGame() {
        GameLogic test = new GameLogic();
        ArrayOfPlayers arrayOfPlayers = test.startNewGame();

        //Check ArrayOfPlayers class
        assertTrue(arrayOfPlayers instanceof ArrayOfPlayers);

        int numPlayers = arrayOfPlayers.getNumberOfPlayers();
        assertEquals(4,numPlayers);


    }

    @Test
    public void testSetPlayerOrder() {
//        Check that there are no cards at first
        GameLogic test = new GameLogic();
        test.initialisePlayers();
        test.initialiseDeck();
        test.setDealer(1);

        test.initialiseDeck();
        ArrayOfPlayers arrayPlayersTest = test.getArrayOfPlayers();
        ArrayList<Card> player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        assertEquals(0, player1Cards.size());

//        Check the right number of cards in hand in 4th round
        test.setPlayersHand(new Round(1));

        test.setPlayerOrder(1);

        arrayPlayersTest = test.getArrayOfPlayers();
        for (Player p : arrayPlayersTest.getArrayOfPlayers()){
            System.out.println(p);
        }
        System.out.println(arrayPlayersTest.getNumberOfPlayers());
        player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        ArrayList<Card> player4Cards = arrayPlayersTest.getPlayerByIndex(3).getHand().getHand();
        assertEquals(1, player1Cards.size());
        assertEquals(1, player4Cards.size());
        assertEquals((52-4),test.getDeck().getNumberOfCardsRemaining() );

        System.out.println(Arrays.deepToString(arrayPlayersTest.getArrayOfPlayers().toArray()));
    }


}
