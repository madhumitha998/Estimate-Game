import cards.Card;
import cards.*;
import java.util.*;
import estimate.gamelogic.GameLogic;
import estimate.gamelogic.PlayerCardArray;
import estimate.gamelogic.Round;
import estimate.gamelogic.TableHand;
import estimate.player.ArrayOfPlayers;
import estimate.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTableHand {

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

    @Test
    public void testTableHandSort() {
        Card testTrump = new Card(Suit.HEARTS, Rank.TWO, null );
        Card testCard1 = new Card(Suit.SPADES, Rank.FIVE, null );
        Card testCard2 = new Card(Suit.DIAMONDS, Rank.KING, null );
        Card testCard3 = new Card(Suit.CLUBS, Rank.JACK, null );
        Card testCard4 = new Card(Suit.HEARTS, Rank.TWO, null );

        GameLogic test = new GameLogic();
        test.initialisePlayers();
        TableHand tableHand = test.getTableHand();
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard1);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard2);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard3);
        tableHand.addCard(test.getArrayOfPlayers().getArrayOfPlayers().get(0),testCard4);

        System.out.println(tableHand.sortedTableHand(testTrump.getSuit())) ;
        System.out.println("Expected: 2H, 5S, JC, KD");

    }
}
