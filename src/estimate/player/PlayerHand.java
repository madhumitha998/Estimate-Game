package estimate.player;

import cards.*; 
import java.util.*;
public class PlayerHand extends Hand {
    
    private ArrayList<Card> hand = new ArrayList<>();

    public PlayerHand(){
    }

    public int evaluateHand(){
        /* dummy return value for now */
        return 1;
    };

    public Card getHighestCard(){
        hand.sort();
        int size = hand.getNumberOfCards();
        return hand.get(size-1);   
    }

    public Card getLowestCard(){
        hand.sort();
        return hand.get(0);     
    }

    public ArrayList<Card> getHand(){
        return hand;
    }
}