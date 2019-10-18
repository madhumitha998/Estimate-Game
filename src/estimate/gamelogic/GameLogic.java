import java.util.ArrayList;
import java.util.HashMap;

import cards.Deck;
import sun.tools.tree.VarDeclarationStatement;
/**
 * GameLogic
 */
public class GameLogic {

    private Deck deckOfCards;
    private Card leadSuit;
    private TableHand tableHand;
    private Card trumpSuit;
    private int roundCounter;

    public GameLogic() {
        deckOfCards = new Deck();
        tableHand = new Hand();
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
    public int getTrickWinner(ArrayList<Player> players) {
        ArrayList<PlayerCardArray> winnerAtIndexZero = (tableHand.sortedTableHand()).get(0);
        int winner = winnerAtIndexZero.getPlayerId();
        for (Player p: players){
            if (p.getPlayerId == winner) {
                p.setTrickWinner(true);
                break;
            }
        }

        return winner;
    }

    public void setTrump( TableHand tableHand) {
        if (deckOfCards.getNumberOfCardsRemaining() == 48) {
            trumpSuit = deckOfCards.dealCard();
        } else {
            System.out.println("Not enough players");
        }
   
    }

    public void setPlayersHand(ArrayList<Player> players){
        Deck d = new Deck();
        d.shuffle();
        // deal from deck --> add to hand
        for (Player p: players){
            p.addCard(d.dealCard())
        }
    }


}