package estimate.gamelogic;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import cards.*;
import estimate.player.*;
import estimate.scoreboard.Scoreboard;

import javax.swing.*;

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
    private int round = 1;
    private Scoreboard scoreboard;
    private int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};

 // private Score; <- this score will always be replaced in a new round
    // private ScoreBoard scoreboard;

    public TableHand getTableHand() {
        return this.tableHand;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public void setRound(int round) {
        this.round = round;
    }


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
        Player player2 = new Computer(1,1);
        Player player3 = new Computer(2,2);
        Player player4 = new Computer(3,3);

//        arrayOfPlayers.addPlayer(player0);
        arrayOfPlayers.addPlayer(player1);
        arrayOfPlayers.addPlayer(player2);
        arrayOfPlayers.addPlayer(player3);
        arrayOfPlayers.addPlayer(player4);
    }


    /**
     * STarts a new game
     */
    public ArrayOfPlayers startNewGame() {
        // Remember to start Round from 1 not 0
        initialisePlayers();
        initialiseDeck(); // Done testing
        setDealer(1); // Done testing
//        setPlayersHand(round); //Done testing
//        setTrump(); //Done testing
//        setPlayerOrder(1);
//        System.out.println("Player Order at start of Game:  " + getArrayOfPlayers().getArrayOfPlayers() + "\n");
        return getArrayOfPlayers();
    }




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
            System.out.println("Dealer ID in set Dealer: " + theDealerId );
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

        Suit c = Suit.CLUBS;
        Suit d = Suit.DIAMONDS;
        Suit h = Suit.HEARTS;
        Suit s = Suit.SPADES;

        Rank[] rankCards = {aceCard,twoCard,threeCard,fourCard,fiveCard,sixCard,sevenCard,eightCard,nineCard,tenCard,jackCard,queenCard,kingCard};

        HashMap<Rank, String> rankCardToString = new HashMap<>();
        rankCardToString.put(aceCard, "a");
        rankCardToString.put(twoCard, "2");
        rankCardToString.put(threeCard, "3");
        rankCardToString.put(fourCard, "4");
        rankCardToString.put(fiveCard, "5");
        rankCardToString.put(sixCard, "6");
        rankCardToString.put(sevenCard, "7");
        rankCardToString.put(eightCard, "8");
        rankCardToString.put(nineCard, "9");
        rankCardToString.put(tenCard, "t");
        rankCardToString.put(jackCard, "j");
        rankCardToString.put(queenCard, "q");
        rankCardToString.put(kingCard, "k");

        HashMap<Suit, String> suitToString = new HashMap<>();
        suitToString.put(c, "c");
        suitToString.put(d, "d");
        suitToString.put(h, "h");
        suitToString.put(s, "s");
        Suit[] suits = {c,d,h,s};

        for (Suit suit: suits) {
            for (Rank rank: rankCards) {
                String cardFaceLocation = "/resource/cards/src/"+rankCardToString.get(rank)+suitToString.get(suit)+
                        ".gif";
//                System.out.println(cardFaceLocation);
                ImageIcon cardFace = new ImageIcon(cardFaceLocation);
                Card aCard = new Card(suit,rank,cardFace);
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
        int roundNumber = this.round;
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
            System.out.println("Expected: " + remainingCards  );
        }
   
    }

    /**
     * Add card to player's hand
     * Takes into account the round (different round deals different cards)
     * @param
     */
    public void setPlayersHand(int round){
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

        for (int toTheRightOfDealer = (dealerIndex%3) + 1; toTheRightOfDealer < playersArray.size(); toTheRightOfDealer++ ) {
            playerReceivingCardOrder.add(playersArray.get(toTheRightOfDealer));
        }

        for (int toTheLeftOfDealer = 0; toTheLeftOfDealer <= (dealerIndex%3); toTheLeftOfDealer++ ) {
            playerReceivingCardOrder.add(playersArray.get(toTheLeftOfDealer));
        }
        
        for (int cardsToDeal = 0; cardsToDeal < cardsToDealPerRound[round - 1]; cardsToDeal++) {
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
    public void startSubRound(int roundNumber) {

        tableHand.clearTableHand();
        // Display Table Hand
        System.out.println(tableHand.toString());


        for (Player p : arrayOfPlayers.getArrayOfPlayers()) {
            Card highestPlayedCard;
            Card leadSuit;
            if (tableHand.sortedTableHand().size() == 0 ) {
                highestPlayedCard = null;
                leadSuit = null;
            } else {
                highestPlayedCard = tableHand.sortedTableHand().get(0).getPlayerCard();
                leadSuit = this.leadSuit;
            }

            System.out.println("Player ID: " + p.getPlayerId() + "\n");

            if (p instanceof Computer) {
                System.out.println("Entered Computer");
                Suit leadSuitReplacer;
                if (leadSuit != null) {
                    leadSuitReplacer = leadSuit.getSuit();
                } else {
                    leadSuitReplacer = null;
                }

                Computer pComputer = (Computer) p;
                Card cardForCompToPlay = pComputer.playCard(trumpSuit.getSuit(), leadSuitReplacer, highestPlayedCard);

                if (p.getPosition() == 0) {
                    this.leadSuit = cardForCompToPlay;
                }

                tableHand.addCard(p, p.removeFromHand(cardForCompToPlay));

                // Display Table Hand
                System.out.println(tableHand.toString());

            } else {
                System.out.println("Entered Player");
                //Display Hand to user
                System.out.println(p.getHand());

                // Get input from user
                System.out.println("Enter Your Card Index You want to play");
                Scanner sc = new Scanner(System.in);
                int cardIndex = 0;

                if (p.getPosition() == 0) {
                    this.leadSuit = p.getHand().getCard(cardIndex);
                }

                tableHand.addCard(p,p.removeFromHand(p.getHand().getCard(cardIndex)));


                // Display Table Hand
                System.out.println(tableHand.toString());
            }
        }
//        Evaluate the subround and add to scoreboard
        ArrayList<PlayerCardArray> sortedTableHand = tableHand.sortedTableHand();
        PlayerCardArray winner = sortedTableHand.get(0);

        // Display winner of the round
        System.out.println("The winner is player ID: "+winner.getPlayerId());
        scoreboard.addTricksWon(roundNumber, winner.getPlayerId());

        // Clear tableHand at end of subround
        tableHand.clearTableHand();
    }

    public void startRound(){
        //{1,2,3,4,5,6,5,4,3,2,1}
        // for loop through 11 rounds
        // for each 11 rounds, get the number of subrounds with the set
        // deal cards for the current round //set player hand method
        // Reinitialise deck
        // Ste bid
            //  do a for loop of subround method for the number of subrounds
        // Tabulate the scores for the round
        // check for winner
        // If winner != null, system out print winner ID
        // break out of for loop

        roundLoop:
        for (int round = 1 ; round <= 11 ; round ++) {
            this.round = round;
            int numberOfSubRounds = cardsToDealPerRound[round - 1];
            setPlayersHand(round); //Done testing
            setTrump(); //Done testing
            setPlayerOrder(round);


            // Set the bid for all players
            for (Player p : arrayOfPlayers.getArrayOfPlayers()) {
                if (p instanceof  Computer) {
                    Computer pComputer = (Computer) p;
                    pComputer.bidWinningTricks(numberOfSubRounds, scoreboard.getTotalBidForRound(round),
                            this.trumpSuit.getSuit());
                    int predictedBid = p.getBid();
                    scoreboard.addPrediction(round,p.getPlayerId(),predictedBid);
                } else {
                    // User needs to set the predicted Bids
                    int totalBidsSoFar = scoreboard.getTotalBidForRound(round);
                    ArrayList<Integer> availableBids = p.getAvailableBids(numberOfSubRounds, totalBidsSoFar);

                    //Display list of options user can bid for.
                    int[] availableBidsArray = availableBids.stream().mapToInt(i -> i).toArray();
                    System.out.println(Arrays.toString(availableBidsArray));

                    // Get User to input a bid
                    int predictedBid = 1;
                    scoreboard.addPrediction(round, p.getPlayerId(), predictedBid);
                }
            }

            subRoundLoop:
            for (int subRound = 0; subRound < numberOfSubRounds ; subRound ++) {
                startSubRound(round);
            }
            scoreboard.calculateRoundScore(round);

            // Check for winner
            scoreboard.calculateTotalScore();
            System.out.println(scoreboard.getTotalScore());
            int[] winner = scoreboard.getWinner(round);

            System.out.println("Round: "+ round);
            if (winner != null ) {
                System.out.println("CONGRATZZZ THE WINNER IS: " + winner[0] + " with " + winner[1] + " points!!!!");
                break roundLoop;
            }

            initialiseDeck();

        }

    }

}