import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.gamelogic.TableHand;
import estimate.player.Player;
import estimate.player.PlayerHand;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TestPlayer {
    @Test
    public void playerTest() {
        Player player1 = new Player(0,3);
        Card testCard = new Card(Suit.CLUBS, Rank.ACE, null );

        assertEquals(0, player1.getPlayerId());

        assertEquals(3, player1.getPosition());

        player1.setPosition(1);
        assertEquals(1, player1.getPosition());

        // TestHand
        assertTrue(player1.getHand()instanceof PlayerHand);

        // SetHand
        player1.setHand(testCard);
        assertEquals(1, player1.getHand().getHand().size());

        // Remove Form hand
        player1.removeFromHand(0);
        assertEquals(0, player1.getHand().getHand().size());
    }

}

