import java.util.ArrayList;
import java.util.HashMap;

import sun.tools.tree.VarDeclarationStatement;
/**
 * GameLogic
 */
public class GameLogic {

    private ArrayList<Cards> deckOfCards;
    private Card leadSuit;
    private TableHand tableHand;
    private Card trumpSuit;
    private int roundCounter;

    public GameLogic() {
        private tableCards = new Hand();
    }

    public void setTableHand(Player player, Card playerCard) {
        tableHand.addCard(player, playerCard);
    }

    public int getDealer(ArrayList<Player> players) {
        players.forEach(
            player-> 
                if (player.isDealer()) {
                    return player.id
                } 
        );
    }


    // set player for isTrickWinner method
    public int getTrickWinner() {
        Hashmap winnerAtIndexZero = tableHand.sort()
        int winner = winnerAtIndexZero.get(winnerAtIndexZero.keySet().toArray()[0])

        return winner;
    }

    public void setPlayersHand(ArrayList<Player> players){
        Deck d = new Deck();
        d.shuffle();
        for (Player p: players){
            // deal from deck --> add to hand
            // to check
            p.hand.addCard(d.dealCard())
        }
    }


}