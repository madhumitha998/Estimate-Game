import java.util.HashMap;
/**
 * TableHand
 */
public class TableHand extends Hand {
    private HashMap tableHand;

    public void addCard(Player player, Card card){
        tableHand.put(player.id, card);
    }

    public void sort() {
        MapUtil.<int, Card>sortByValue(tableHand);
    }
}