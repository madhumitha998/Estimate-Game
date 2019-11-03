package estimate.player;

import cards.*; 
import java.util.*;
public class PlayerHand extends Hand {

    private ArrayList<Card> hand = (ArrayList<Card>)(super.hand);

    public PlayerHand() {
        super();
    }

    public int evaluateHand(){
        /* dummy return value for now */
        return 1;
    };

    public ArrayList<Card> getHand(){
        return hand;
    }
}