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

    public Card getHighestCard(){
        Hand sortHand = (Hand)hand ;
        sortHand.sort();
        int size = sortHand.getNumberOfCards();
        return sortHand.get(size-1);   
    }

    public Card getLowestCard(){
        Hand sortHand = (Hand)hand ;
        sortHand.sort();
        return sortHand.get(0);     
    }

    public ArrayList<Card> getHand(){
        return hand;
    }
}