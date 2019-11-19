package estimate.player;
import cards.*;
import java.util.*;
import java.util.Comparator;

/**
 *
 * @author madhumitha
 * @version 1.0
 */

public class BidPosFirstCardComparator implements Comparator<Card> {
    private Suit trumpSuit;

    public BidPosFirstCardComparator(Suit trumpSuit) {
        this.trumpSuit = trumpSuit;
    }

    public BidPosFirstCardComparator() {

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
            return -rankDiff;
        }

    }

}