import java.util.Collections;

/**
 * Table hand should be an array instead to track position.
 */

 /**
  * TableHand
  */
 public class TableHand {
    private ArrayList<PlayerCardArray> tableHand;
    private ArrayList<PlayerCardArray> sortedTableHand;
     
    public TableHand() {
        tableHand = new ArrayList<PlayerCardArray>;
        sortedTableHand = new ArrayList<PlayerCardArray>;
    }

    public void addCard(Player player, Card card) {
        PlayerCardArray playerAndCard = new PlayerCardArray(player, card);
        tableHand.add(playerAndCard);
        sortedTableHand.add(playerAndCard);
        
    }

    public ArrayList<PlayerCardArray> sortedTableHand() {
        Collections.sort(sortedTableHand, new PlayerCardComparator());
        return sortedTableHand;
    }


 }
