package estimate.gamelogic;

import java.util.*;
import java.util.Collections;
import java.util.HashMap;
import cards.*;
import estimate.player.*;
import estimate.scoreboard.Scoreboard;

/**
 * Main logic of game is here. GameLogic is called by the controller to execute business logic
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
    private Scoreboard scoreboard;
    private int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};

 // private Score; <- this score will always be replaced in a new round
    // private ScoreBoard scoreboard;

    /**
     * Sets the order of players at the start of every round / subround
     */
    public void setPlayerOrder(int round){
        int cardsDealtThisRound = cardsToDealPerRound[round - 1];
        int cardsInHand = arrayOfPlayers.getPlayerByIndex(0).getHand().getNumberOfCards();
        if ( cardsInHand == cardsDealtThisRound) {
            int dealerId = getDealer();
            int firstPosition = clockWiseNext(dealerId);
            int secondPosition = clockWiseNext(firstPosition);
            int thirdPosition = clockWiseNext(secondPosition);
            int fourthPosition = clockWiseNext(thirdPosition);

            List<Player> players = arrayOfPlayers.getArrayOfPlayers();
            int[] positionList = new int[]{firstPosition, secondPosition, thirdPosition,fourthPosition};

            arrayOfPlayers.getPlayerByIndex(positionList[0]).setPosition(0);
            arrayOfPlayers.getPlayerByIndex(positionList[1]).setPosition(1);
            arrayOfPlayers.getPlayerByIndex(positionList[2]).setPosition(2);
            arrayOfPlayers.getPlayerByIndex(positionList[3]).setPosition(3);

            ArrayList<Player> playerArray = arrayOfPlayers.getArrayOfPlayers();
            Collections.sort(playerArray, (a, b ) -> a.getPosition() - b.getPosition());

            // This should be random every time it is run because dealer will always be changing?
//        System.out.println(playerReceivingCardOrder.get(0).getPlayerId());

            this.arrayOfPlayers.updatePlayerStates(playerArray);

        } else {
            System.out.println("Entered Else");
            // if not the same cards, then check for isWinner
            // player that isWinner is starts the nextRound
            // everyone to the left is the next subsequent person
            int winnerId = -1;
            for(Player p : arrayOfPlayers.getArrayOfPlayers() ) {
                if (p.getTrickWinner() == true) {
                    winnerId = p.getPlayerId();
                    System.out.println("Winner ID Found Else Statement " + winnerId);
                    p.setTrickWinner(false);
                    break;
                }
            }
            int firstPosition = winnerId;
            int secondPosition = clockWiseNext(firstPosition);
            int thirdPosition = clockWiseNext(secondPosition);
            int fourthPosition = clockWiseNext(thirdPosition);

            ArrayList<Player> players = arrayOfPlayers.getArrayOfPlayers();
            int[] positionList = new int[]{firstPosition, secondPosition, thirdPosition,fourthPosition};
            Collections.sort(players, (a, b ) -> a.getPlayerId() - b.getPlayerId());

            arrayOfPlayers.getPlayerByIndex(positionList[0]).setPosition(0);
            arrayOfPlayers.getPlayerByIndex(positionList[1]).setPosition(1);
            arrayOfPlayers.getPlayerByIndex(positionList[2]).setPosition(2);
            arrayOfPlayers.getPlayerByIndex(positionList[3]).setPosition(3);

            Collections.sort(players, (a, b ) -> a.getPosition() - b.getPosition());

            // This should be random every time it is run because dealer will always be changing?
//        System.out.println(playerReceivingCardOrder.get(0).getPlayerId());
            this.arrayOfPlayers.updatePlayerStates(players);

        }

    }

    public GameLogic() {

        tableHand = new TableHand();
        arrayOfPlayers = new ArrayOfPlayers();
        scoreboard = new Scoreboard();
    }

    // Array Of Players will always be sorted by their position in the arrayList
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
        setDealer(1); // Done testing
        setPlayersHand(round); //Done testing
        setTrump(); //Done testing
        return getArrayOfPlayers();
    }

    /**
     * method of starting a subRound()
     * get the number of cards in hand
     * If num of Cards == cards per trick, first player == left of dealer
     * Get player position and start looping through player to play a card
     *  If player instanceof computer, then play a card
     *  call the play card method of computer
     *  Display the card played
     * If physical normal player, then get input from player
     * Takes in player input and input into the tablehand
     * Evaluate the table hand once 4 cards in the tablehand
     * display winner
     * Set winning player's trick winner attribute
     * Record this trick in round's score
     * Set winning player as the first position and all to the left of him as subsequent players
     */
//    public void startSubRound() {
//        get
//    }


    /**
     *  Gets ArrayOfPlayers
     * @return
     */
    public ArrayOfPlayers getArrayOfPlayers() {
        return this.arrayOfPlayers;
    }

    /**
     * Sets the dealer at the start of the Game.
     */
    public void setDealer(int round) {
        // If round 0, dealer is the highest card
        if (round == 1 ) {
            ArrayList<Player> playersArray = this.arrayOfPlayers.getArrayOfPlayers();
            for (Player p: playersArray){
                tableHand.addCard(p, deckOfCards.dealCard());
            }

            int theDealerId = tableHand.sortedTableHand().get(0).getPlayerId();
            System.out.println(theDealerId + " Dealer ID in set Dealer");
            playersArray.get(theDealerId).setIsDealer(true);
//            this.arrayOfPlayers.updatePlayerStates(playersArray);
        } else {
            ArrayList<Player> playersArray = this.arrayOfPlayers.getArrayOfPlayers();
            int idOfDealer = -1;
            for ( Player p : playersArray) {
                if ( p.isDealer() ){
                    idOfDealer = p.getPlayerId();
                    p.setIsDealer(false);
                    break;
                }
            }
            System.out.println("Previous Dealer: "+idOfDealer);
            // set the left of dealerId
            int newDealerId =clockWiseNext(idOfDealer);

            arrayOfPlayers.getPlayerByIndex(newDealerId).setIsDealer(true);

        }

        // If round > 0, dealer is the person to the left of the dealer

    }

    public int clockWiseNext(int id) {
        int result = id - 1;
        if (result == -1 ) {
            result = 3;
        }

        return result;
    }

    public Deck getDeck() {
        return deckOfCards;
    }

    /**
     * Adds 52 cards to deck
     */
    public void initialiseDeck() {
        deckOfCards = new Deck();
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
        Collections.sort(playersArray, (a, b ) -> a.getPlayerId() - b.getPlayerId());
        int dealerIndex = 0;
        for (int playerCounter = 0; playerCounter < playersArray.size() ; playerCounter ++ ) {
            Player selectedPlayer = playersArray.get(playerCounter);
            if (selectedPlayer.isDealer()) {
                dealerIndex = selectedPlayer.getPlayerId();
                break;
            }
        }
        System.out.println("Dealer index: " + dealerIndex);

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
        Collections.sort(playerReceivingCardOrder, (a, b ) -> a.getPosition() - b.getPosition() );

        // This should be random every time it is run because dealer will always be changing?
//        System.out.println(playerReceivingCardOrder.get(0).getPlayerId());

        this.arrayOfPlayers.updatePlayerStates(playerReceivingCardOrder);

    }


}