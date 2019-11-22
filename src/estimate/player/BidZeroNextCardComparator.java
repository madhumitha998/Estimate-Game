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
 * and when no card of TRUMP/lead suit available
 *
* @author madhumitha
* @version 1.0
*/ 
public class BidZeroNextCardComparator implements Comparator<Card> {
    private Suit trumpSuit;
    private Suit leadSuit;

    /**
     * Constructs an instance of BidZeroNextCardComparator
     *
     * @param  trumpSuit the trump Suit for the round
     * @param  leadSuit the lead Suit for the trick
     *
     */
    public BidZeroNextCardComparator(Suit trumpSuit, Suit leadSuit) {
        this.trumpSuit = trumpSuit;
        this.leadSuit = leadSuit;
    }

    /**
     * Compares two cards for the purposes of sorting.
     * Cards are ordered in ascending suit value excluding trump suit and lead suit.
     * They are then ordered in descending rank value.
     * This is to enable the selection of the highest rank card
     * of another suit in the ascending order of the suit
     *
     * @param c1 the first card to be compared
     * @param c2 the second card to be compared
     * @return a negative integer, zero, or a positive integer if c1 is
     * less than, equal to, or greater than c2.
     */
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