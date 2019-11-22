package estimate.player;

import cards.*;
import java.util.*;
import java.lang.Math;
import estimate.player.SelectCard;
import java.util.Comparator;

/**
* The BidNegFirstCardComparator class compares cards when the computer's bid is negative
* and when the computer is the first player in the trick
*
* @author  Madhumitha
* @version 1.0
*/
public class BidZeroFirstCardComparator implements Comparator<Card> {
    private Suit trumpSuit;

    public BidZeroFirstCardComparator(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public int compare(Card c1, Card c2) {
        List<String> suitList = Arrays.asList("Clubs","Diamonds","Hearts","Spades");
        ArrayList<String> suitArrayList = new ArrayList<>();
        suitArrayList.addAll(suitList);

        String trumpSuitName = trumpSuit.getName();
        suitArrayList.remove(trumpSuitName);
        suitArrayList.add(trumpSuitName);

        int suitDiff = suitArrayList.indexOf(c1.getSuit().getName())-suitArrayList.indexOf(c2.getSuit().getName());

        int rankDiff = c1.getRank().compareTo( c2.getRank() );

        if ( suitDiff != 0 ){
            return suitDiff;
        }else{
            return rankDiff;
        }

    }

}