package estimate.player;
import cards.*;
import java.util.*;
import java.util.Comparator;

/**
* The BidPosFirstCardComparator class compares cards when the computer's bid is positive
* and when the computer is the first player in the trick
*
* @author  Madhumitha
* @version 1.0
*/
public class BidPosFirstCardComparator implements Comparator<Card> {
    private Suit trumpSuit;

    /**
     * Constructs an instance of BidPosFirstCardComparator
     *
     * @param  trumpSuit the trump Suit for the round
     */
    public BidPosFirstCardComparator(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
    }


    /**
     * Compares two cards for the purposes of sorting.
     * Cards are ordered in ascending suit value with Trump suit being highest.
     * They are then ordered in descending rank value.
     * This is to enable the selection of the highest rank card
     * in the ascending order of the suit
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
        suitArrayList.remove(trumpSuitName);
        suitArrayList.add(trumpSuitName);

        int suitDiff = suitArrayList.indexOf(c1.getSuit().getName())-suitArrayList.indexOf(c2.getSuit().getName());

        int rankDiff = c1.getRank().compareTo( c2.getRank() );

        if ( suitDiff != 0 ){
            return suitDiff;
        }else{
            return -rankDiff;
        }

    }

}