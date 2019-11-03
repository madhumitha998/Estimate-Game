package estimate.player;

import cards.*; 
import java.util.*;
public class PlayerHand extends Hand {
    
    private ArrayList<Card> hand = new ArrayList<>();

    public PlayerHand(){
    }

    public int evaluateHand(String condition){
        /* dummy return value for now */
        return 1;
    };

    public ArrayList<Card> getHand(){
        return hand;
    }
}