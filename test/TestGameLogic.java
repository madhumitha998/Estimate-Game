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
    @Test
    public void initializeDeck() {
        GameLogic test = new GameLogic();
        test.initialiseDeck();
        assertEquals(test.getDeck().getSizeOfDeck(), 52);
    }

    @Test
    public void setPlayerHand() {
//        Check that there are no cards at first
        GameLogic test = new GameLogic();
        test.initialisePlayers();
        ArrayOfPlayers arrayPlayersTest = test.getArrayOfPlayers();
        ArrayList<Card> player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        assertEquals(0, player1Cards.size());

//        Check the right number of cards in hand in 4th round
        test.setPlayersHand(new Round(4));
        arrayPlayersTest = test.getArrayOfPlayers();
        player1Cards = arrayPlayersTest.getPlayerByIndex(0).getHand().getHand();
        assertEquals(4, player1Cards.size());

    }

}
