import java.util.ArrayList;
import java.lang.Comparable;
/**
 * PlayerCardArray
 */
public class PlayerCardArray implements Comparable<PlayerCardArray> {

    int playerId;
    Card playerCard;
    
    PlayerCardArray(int playerId, Card playerCard) {
        this.playerId = playerId;
        this.playerCard = playerCard;
    }

    public int getPlayerId(){
        return this.playerId;
    }

    public Card playerCard(){
        return this.playerCard;
    }

    @Override
    public int compareTo(PlayerCardArray o1) {
        return (this.playerCard.compareTo(o1.playerCard));
    }
}