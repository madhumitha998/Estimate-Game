package estimate.player;

import cards.Card;
import cards.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
* The BidNegNextCardComparator class compares cards when the computer's bid is negative
* and when the computer is not the first player in the trick
*
* @author madhumitha
* @version 1.0
*/ 
public class BidZeroNextCardComparator implements Comparator<Card> {
    private Suit trumpSuit;
    private Suit leadSuit;

    public BidZeroNextCardComparator(Suit trumpSuit, Suit leadSuit) {
        this.trumpSuit = trumpSuit;
        this.leadSuit = leadSuit;
    }


    public int compare(Card c1, Card c2) {
        List<String> suitList = Arrays.asList("Clubs","Diamonds","Hearts","Spades");
        ArrayList<String> suitArrayList = new ArrayList<>();
        suitArrayList.addAll(suitList);

        String trumpSuitName = trumpSuit.getName();
        String leadSuitName = leadSuit.getName();
        suitArrayList.remove(trumpSuitName);
        suitArrayList.remove(leadSuitName);

        int suitDiff = suitArrayList.indexOf(c1.getSuit().getName())-suitArrayList.indexOf(c2.getSuit().getName());

        int rankDiff = c1.getRank().compareTo( c2.getRank() );

        if ( suitDiff != 0 ){
            return suitDiff;
        }else{
            return -rankDiff;
        }

    }

}