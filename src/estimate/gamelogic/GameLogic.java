package estimate.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import cards.*;
import estimate.player.*;

/**
 * Main logic of game is here. GameLogic is called by the Front end to execute business logic
 * 
 * @author abelwong2017
 * @version 1.0
 */
public class GameLogic {

    private ArrayOfPlayers arrayOfPlayers;
    private Deck deckOfCards;
    private Card leadSuit;
    private TableHand tableHand;
    private Card trumpSuit;
    private Round round;
    // private ScoreBoard scoreboard;

    public GameLogic() {
        deckOfCards = new Deck();
        tableHand = new TableHand();
        arrayOfPlayers = new ArrayOfPlayers();
    }

    public void initialisePlayers() {
        Player player1 = new Player(0,0);
        Player player2 = new Player(1,1);
        Player player3 = new Player(2,2);
        Player player4 = new Player(3,3);
        arrayOfPlayers.addPlayer(player1);
        arrayOfPlayers.addPlayer(player2);
        arrayOfPlayers.addPlayer(player3);
        arrayOfPlayers.addPlayer(player4);
    }

    /**
     * 
     */
    public ArrayOfPlayers startNewGame() {
        initialiseDeck();
//        setDealerAtStartOfRound();
        setPlayersHand(round);
        setTrump();
        return getArrayOfPlayers();
    }

    public ArrayOfPlayers getArrayOfPlayers() {
        return this.arrayOfPlayers;
    }
    /**
     * Sets the order of players at the start of every round 
     */
    public void setPlayerOrder(){
        // First player is to the left of the dealer aka: playerIDs in ascending order

        // Fringe case: the left of 4 will be 1
    }

    public Deck getDeck() {
        return deckOfCards;
    }

    /**
     * Adds 52 cards to deck
     */
    public void initialiseDeck() {
        Rank aceCard = Rank.ACE;
        Rank twoCard = Rank.TWO;
        Rank threeCard = Rank.THREE;
        Rank fourCard = Rank.FOUR;
        Rank fiveCard = Rank.FIVE;
        Rank sixCard = Rank.SIX;
        Rank sevenCard = Rank.SEVEN;
        Rank eightCard = Rank.EIGHT;
        Rank nineCard = Rank.NINE;  
        Rank tenCard = Rank.TEN;
        Rank jackCard = Rank.JACK;
        Rank queenCard = Rank.QUEEN;
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
        deckOfCards.shuffle();
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
     * Sets the dealer at the start of the round. 
     */
//    public void setDealerAtStartOfRound() {
//        // If round 0, dealer is the highest card
//        ArrayList<Player> playersArray = this.arrayOfPlayers.getArrayOfPlayers();
//        for (Player p: playersArray){
//            p.setHand(this.deckOfCards.dealCard());
//        }
//        for (Player p: playersArray) {
//
//        }
//        int theDealerIndex = tableHand.sortedTableHand().get(0).getPlayerId();
//
//        playersArray.get(theDealerIndex).setIsDealer(true);
//
//        this.arrayOfPlayers.updatePlayerStates(playersArray);
//
//        // If round > 0, dealer is the person to the left of the dealer
//
//    }
    
    /**
     * Checks the players to see who the dealer is
     * @param players
     * @return
     */
    public int getDealer(ArrayList<Player> players) {

        for(Player p: players) {
            if (p.isDealer()) {
                    return p.getPlayerId();
                } 
        }
        return -1;
    }

    /**
     * Gets the trick winner from the tablehand when all 4 players have played their card
     * @param players ArrayList of players
     * @return returns playerId of winner
     */
    public int getTrickWinner(ArrayList<Player> players) {
        PlayerCardArray winnerAtIndexZero = (tableHand.sortedTableHand()).get(0);
        int winner = winnerAtIndexZero.getPlayerId();
        for (Player p: players){
            if (p.getPlayerId() == winner) {
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
    public void setTrump() {
        if (deckOfCards.getNumberOfCardsRemaining() == 48) {
            trumpSuit = deckOfCards.dealCard();
        } else {
            System.out.println("Not enough players");
        }
   
    }

    /**
     * Add card to player's hand
     * TODO: Takes into account the round (different round deals different cards)
     * @param players
     */
    public void setPlayersHand(Round round){
        deckOfCards.shuffle();
        
        ArrayList<Player> playersArray = this.arrayOfPlayers.getArrayOfPlayers();
        ArrayList<Player> playerReceivingCardOrder = new ArrayList<>();
        int dealerIndex = 0;
        for (int playerCounter = 0; playerCounter < playersArray.size() ; playerCounter ++ ) {
            Player selectedPlayer = playersArray.get(playerCounter);
            if (selectedPlayer.isDealer()) {
                dealerIndex = playerCounter;
                break;
            }
        }
        for (int toTheRightOfDealer = dealerIndex + 1; toTheRightOfDealer < playersArray.size(); toTheRightOfDealer++ ) {
            playerReceivingCardOrder.add(playersArray.get(toTheRightOfDealer));
        }

        for (int toTheLeftOfDealer = 0; toTheLeftOfDealer < dealerIndex; toTheLeftOfDealer++ ) {
            playerReceivingCardOrder.add(playersArray.get(toTheLeftOfDealer));
        }

        int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};
        
        for (int cardsToDeal = 0; cardsToDeal < cardsToDealPerRound[round.getRound() - 1]; cardsToDeal++) {
            for (Player p: playerReceivingCardOrder){
                p.setHand(deckOfCards.dealCard());
                
            }
            System.out.println("added once");
        }
        Collections.sort(playerReceivingCardOrder, (a, b ) -> a.getPlayerId() - b.getPlayerId());
        System.out.println(playerReceivingCardOrder.get(0).getPlayerId());

        this.arrayOfPlayers.updatePlayerStates(playerReceivingCardOrder);

    }


}