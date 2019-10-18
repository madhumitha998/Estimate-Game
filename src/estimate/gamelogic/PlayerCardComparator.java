import java.util.Comparator;
/**
 * StudentComparator
 */
public class PlayerCardComparator implements Comperator <PlayerCardArray> {
    public int compare(PlayerCardArray o1, PlayerCardArray o2) {
        return (o1.compareTo(o2));
    }
    
}