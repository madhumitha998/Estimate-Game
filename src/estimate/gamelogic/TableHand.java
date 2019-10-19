package estimate.gamelogic;
import estimate.player.*;

import java.util.*;
 /**
  * TableHand: Holds all the cards that players play on the table
  * Arraylist used as positioning of array indicates which player goes first
  * @author abelwong2017
  * @version 2.0
  */
 public class TableHand {
    private ArrayList<PlayerCardArray> tableHand;
    private ArrayList<PlayerCardArray> sortedTableHand;
     
    public TableHand() {
        tableHand = new ArrayList<PlayerCardArray>();
        sortedTableHand = new ArrayList<PlayerCardArray>();
    }

    /**
     * Adds a player's id and card to the table
     * @param player
     * @param card
     */
    public void addCard(Player player, Card card) {
        PlayerCardArray playerAndCard = new PlayerCardArray(player.getPlayerId(), card);
        tableHand.add(playerAndCard);
        sortedTableHand.add(playerAndCard);
        
    }

    /**
     * Sorts the cards on the table and returns an array list with the winning/highest card and player at index 0
     * @return returns an array list of player and card according to descending order
     */
    public ArrayList<PlayerCardArray> sortedTableHand() {
        Collections.sort(sortedTableHand, new PlayerCardComparator() );
        return sortedTableHand;
    }


 }
