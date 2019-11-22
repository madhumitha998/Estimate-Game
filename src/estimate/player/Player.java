package estimate.player;

import cards.*;
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

    /**
     *
     *
     * @param leadSuit
     * @param trumpSuit
     * @return
     */
    public ArrayList<Card> getPlayableHand(Suit leadSuit, Suit trumpSuit) {
        return this.hand.getPlayableHand(leadSuit, trumpSuit);
    }

    /**
     * Gets the Player's ID
     * @return player's ID
     */
    public int getPlayerId() {
        return this.id;
    }

    /**
     * Gets the Position of the player from index 0-3
     * @return position index of player
     */
    public int getPosition(){
        return position;
    }


    /**
     * Sets the Position of the player from index 0-3
     * @param position
     */
    public void setPosition(int position){
        this.position=position;
    }


    /**
     * Returns the player's hand. PlayerHand is a separate class
     * @return player's hand
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
     * Removes Card from player's hand
     * @param cardIndex
     * @return Card removed
     */
    public Card removeFromHand(int cardIndex){
        return hand.removeCard(cardIndex);
    }

    /**
     * Removes Card from player's hand
     * @param card
     * @return Card removed
     */
    public Card removeFromHand(Card card){
        return hand.removeCard(card);
    }

    /**
     * Get Player's bid for the current round
     * @return player's bid for the round
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
     * Returns whether the player is dealer or not
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

    /**
     * Returns string value of player's position and Id
     * @return
     */
    public String toString() {
        return String.format("Id: %s, Position: %s", this.getPlayerId() ,this.getPosition() );
    }

    /**
     * Returns list of available bids
     * @param tricksToWinForRound
     * @param totalBidsSoFar
     * @return
     */
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