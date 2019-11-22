package estimate.player;

import cards.*;
import estimate.player.*;
import java.lang.reflect.Array;
import java.util.*;

/**
* The Player class defines a player in the game
* 
*
* @author  Madhumitha
* @version 1.0
*/
public class Player {
    private int id;
    private int position;
    private PlayerHand hand = new PlayerHand();
    private int bid;
    private boolean isTrickWinner;
    private boolean isDealer;
    
    /**
     * Initialise a player with its ID and position
     * @param id
     * @param position
     */
    public Player(int id, int position){
        this.id = id;
        this.position = position;
    }

    public ArrayList<Card> getPlayableHand(Suit leadSuit, Suit trumpSuit) {
        return this.hand.getPlayableHand(leadSuit, trumpSuit);
    }

    /**
     * Get the Player's ID
     * @return
     */
    public int getPlayerId() {
        return this.id;
    }

    /**
     * Get the Position of the player
     * @return
     */
    public int getPosition(){
        return position;
    }


    /**
     * Set the Position of the player
     * @param position
     */
    public void setPosition(int position){
        this.position=position;
    }


    /**
     * Returns the player's hand. PlayerHand is a separate class
     * @return
     */
    public PlayerHand getHand(){
        return hand;
    }

    /**
     * Adds a card to the player's Hand. PlayerHand is a class
     * @param c
     */
    public void setHand(Card c){
        hand.addCard(c);
    }


    /**
     * Remove Card from hand
     * @param
     * @return
     */
    public Card removeFromHand(int cardIndex){
        return hand.removeCard(cardIndex);
    }
    public Card removeFromHand(Card card){
        return hand.removeCard(card);
    }

    /**
     * Get Player's bid for the current round
     * @return
     */
    public int getBid(){
        return bid;
    }

    /**
     * Sets the current bid for the current round
     * @param bid
     */
    public void setBid(int bid){
        this.bid = bid;
    }

    /**
     * Tells you if player is the trick winner. 
     * @return
     */
    public boolean getTrickWinner(){
        return isTrickWinner;
    }

    /**
     * Sets isTrickWinner Status to true / false
     * @param value
     */
    public void setTrickWinner(boolean value){
        isTrickWinner = value;
    }

    /**
     * Returns the status of the dealer
     * @return
     */
    public boolean isDealer(){
        return isDealer;
    }

    /**
     * Sets dealer status to true / false
     * @param value
     */
    public void setIsDealer(boolean value){
        isDealer = value;
    }

    public String toString() {
        return String.format("Id: %s, Position: %s", this.getPlayerId() ,this.getPosition() );
    }

    public ArrayList<Integer> getAvailableBids(int tricksToWinForRound, int totalBidsSoFar) {
        ArrayList<Integer> returnList = new ArrayList<>();

        if (this.position != 3) {

            for (int i = 0; i <= tricksToWinForRound ; i ++) {
                returnList.add(i);
            }
        } else {

            if ((tricksToWinForRound - totalBidsSoFar) >= 0 ) {
                int forbiddenBid = (tricksToWinForRound - totalBidsSoFar);
                for (int i = 0; i <= tricksToWinForRound ; i ++) {
                    if (i != forbiddenBid) {
                        returnList.add(i);
                    }
                }
            } else if ( (tricksToWinForRound - totalBidsSoFar) < 0 ) {
                for (int i = 0; i <= tricksToWinForRound ; i ++) {
                    returnList.add(i);
                }
            }
        }
        return returnList;
    }

}