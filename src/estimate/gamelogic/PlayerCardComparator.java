package estimate.gamelogic;

import cards.Card;
import cards.Suit;

import java.util.Comparator;
/**
 * StudentComparator used to compare an array made up of PlayerCardArray
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
            // if one lead suit, and another not lead suit, return 1 x2
            if ( (o1Card.getSuit() == this.leadSuit) && (o2Card.getSuit() != this.leadSuit) ) {
                return -1;
            } else if ( (o1Card.getSuit() != this.leadSuit) && (o2Card.getSuit() == this.leadSuit) ) {
                return 1;
            } else { // if both lead suit, compare normally
                return -(o1.compareTo(o2));
            }

        } else {
            return -(o1.compareTo(o2));
        }


    }

}