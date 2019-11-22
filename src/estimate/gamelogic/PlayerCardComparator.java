package estimate.gamelogic;

import cards.Card;
import cards.Suit;

import java.util.Comparator;
/**
 * StudentComparator used to compare an arrayList made up of PlayerCardArray
 * @author abelwong2017
 * @version 1.0
 */ 
public class PlayerCardComparator implements Comparator<PlayerCardArray> {
    private Suit trumpSuit;
    private Suit leadSuit;

    public PlayerCardComparator() {

    }

    public PlayerCardComparator(Suit trumpSuit, Suit leadSuit) {
        this.trumpSuit = trumpSuit;
        this.leadSuit = leadSuit;
    }

    /**
     * Compares two PlayerCardArray objects
     * Returns negative, 0 and positive number respectively when PlayerCardArray is larger, equals to or smaller in
     * rank first, followed by suit.
     * Takes into account trumpSuit and leadSuit if included
     * trump suit > lead suit > normal suit in descending order
     * @param o1
     * @param o2
     * @return
     * @see PlayerCardArray
     */
    public int compare(PlayerCardArray o1, PlayerCardArray o2) {
        // new order:
        // if o1 is a trump card and o2 is not a trump card then return -1
        // opposite of this return 1
        // else, return normal compare
        //
        Card o1Card = o1.getPlayerCard();
        Card o2Card = o2.getPlayerCard();

        if (trumpSuit != null) {

            if ( (o1Card.getSuit() == this.trumpSuit) && (o2Card.getSuit() != this.trumpSuit) ) {
                return -1;
            } else if ( (o1Card.getSuit() != this.trumpSuit) && (o2Card.getSuit() == this.trumpSuit) ) {
                return 1;
            }
             else if ( (o1Card.getSuit() == this.leadSuit) && (o2Card.getSuit() != this.leadSuit) ) {
                return -1;
            } else if ( (o1Card.getSuit() != this.leadSuit) && (o2Card.getSuit() == this.leadSuit) ) {
                return 1;
            } else { // if both lead suit, compare normally
                return -(o1.compareTo(o2));
            }

        } else if (leadSuit != null) {
            if ( (o1Card.getSuit() == this.leadSuit) && (o2Card.getSuit() != this.leadSuit) ) {
                return -1;
            } else if ( (o1Card.getSuit() != this.leadSuit) && (o2Card.getSuit() == this.leadSuit) ) {
                return 1;
            } else { // if both lead NOT suit, compare normally
                return -(o1.compareTo(o2));
            }

        } else {    // If no lead suit and no trump suit
//            System.out.println("Printed");
            return -(o1.compareTo(o2));
        }


    }

}