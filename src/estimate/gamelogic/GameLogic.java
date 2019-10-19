import java.util.ArrayList;
import java.util.HashMap;

import cards.Deck;
import sun.tools.tree.VarDeclarationStatement;
/**
 * Main logic of game is here. GameLogic is called by the Front end to execute business logic
 * 
 * @author abelwong2017
 * @version 1.0
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

    /**
     * Adds 52 cards to deck
     */
    public void initialiseDeck() {
        Rank aceCard = Rank.ACE;
        Rand twoCard = Rank.TWO;
        Rank threeCard = Rank.THREE;
        Rand fourCard = Rank.FOUR;
        Rank fiveCard = Rank.FIVE;
        Rand sixCard = Rank.SIX;
        Rank sevenCard = Rank.SEVEN;
        Rand eightCard = Rank.EIGHT;
        Rank nineCard = Rank.NINE;
        Rand tenCard = Rank.TEN;
        Rank jackCard = Rank.JACK;
        Rand queenCard = Rank.QUEEN;
        Rank kingCard = Rank.KING;

        Suit clubs = Suit.CLUBS;
        Suit diamonds = Suit.DIAMONDS;
        Suit hearts = Suit.HEARTS;
        Suit spades = Suit.SPADES;

        Rank[] rankCards = {aceCard,twoCard,threeCard,fourCard,fiveCard,sixCard,sevenCard,eightCard,nineCard,tenCard,jackCard,queenCard,kingCard};

        Suit[] suits = {clubs,diamonds,hearts,spades};

        for (Suit suit: suits) {
            for (Rank rank: rankCards) {
                Card aCard = new Card(suit,rank,null);
                deckOfCards.addCard(aCard);
            }
        }
    }

    /**
     * Adds a card to the table
     * Used when a player plays a card on the table
     * @param player player object
     * @param playerCard card object
     */
    public void setTableHand(Player player, Card playerCard) {
        tableHand.addCard(player, playerCard);
    }
    
    /**
     * Checks the players to see who the dealer is
     * @param players
     * @return
     */
    public int getDealer(ArrayList<Player> players) {
        players.forEach(
            player-> 
                if (player.isDealer()) {
                    return player.id
                } 
        );
    }

    /**
     * Gets the trick winner from the tablehand when all 4 players have played their card
     * @param players ArrayList of players
     * @return returns playerId of winner
     */
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

    /**
     * Sets the trump at the start of the game when all players have been dealt a card
     * @param tableHand
     */
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