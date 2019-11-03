package estimate.player;

import cards.*; 
import java.util.*;
public class PlayerHand extends Hand {
    
    private ArrayList<Card> hand = new ArrayList<>();

    public PlayerHand(){
        super();
    }

    public int evaluateHand(){
        /* dummy return value for now */
        return 1;
    };

<<<<<<< HEAD
   public Card getHighestCard(){
       ArrayList<Card> sortHand = Collections.sort( hand );
       int size = sortHand.getNumberOfCards();
       return sortHand.get(size-1);
   }

    public Card getLowestCard(){
        ArrayList<Card> sortHand = Collections.sort( hand );
        return sortHand.get(0);
    }

=======
>>>>>>> bd951e4bca527259940e34dc241a82a015a68b0c
    public ArrayList<Card> getHand(){
        return hand;
    }
}