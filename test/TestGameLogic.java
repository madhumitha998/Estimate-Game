import cards.Card;

import java.util.*;
import estimate.gamelogic.GameLogic;
import estimate.gamelogic.PlayerCardArray;
import estimate.gamelogic.TableHand;
import estimate.player.ArrayOfPlayers;
import estimate.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameLogic {

    @Test
    public void testNewDeck(){
        GameLogic test = new GameLogic();
        test.initialiseDeck();
        assertEquals(test.getDeck().getSizeOfDeck(), 52);
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
        test.setPlayersHand(1);

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
        test.setRound(3);
        test.initialisePlayers();
        test.initialiseDeck();
        test.setPlayersHand(3);

        // Ensure no printed line in console
        test.setTrumpCard();

        assertTrue(test.getTrumpCard() instanceof Card);

        System.out.println(test.getTrumpCard());

        test.initialiseDeck();
        System.out.println(test.getDeck().getNumberOfCardsRemaining());

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

    /**
     * Test the player's order.
     * 1. Test correct order in next subround
     * 2. Test correct order in new round
     */
    @Test
    public void testSetPlayerOrder() {
//        Check that there are no cards at first
        GameLogic test = new GameLogic();
        TableHand tableHand = new TableHand();

        test.initialisePlayers();
        test.initialiseDeck();


        test.setDealer(1);

        test.initialiseDeck();
        ArrayOfPlayers arrayPlayersTest = test.getArrayOfPlayers();
        ArrayList<Card> player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        assertEquals(0, player1Cards.size());

//        Check the right number of cards in hand in 4th round
        test.setPlayersHand(1);

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

        System.out.println("Position for first round when no cards are played" + Arrays.deepToString(arrayPlayersTest.getArrayOfPlayers().toArray()));

        System.out.println();
        test.setTrumpCard();
        for (Player p : arrayPlayersTest.getArrayOfPlayers()){

            Card c = p.removeFromHand(0);

            tableHand.addCard(p, c);
            if (p.getPlayerId() == 0 ) {
                test.setLeadSuitCard(c);
            }

        }
        PlayerCardArray winner = tableHand.sortedTableHand(test.getTrumpCard().getSuit(), test.getLeadSuitCard().getSuit()).get(0);

        System.out.println("Winner of first Trick: " + winner.getPlayerId());

        for (Player p : arrayPlayersTest.getArrayOfPlayers()){
            if (p.getPlayerId() == winner.getPlayerId()) {
                p.setTrickWinner(true);
            } else {
                p.setTrickWinner(false);
            }
        }
        test.setPlayerOrder(1);

        System.out.println("Position for first round, second trick. This ia after 1 card per person is played: " + Arrays.deepToString(arrayPlayersTest.getArrayOfPlayers().toArray()));

        test.initialiseDeck();
        test.setDealer(2);
        test.setPlayersHand(2);
        test.setTrumpCard();
        test.setPlayerOrder(2);
        System.out.println("Position for Second round, first trick. the player left of new dealer should start: " + Arrays.deepToString(arrayPlayersTest.getArrayOfPlayers().toArray()));
    }

    /**
     * Test to see if a subround runs properly
     */
    @Test
    public void testStartSubRound(){
        GameLogic test = new GameLogic();
        test.startNewGame();
        test.setPlayersHand(1); //Done testing
        test.setTrumpCard(); //Done testing
        test.setPlayerOrder(1);
        test.startSubRound(1 );

        //check Scoreboard
        test.getScoreboard().printScoreForRound(1);
    }

    /**
     * Test to see if a round works
     */
    @Test
    public void testRound(){
        GameLogic test = new GameLogic();
        test.startNewGame();
        test.startRound();
        test.getScoreboard().printScoreForAllRounds();

    }


}
