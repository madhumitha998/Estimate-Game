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


//        Player player0 = new Player(10,10);
        Player player1 = new Player(0,0);
        Player player2 = new Player(1,1);
        Player player3 = new Player(2,2);
        Player player4 = new Player(3,3);

//        arrayOfPlayers.addPlayer(player0);
        arrayOfPlayers.addPlayer(player1);
        arrayOfPlayers.addPlayer(player2);
        arrayOfPlayers.addPlayer(player3);
        arrayOfPlayers.addPlayer(player4);
    }

    /**
     * Sets the round number
     * @param roundNumber
     */
    public void setRound(int roundNumber) {
        this.round = new Round(roundNumber);
    }

    /**
     * STarts a new game
     */
    public ArrayOfPlayers startNewGame() {
        // Remember to start Round from 1 not 0
        setRound(1);
        initialisePlayers();
        initialiseDeck(); // Done testing
        setDealerAtStartOfGame(); // Done testing
        setPlayersHand(round); //Done testing
        setTrump(); //Done testing
        return getArrayOfPlayers();
    }

    public ArrayOfPlayers getArrayOfPlayers() {
        return this.arrayOfPlayers;
    }

    /**
     * Sets the dealer at the start of the Game.
     */
    public void setDealerAtStartOfGame() {
        // If round 0, dealer is the highest card
        ArrayList<Player> playersArray = this.arrayOfPlayers.getArrayOfPlayers();
        for (Player p: playersArray){
            tableHand.addCard(p, deckOfCards.dealCard());
        }

        int theDealerId = tableHand.sortedTableHand().get(0).getPlayerId();
        System.out.println(theDealerId);
        playersArray.get(theDealerId).setIsDealer(true);
        this.arrayOfPlayers.updatePlayerStates(playersArray);
        // If round > 0, dealer is the person to the left of the dealer

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
     * Checks the players to see who the dealer is
     * @return
     */
    public int getDealer() {
        ArrayList<Player> players = arrayOfPlayers.getArrayOfPlayers();
        for(Player p: players) {
            if (p.isDealer()) {
                    return p.getPlayerId();
                }
        }
        return -1;
    }

    /**
     * Get Trump Suit
     */
    public Card getTrumpSuit() {
        return this.trumpSuit;
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
     * Sets the trump at the start of the round after all players have been dealt a card
     * @param
     */
    public void setTrump() {
        int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};
        int roundNumber = this.round.getRound();
        int remainingCards = 52 - (cardsToDealPerRound[roundNumber -1] * 4) ;
        System.out.println(deckOfCards.getNumberOfCardsRemaining() + " Number of cards in deck");

        //For other rounds
        if (deckOfCards.getNumberOfCardsRemaining() == remainingCards) {
            this.trumpSuit = deckOfCards.dealCard();
            System.out.println(remainingCards + " number of expected cards");
        }
            // For first round
        else if ( roundNumber == 1 &&(deckOfCards.getNumberOfCardsRemaining() == remainingCards - 4)) {
            this.trumpSuit = deckOfCards.dealCard();
            System.out.println(remainingCards - 4 + " number of expected cards in round 1");
        } else {
            System.out.println("Spoilt broskis, not enough cards in the deck");
        }
   
    }

    /**
     * Add card to player's hand
     * Takes into account the round (different round deals different cards)
     * @param
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

        for (int toTheRightOfDealer = (dealerIndex%3) + 1; toTheRightOfDealer < playersArray.size(); toTheRightOfDealer++ ) {
            playerReceivingCardOrder.add(playersArray.get(toTheRightOfDealer));
        }

        for (int toTheLeftOfDealer = 0; toTheLeftOfDealer <= (dealerIndex%3); toTheLeftOfDealer++ ) {
            playerReceivingCardOrder.add(playersArray.get(toTheLeftOfDealer));
        }

        int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};
        
        for (int cardsToDeal = 0; cardsToDeal < cardsToDealPerRound[round.getRound() - 1]; cardsToDeal++) {
            for (Player p: playerReceivingCardOrder){
                p.setHand(deckOfCards.dealCard());
            }
//            System.out.println("added once");
        }
        Collections.sort(playerReceivingCardOrder, (a, b ) -> a.getPlayerId() - b.getPlayerId());

        // This should be random every time it is run because dealer will always be changing?
//        System.out.println(playerReceivingCardOrder.get(0).getPlayerId());

        this.arrayOfPlayers.updatePlayerStates(playerReceivingCardOrder);

    }


}