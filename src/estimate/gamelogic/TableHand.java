package estimate.gamelogic;
import estimate.player.*;
import cards.*;

import java.util.*;
 /**
  * TableHand: Holds all the cards that players play on the table
  * Arraylist used as positioning of array indicates which player goes first
  * @author abelwong2017
  * @version 2.0
  */
 public class TableHand {
    private List<PlayerCardArray> tableHand;
    private List<PlayerCardArray> sortedTableHand;

     /**
      * Constructor initialises two ArrayList
       */
    public TableHand() {
        tableHand = new ArrayList<PlayerCardArray>();
        sortedTableHand = new ArrayList<PlayerCardArray>();
    }


    public List<PlayerCardArray> getTableHand(){
        return this.tableHand;
    }

     /**
      * Clears the table of all cards
      */
    public void clearTableHand() {
        tableHand.clear();
        sortedTableHand.clear();
    }

    /**
     * Adds a player and card to the table
     * @param player
     * @param card
     */
    public void addCard(Player player, Card card) {
        PlayerCardArray playerAndCard = new PlayerCardArray(player.getPlayerId(), card);
        tableHand.add(playerAndCard);
        sortedTableHand.add(playerAndCard);
        
    }

     /**
      * Sorts the cards on the table with the winning/highest card and player at index 0
      * @return ArrayList of PlayerCardArray object
      */
     public List<PlayerCardArray> sortedTableHand() {
         Collections.sort(sortedTableHand, new PlayerCardComparator() );
         return sortedTableHand;
     }

    /**
     * Overloads sortedTableHand()
     * Include trumpSuit and LeadSuit
     * Sorts the cards on the table and returns an array list with the winning/highest card and player at index 0
     * @return returns an array list of player and card according to descending order
     */
    public List<PlayerCardArray> sortedTableHand(Suit trumpSuit, Suit leadSuit) {
        Collections.sort(sortedTableHand, new PlayerCardComparator(trumpSuit, leadSuit) );
        return sortedTableHand;
    }

    public String toString() {
        return "Table Hand Cards \n " + Arrays.deepToString(tableHand.toArray(new PlayerCardArray[tableHand.size()]));
    }


 }
