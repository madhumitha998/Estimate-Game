/**
 * The Computer class can place bids and play cards
 *
 *
 * @author  Madhumitha
 * @version 1.2
 * @since   2019-10-19
 */
package estimate.player;

import cards.*; 
import java.util.*;
public class PlayerHand extends Hand {

    private ArrayList<Card> hand = (ArrayList<Card>)(super.hand);

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

    // What's this supposed to do?
    public int evaluateHand(){
        /* dummy return value for now */
        return 1;
    };

    public ArrayList<Card> getHand(){
        return hand;
    }

    public String toString() {
        String returnString = String.format(" " );
        for (Card c : hand) {
            returnString += "" + c.getRank().toString() + " " + c.getSuit().toString() + "\n";
        }

        return returnString;
    }
}