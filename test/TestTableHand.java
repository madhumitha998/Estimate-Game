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
}
