package estimate.player;

import cards.*; 
import java.util.*;

/**
* The PlayerHand class defines a hand of cards 
* 
*
* @author  Madhumitha
* @version 1.0
*/
public class PlayerHand extends Hand {

    private ArrayList<Card> hand = (ArrayList<Card>)(super.hand);

    /**
     * Creates an instance of PlayerHand
     */
    public PlayerHand() {
        super();
    }

    public ArrayList<Card> getPlayableHand(Suit leadSuit, Suit trumpSuit) {
        ArrayList<Card> playableCards = new ArrayList<>();

        //Player is only allowed to play other suits if no lead / trump suit cards

        // Counter for lead suit
        int leadSuitCounter = 0;
        // Counter for trump Suit
        int trumpSuitCounter = 0;
        // Loop through hand
        for (Card c : hand) {
            // If card suit is lead suit / trump suit, add to playableCards and add to counter

            if (c.getSuit() == leadSuit) {
                leadSuitCounter++;
                playableCards.add(c);
            }
            else if (c.getSuit() == trumpSuit) {
                trumpSuitCounter++;
                playableCards.add(c);
            }
        }
        // If counter for leadSuit and trumpSuit both == 0
            // Add all cards in hand into playableCards
        if (leadSuitCounter == 0 && trumpSuitCounter == 0) {
            for (Card c : hand) {
                playableCards.add(c);
            }
        }

        return playableCards;
    }

    /**
     *  Evaluates the hand
     *  @return an integer corresponding to the rating of the hand.
     */
    public int evaluateHand(){
        return 1;
    };

    /**
     * Returns the player hand as an array list of Cards
     * @return a list of cards held in the hand
     */
    public ArrayList<Card> getHand(){
        return hand;
    }

    /**
     * Returns a description of the player hand.
     * @return a description of player hand
     */
    public String toString() {
        String returnString = String.format(" " );
        for (Card c : hand) {
            returnString += "" + c.getRank().toString() + " " + c.getSuit().toString() + "\n";
        }

        return returnString;
    }
}