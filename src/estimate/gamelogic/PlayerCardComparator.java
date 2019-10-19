package estimate.gamelogic;

import java.util.Comparator;
/**
 * StudentComparator used to compare an array made up of PlayerCardArray
 * @author abelwong2017
 * @version 1.0
 */ 
public class PlayerCardComparator implements Comperator<PlayerCardArray> {
    public int compare(PlayerCardArray o1, PlayerCardArray o2) {
        return (o1.compareTo(o2));
    }
    
}