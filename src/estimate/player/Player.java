/**
* The Player class defines a player in the game
* 
*
* @author  Madhumitha
* @version 1.2
* @since   2019-10-19 
*/
package estimate.player;

import cards.*;
import estimate.player.*;
import java.util.*;

public class Player {
    private int id;
    private int position;
    private PlayerHand hand = new PlayerHand();
    private int bid;
    private boolean isTrickWinner;
    private boolean isDealer;
    
    public Player(int id, int position){
        this.id = id;
        this.position = position;
    }

    //Position 
    public void setPosition(int position){
        this.position=position;
    }

    public int getPlayerId() {
        return this.id;
    }

    public int getPosition(){
        return position;
    }

    //Hand
    //use hand.addCard() from Hand class to set the hand for a trick
    public void setHand(Card c){
        hand.addCard(c);
    }

    public PlayerHand getHand(){
        return hand.getHand();
    }

    //Bid
    public void setBid(int bid){
        this.bid = bid;
    }

    public int getBid(){
        return bid;
    }

    //TrickWinner
    public void setTrickWinner(boolean value){
        isTrickWinner = value;
    }

    public boolean getTrickWinner(){
        return isTrickWinner;
    }

    //IsDealer
    public void setIsDealer(boolean value){
        isDealer = value;
    }

    public boolean IsDealer(){
        return isDealer;
    }


}