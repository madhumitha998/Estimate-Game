import estimate.gamelogic.GameLogic;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestGameLogic {

    @Test
    public void testNewDeck(){
        GameLogic test = new GameLogic();
        assertEquals(test.getDeck().getSizeOfDeck(), 0);
    }
    @

}
