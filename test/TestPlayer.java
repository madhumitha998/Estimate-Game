import cards.Card;
import cards.Rank;
import cards.Suit;
import estimate.player.Player;
import estimate.player.PlayerHand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPlayer {
    @Test
    public void playerTest() {
        Player player1 = new Player(0,3);
        Card testCard = new Card(Suit.CLUBS, Rank.ACE, null );

        assertEquals(0, player1.getPlayerId());

        assertEquals(3, player1.getPosition());

        player1.setPosition(1);
        assertEquals(1, player1.getPosition());

        // Test Hand
        assertTrue(player1.getHand() != null);

        // SetHand
        assertEquals(0, player1.getHand().getNumberOfCards());
        player1.setHand(testCard);
        assertEquals(1, player1.getHand().getNumberOfCards());

        // Remove From hand using index
        player1.removeFromHand(0);
        assertEquals(0, player1.getHand().getNumberOfCards());

        // Remove from hand using Card
        player1.setHand(testCard);
        player1.removeFromHand(testCard);
        assertEquals(0, player1.getHand().getNumberOfCards());

        //getBid
        player1.setBid(3);
        assertEquals(3, player1.getBid());

        //getTrickWinner
        player1.setTrickWinner(true);
        assertTrue(player1.getTrickWinner());
        player1.setTrickWinner(false);
        assertEquals(false, player1.getTrickWinner());

        // getIsDealer
        player1.setIsDealer(true);
        assertTrue(player1.isDealer());
        player1.setIsDealer(false);
        assertEquals(false, player1.isDealer());
    }
}