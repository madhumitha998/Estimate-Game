import cards.Card;
import cards.*;
import estimate.gamelogic.GameLogic;
import estimate.gamelogic.TableHand;
import estimate.player.ArrayOfPlayers;
import estimate.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTableHand {

    /**
     * Test printing of the tableHand
     */
    @Test
    public void testTableHandPrinting(){
        GameLogic test = new GameLogic();
        test.initialiseDeck();
        test.initialisePlayers();
        ArrayOfPlayers arrayPlayersTest = test.getArrayOfPlayers();

        for ( Player p : arrayPlayersTest.getArrayOfPlayers() ){
            System.out.println(p.getHand());

            test.setTableHand(p, test.getDeck().dealCard());
        }
        System.out.println(test.getTableHand());

    }

    /**
     * Ensure that cards on the table are sorted in descending order (Largest first)
     */
    @Test
    public void testTableHandSort() {
        Card testTrump = new Card(Suit.CLUBS, Rank.TWO, null );
        Card testCard1 = new Card(Suit.DIAMONDS, Rank.ACE, null );
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.NINE, null );
        Card testCard3 = new Card(Suit.HEARTS, Rank.SEVEN, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard5 = new Card(Suit.SPADES, Rank.TEN, null );

        GameLogic test = new GameLogic();
        test.initialisePlayers();
        TableHand tableHand = test.getTableHand();
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard1);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard2);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard3);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard4);

        System.out.println(tableHand.sortedTableHand(null, testCard1.getSuit())) ;
        System.out.println("Expected: AD, ND, SH, NC");

    }

    @Test
    public void testTableHandForNolead() {
        Card testTrump = new Card(Suit.CLUBS, Rank.TWO, null );
        Card testCard1 = new Card(Suit.DIAMONDS, Rank.ACE, null );
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.NINE, null );
        Card testCard3 = new Card(Suit.HEARTS, Rank.SEVEN, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard5 = new Card(Suit.SPADES, Rank.TEN, null );

        GameLogic test = new GameLogic();
        test.initialisePlayers();
        TableHand tableHand = test.getTableHand();
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard1);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard2);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard3);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard4);

        System.out.println(tableHand.sortedTableHand(null,null)) ;

//        TableHand results = new TableHand();
//        results.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard3);
//        results.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard1);
//        results.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard2);
//        results.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard4);
//
        System.out.println("Expected: SD, AD, ND, NC");
//        assertEquals(tableHand.sortedTableHand(null,null), results.getTableHand());

    }

    /**
     * Test the table hand sorting order with trump
     */
    @Test
    public void testTableHandSortWithTrump() {
        Card testTrump = new Card(Suit.CLUBS, Rank.TWO, null );
        Card testCard1 = new Card(Suit.DIAMONDS, Rank.ACE, null );
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.NINE, null );
        Card testCard3 = new Card(Suit.HEARTS, Rank.SEVEN, null );
        Card testCard4 = new Card(Suit.CLUBS, Rank.NINE, null );
        Card testCard5 = new Card(Suit.SPADES, Rank.TEN, null );

        GameLogic test = new GameLogic();
        test.initialisePlayers();
        TableHand tableHand = test.getTableHand();
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard1);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard2);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard3);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard4);

        System.out.println(tableHand.sortedTableHand(Suit.CLUBS, testCard1.getSuit())) ;
        System.out.println("Expected: NC, AD, ND, SH");

    }
}
