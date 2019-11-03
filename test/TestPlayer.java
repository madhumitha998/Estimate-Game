import cards.*;
import java.util.*;
import estimate.*;
import estimate.player.Player;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    public void testSetPosition(){
        Player test = new Player(1,1);
        test.setPosition(2);
        assertEquals(test.getPosition(), 2);
    }

    



}