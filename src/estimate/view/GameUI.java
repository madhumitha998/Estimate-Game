package estimate.view;

import cards.Card;
import cards.Suit;
import estimate.gamelogic.GameLogic;
import estimate.gamelogic.PlayerCardArray;
import estimate.player.Computer;
import estimate.player.Player;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Main GUI of the game. Contains all the methods to display selected options by player.
 * GameUI is called by the main menu, GUI. The logic is controlled by GameLogic.
 * @author Elias Lim
 * @version 2.8
 * @since   Nov 4, 2019
 */

public class GameUI {
	private JFrame estimationGame;
	private SpringLayout springLayout;

	private GameLogic gameLogic;
	private int numberOfSubRounds;
	private int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};

	private int round = 1;
	private int subRound = 0;
	private int roundCounter = 0;
	private int currentPlayer;
	private int roundBidsWon = 0;
	private int roundBidsWon1 = 0;
	private int roundBidsWon2 = 0;
	private int roundBidsWon3 = 0;
	private Boolean waitingUser;

	private int cardWidth = 75;
    private int cardHeight = 108;

    private JLabel lblNameA;
	private JLabel lblNameB;
	private JLabel lblNameC;
	private JLabel lblNameD;
	private JButton btnAvatarA;
    private JButton btnCardA;
    private JButton btnCardB;
    private JButton btnCardC;
    private JButton btnCardD;
	private JLabel lblScoreA;
	private JLabel lblScoreB;
	private JLabel lblScoreC;
	private JLabel lblScoreD;
	private JLabel lblNoti;
	private String notification;
	private String winnerName;

	private ArrayList<JLabel> bidList = new ArrayList<JLabel>();
	private ArrayList<JLabel> bidList1 = new ArrayList<JLabel>();
	private ArrayList<JLabel> bidList2 = new ArrayList<JLabel>();
	private ArrayList<JLabel> bidList3 = new ArrayList<>();
	private ArrayList<JLabel> bidWonList = new ArrayList<JLabel>();
	private ArrayList<JLabel> bidWonList1 = new ArrayList<JLabel>();
	private ArrayList<JLabel> bidWonList2 = new ArrayList<JLabel>();
	private ArrayList<JLabel> bidWonList3 = new ArrayList<JLabel>();
	private ArrayList<JLabel> leadList = new ArrayList<JLabel>();
	private ArrayList<JLabel> trumpList = new ArrayList<JLabel>();
	private ArrayList<JLabel> roundList = new ArrayList<JLabel>();
	private ArrayList<JLabel> subRoundList = new ArrayList<JLabel>();
	private ArrayList<JLabel> positionList = new ArrayList<>();
	private ArrayList<JLabel> positionList1 = new ArrayList<>();
	private ArrayList<JLabel> positionList2 = new ArrayList<>();
	private ArrayList<JLabel> positionList3 = new ArrayList<>();
	private ArrayList<JButton> listButton = new ArrayList<JButton>();
	private ArrayList<JButton> listButton1 = new ArrayList<JButton>();
	private ArrayList<JButton> listButton2 = new ArrayList<JButton>();
	private ArrayList<JButton> listButton3 = new ArrayList<JButton>();


	public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameUI window = new GameUI();
                    window.initializeGame();

                    window.estimationGame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
	}


	public GameUI(){
	}

	/**
     * Sets the frame and attributes for the game.
     * Initalizes the variables from GameLogic
     * Initalizes computer and player
     * Adds button to start the game
     */
	private void initializeGame(){
		estimationGame = new JFrame();
		estimationGame.setTitle("Estimation Game");
        estimationGame.getContentPane().setFont(new Font("Tahoma", Font.BOLD, 15));
        estimationGame.getContentPane().setBackground(new Color(7,99,36));
        estimationGame.setBackground(SystemColor.desktop);
        estimationGame.setResizable(false);
        estimationGame.setBounds(50, 50, 950, 850);
        estimationGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        springLayout = new SpringLayout();
        estimationGame.getContentPane().setLayout(springLayout);

        notification = "Click your player icon to start game!";

		/* initializes the players */
        initPlayer0();
        initPlayer1();
        initPlayer2();
        initPlayer3();

        initNoti();
        drawNoti(null);

		/* initializes global variables in gamelogic */
        gameLogic = new GameLogic();
        gameLogic.getScoreboard();
		gameLogic.getTableHand();
		gameLogic.startNewGame();

		/* starts the game on button click */
        btnAvatarA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notification = "";
                newRound();
            }
        });
	}

	/**
     * Starts a new round for the game to be played
     * This method is called at the start of the game, and after every subround/round
     */
	private void newRound(){
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("PREPPING ROUND " + round + " SETUP. Game Logic round (should be aligned) : " + gameLogic.getRound());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		System.out.println("PRINTING LEADSUIT OF NEW ROUND " + gameLogic.getLeadSuitCard());
		gameLogic.initialiseDeck();
		gameLogic.setDealer(round);
		
		System.out.println("Players Hand (Should be empty): " + gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(0).getHand());
		
		gameLogic.setPlayersHand(round);
		gameLogic.setTrumpCard();
		gameLogic.setPlayerOrder(round);
		gameLogic.getTableHand().clearTableHand();

		waitingUser = false;
		if (waitingUser()){
			waitingUser = true;
		}

		currentPlayer = gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(0).getPlayerId();

		displayRoundUI();
		displayTrumpUI();
		displayCardUI();
		displayAvailableBidsUI();
		displayTableHandUI();

		System.out.println("BEGIN ROUND " + round);
		todoThread();
	}


	/**
     * Starts the sequence of events for the game
     * Checks if SubRound / Round / Game has been completed
     * If not, continue playing
     */
	public void todoThread() {
		while (true) {
			numberOfSubRounds = cardsToDealPerRound[round - 1];
			displaySubRoundUI();

			/* checks if a subround is completed */
			if (completeSubRound()) {
				/* execute calculations for trick winner and clean up after the subround */
				finishSubRound();

				/* clears the lead suit and displays the cards left */
				gameLogic.setLeadSuitCard(null);
				clearLeadUI();
				displayTableHandUI();
				displayCardUI();

				roundCounter++;

				/* checks if round is completed */
				if (completeRound()) {

					/* updates number of tricks won for the UI */
					roundBidsWon = 0;
					roundBidsWon1 = 0;
					roundBidsWon2 = 0;
					roundBidsWon3 = 0;

					/* updates the scoreboard */
					updateScoreUI();
            		System.out.println(gameLogic.getScoreboard().getTotalScore());
            		int[] winner = gameLogic.getScoreboard().getWinner(round);

					/* check if game is completed */
            		if (completeGame(winner)){
            			JOptionPane.showMessageDialog(new JFrame(), "THE GRAND WINNER IS Player " + winner[0] + " with " + winner[1] + " POINTS!!!!", "Info",
                                JOptionPane.INFORMATION_MESSAGE);
            			MainMenuUI.main(null);
            			return;
            		} else {
            			newRound();
            		}
            		return;
				}
			}

			/* continues playing logic */
			if (waitingUser) {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~PLAYER'S TURN~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				drawNoti("Your turn");
				break;
			} else {
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~COMPUTER'S TURN~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				drawNoti("...");
				playSubRound(-1);
				displayTableHandUI();

				if (waitingUser()) {
					displayTableHandUI();
					displayCardUI();
					waitingUser = true;
				}
			}
		}
	}


	/**
     * Starts the sequence of events for a SubRound.
     * This is the main method for the player and computer to play the game
     */
	public void playSubRound(int index) {
		System.out.println();
		System.out.println("PRINTING ARRAY OF PLAYERS:");
		System.out.println(gameLogic.getArrayOfPlayers().getArrayOfPlayers());

		displayPositionUI();
		displayBidsWonUI();

		if (currentPlayer ==  gameLogic.getArrayOfPlayers().getArrayOfPlayers().get( gameLogic.getArrayOfPlayers().getArrayOfPlayers().size()-1).getPlayerId()){
			subRound += 1;
		}

		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
			if (currentPlayer == p.getPlayerId()) {
				if (currentPlayer == 3){
					currentPlayer = 0;
				} else {
					currentPlayer += 1;
				}

				/* Get the highest card in the hand */
		        Card highestPlayedCard;
		        Suit leadSuit2;

		        if (gameLogic.getLeadSuitCard() == null) {
		            leadSuit2 = null;
		        } else {
		            leadSuit2 = gameLogic.getLeadSuitCard().getSuit();
		        }

		        if (gameLogic.getTableHand().sortedTableHand( gameLogic.getTrumpCard().getSuit(), leadSuit2 ).size() == 0 ) {
		            highestPlayedCard = null;
		        } else {
		            highestPlayedCard = gameLogic.getTableHand().sortedTableHand(gameLogic.getTrumpCard().getSuit(), gameLogic.getLeadSuitCard().getSuit()).get(0).getPlayerCard();
		        }

				/* if first player is a computer, play the first card and set the lead suit */
		        if (p instanceof Computer) {
	                Computer pComputer = (Computer) p;
	                System.out.println("Printing computer: " + pComputer);

	                Card cardForCompToPlay = pComputer.playCard(gameLogic.getTrumpCard().getSuit(), leadSuit2, highestPlayedCard);
	                System.out.println("Computer's Hand" + p.getHand() + "\n");

	                if (p.getPosition() == 0) {
	                    gameLogic.setLeadSuitCard(cardForCompToPlay);
	                	String leadSuitString = "" + gameLogic.getLeadSuitCard().getSuit();
	                    displayLead(leadSuitString);
	                }

	                gameLogic.getTableHand().addCard(p, p.removeFromHand(cardForCompToPlay));

					/* Display Table Hand */
					displayTableHandUI();
	                System.out.println(gameLogic.getTableHand().toString());
	                break;

	            } else {
					/* if first player is the player, play the first card and set the lead suit */

					/* Display Hand to user */
	                System.out.println("Player's Hand: " + p.getHand());

					/* Get input from user */
	                displayCardUI();

	                try {
		                if (p.getPosition() == 0) {
		                    gameLogic.setLeadSuitCard(p.getHand().getCard(index));
		                	String leadSuitString = "" + gameLogic.getLeadSuitCard().getSuit();
		                    displayLead(leadSuitString);
		                }
                		gameLogic.getTableHand().addCard(p,p.removeFromHand(p.getHand().getCard(index)));
		            } catch (IndexOutOfBoundsException error){
		            }

	                displayTableHandUI();
	                // Display Table Hand
            		break;
	            }
	        }
	    }
	}


	/**
     * After each SubRound, this method is called to calculate the scores and get the trick winner
     */
	public void finishSubRound(){

		/* sorts the cards on in the table pool in a decsending order */
        List<PlayerCardArray> sortedTableHand =
				gameLogic.getTableHand().sortedTableHand(gameLogic.getTrumpCard().getSuit(),
                gameLogic.getLeadSuitCard().getSuit());

        System.out.println(sortedTableHand);

        PlayerCardArray winner = sortedTableHand.get(0);

		/* Display winner of the round */
        System.out.println("The winner of this round is player ID: " + winner.getPlayerId());
        gameLogic.getScoreboard().addTricksWon(round, winner.getPlayerId());

		/* Get the winner name */
		if (winner.getPlayerId() == 0){
			winnerName = lblNameA.getText();
		} else if (winner.getPlayerId() == 1){
			winnerName = lblNameB.getText();
		}else if (winner.getPlayerId() == 2){
			winnerName = lblNameC.getText();
		}else if (winner.getPlayerId() == 3){
			winnerName = lblNameD.getText();
		}

		/* displays the message dialog box informing winner of the round */
        JOptionPane.showMessageDialog(null, "Round: " + round + "\nSubround: " + (roundCounter+1) + "\nWinner is: " + winnerName);

		/* set Winner for player */
        for (Player p : gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
            if ( p.getPlayerId() == winner.getPlayerId() ) {
                p.setTrickWinner(true);
            } else {
                p.setTrickWinner(false);
            }
        }

		/* updates the UI informing play how many tricks won so far */
		for (Player p : gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
			if (winner.getPlayerId() == 0){
				roundBidsWon += 1;
				break;
			} else if (winner.getPlayerId() == 1){
				roundBidsWon1 += 1;
				break;
			} else if (winner.getPlayerId() == 2){
				roundBidsWon2 += 1;
				break;
			} else if (winner.getPlayerId() == 3){
				roundBidsWon3 += 1;
				break;
			}
		}

		/* Set position for players */
        System.out.println("OLD PLAYER ORDER: " + gameLogic.getArrayOfPlayers().getArrayOfPlayers());
        if (round != 11) {
            gameLogic.setPlayerOrder(round);
            currentPlayer = gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(0).getPlayerId();
            if (currentPlayer == 0){
            	System.out.println("YOU ARE NOW THE FIRST PERSON TO PLAY");
            	waitingUser = true;
            } else {
            	waitingUser = false;
            }
            System.out.println("NEW PLAYER ORDER: " + gameLogic.getArrayOfPlayers().getArrayOfPlayers());
        }

		/* Clear tableHand at end of subround */
        gameLogic.getTableHand().clearTableHand();
	}


	/**
     * Checks if SubRound is completed
     */
	public Boolean completeSubRound() {
		if (subRound != 1) {
			return false;
		} else {
			System.out.println("#################################SUBROUND COMPLETED#################################");
			subRound = 0;
			return true;
		}
	}


	/**
     * Checks if Round is completed
     */
	public Boolean completeRound(){
		int[] roundsTotal = {1,2,3,4,5,6,5,4,3,2,1};

		if (roundsTotal[round-1] == roundCounter) {
			System.out.println("ROUNDCOUNTER ####### " + roundCounter);
			System.out.println("TOTAL SUBROUND ####### " + cardsToDealPerRound[round-1]);
			System.out.println("ROUND ####### " + round);
			gameLogic.getScoreboard().calculateRoundScore(round);
			gameLogic.getScoreboard().calculateTotalScore();
			roundCounter = 0;
			round += 1;
			gameLogic.setRound(round);

			System.out.println("#################################ROUND COMPLETED#################################");
			
			return true;
		} else {
			return false;
		}
	}


	/**
     * Checks if Game is completed
     */
	public Boolean completeGame(int[] winner) {
		if (winner != null) {
			System.out.println("CONGRATZZZ THE WINNER IS: " + winner[0] + " with " + winner[1] + " points!!!!");
			return true;
		}
		return false;
	}


	/**
     * Displays the card UI and executes the player action of selecting a card
     */
	public void displayCardUI() {
		for (Player player: gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
			ArrayList<Card> playerHandCards = player.getHand().getHand();

			/* Displays the card back for Computer 1 */
			if (player.getPlayerId() == 1) {
				float cardIndex = -1;
				int cardLeft = 55;
				int cardTop = 235;

				for (JButton item: listButton1){
					estimationGame.getContentPane().remove(item);
				}
				listButton1.clear();

				for (int i = 0; i < playerHandCards.size(); i++) {
		            cardIndex++;

					JButton btnCard1 = new JButton();

					springLayout.putConstraint(SpringLayout.WEST, btnCard1, (int) (cardLeft),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.EAST, btnCard1, (int) (cardLeft + cardHeight),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.NORTH, btnCard1, (int) (cardTop + cardIndex * cardWidth), SpringLayout.NORTH,
			            estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.SOUTH, btnCard1, (int) (cardTop + (cardIndex + 1) * cardWidth), SpringLayout.NORTH,
			            estimationGame.getContentPane());

			        try {
		                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
		                btnCard1.setIcon(new ImageIcon(
		                        img.getImage().getScaledInstance(cardHeight, cardWidth, java.awt.Image.SCALE_SMOOTH)));
		            } catch (NullPointerException e) {
		                System.out.println("Image not found");
		                btnCard1.setText("Not found");
		            }
		            listButton1.add(btnCard1);
			        estimationGame.getContentPane().add(btnCard1);
			    }
			}

			/* Displays the card back for Computer 2 */
			if (player.getPlayerId() == 2) {
				float cardIndex = -1;
				int cardLeft = 300;
				int cardTop = 30;

				for (JButton item: listButton2) {
					estimationGame.getContentPane().remove(item);
				}
				listButton2.clear();

				for (int i = 0; i < playerHandCards.size(); i++) {
		            cardIndex++;

					JButton btnCard2 = new JButton();

					springLayout.putConstraint(SpringLayout.WEST, btnCard2, (int) (cardLeft + cardIndex * cardWidth),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.EAST, btnCard2, (int) (cardLeft + (cardIndex + 1) * cardWidth),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.NORTH, btnCard2, cardTop, SpringLayout.NORTH,
			            estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.SOUTH, btnCard2, cardTop + cardHeight, SpringLayout.NORTH,
			            estimationGame.getContentPane());

			        try {
		                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
		                btnCard2.setIcon(new ImageIcon(
		                        img.getImage().getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH)));
		            } catch (NullPointerException e) {
		                System.out.println("Image not found");
		                btnCard2.setText("Not found");
		            }

		            listButton2.add(btnCard2);
			        estimationGame.getContentPane().add(btnCard2);
		    	}
			}

			/* Displays the card back for Computer 3 */
			if (player.getPlayerId() == 3) {
				float cardIndex = -1;
				int cardLeft = 750;
				int cardTop = 215;

				for (JButton item: listButton3) {
					estimationGame.getContentPane().remove(item);
				}
				listButton3.clear();

				for (int i = 0; i < playerHandCards.size(); i++) {
		            cardIndex++;

					JButton btnCard3 = new JButton();

			        springLayout.putConstraint(SpringLayout.WEST, btnCard3, (int) (cardLeft),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.EAST, btnCard3, (int) (cardLeft + cardHeight),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.NORTH, btnCard3, (int) (cardTop + cardIndex * cardWidth), SpringLayout.NORTH,
			            estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.SOUTH, btnCard3, (int) (cardTop + (cardIndex + 1) * cardWidth), SpringLayout.NORTH,
			            estimationGame.getContentPane());

			        try {
		                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
		                btnCard3.setIcon(new ImageIcon(
		                        img.getImage().getScaledInstance(cardHeight, cardWidth, java.awt.Image.SCALE_SMOOTH)));
		            } catch (NullPointerException e) {
		                System.out.println("Image not found");
		                btnCard3.setText("Not found");
		            }

		            listButton3.add(btnCard3);
			        estimationGame.getContentPane().add(btnCard3);
		    	}
			}

			/* Displays the cards for Player 0 */
			if (!(player instanceof Computer)) {
				System.out.println("PLAYERS HAND to PLAY: "  + playerHandCards);
				int cardLeft = 300;
				int cardTop = 675;

				float cardIndex = -1;

				// remove all UI card
				for (JButton item: listButton){
					estimationGame.getContentPane().remove(item);
				}
				listButton.clear();

				ArrayList<Card> listCard = playerHandCards;
				ArrayList<Card> playableCards = getPlayableCards(player);

				for (int i = 0; i < listCard.size(); i++) {
		            cardIndex++;

		            Card card = listCard.get(i);
			        JButton btnCard = new JButton();
			        btnCard.setFocusPainted(false);
			        btnCard.setName(i + "");

			        springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + cardIndex * cardWidth),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + (cardIndex + 1) * cardWidth),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
			            estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + cardHeight, SpringLayout.NORTH,
			            estimationGame.getContentPane());

			        try {
			        	ImageIcon cardImg = card.getCardImage();
			        	btnCard.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH)));
			        } catch (NullPointerException e){
			        	System.out.println("Image not found");
			        	btnCard.setText("Not found");
			        }

					/* if the player is the first to play, allow all cards to be played */
					if (player.getPosition() != 0) {
						if (!(playableCards.contains(card))) {
							btnCard.setEnabled(false);
						}
					}

			        btnCard.addActionListener(new ActionListener() {
			        	@Override
			        	public void actionPerformed(ActionEvent e){
			        		if (waitingUser){
			        			int index = Integer.parseInt(btnCard.getName());
			        			System.out.println("CLICKING BUTTON CARD NOW: " +  index);
			        			passSelectedCard(index);
			        		}
			        	}
			        });

			    listButton.add(btnCard);
	        	estimationGame.getContentPane().add(btnCard);
			    }
			}
        }
        estimationGame.validate();
        estimationGame.repaint();
	}


	/**
     * Passes the selected card chosen in method displayCardUI() to playSubRound() method
     */
	public void passSelectedCard(int index){
		System.out.println("PLAYER SELECTED CARD INDEX " + index);
		waitingUser = false;
		playSubRound(index);

		todoThread();
	}


	/**
     * Gets the cards that can be played for a round for the player
     */
	public ArrayList<Card> getPlayableCards(Player p) {
        ArrayList<Card> playableCards;
	    if (gameLogic.getLeadSuitCard() == null) {
	        playableCards = p.getPlayableHand(null, gameLogic.getTrumpCard().getSuit());
	        System.out.println(playableCards);
	        System.out.println("Player's playable Cards: " + p.getPlayableHand(null, gameLogic.getTrumpCard().getSuit()));
	    } else {
	        playableCards = p.getPlayableHand(gameLogic.getLeadSuitCard().getSuit(), gameLogic.getTrumpCard().getSuit());
	        System.out.println("Player's playable Cards: " + p.getPlayableHand(gameLogic.getLeadSuitCard().getSuit(),
	                gameLogic.getTrumpCard().getSuit()));
	    }

	    return playableCards;
	}


	/**
     * Displays the UI of all available bids for the player to select from
     */
	public void displayAvailableBidsUI() {
		System.out.println("BIDDING TIME");

        ArrayList<Integer> availableBids = new ArrayList<Integer>();
        numberOfSubRounds = cardsToDealPerRound[round - 1];

		/* loop through the players and set their bids */
		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
			if (p instanceof Computer) {
				waitingUser = false;
				Computer pComputer = (Computer) p;

				pComputer.bidWinningTricks(numberOfSubRounds, gameLogic.getScoreboard().getTotalBidForRound(round),
                            gameLogic.getTrumpCard().getSuit());
				int predictedBid = p.getBid();
				gameLogic.getScoreboard().addPrediction(round,p.getPlayerId(),predictedBid);
			} else {
				// User needs to set the predicted Bids
                int totalBidsSoFar = gameLogic.getScoreboard().getTotalBidForRound(round);
                availableBids = p.getAvailableBids(numberOfSubRounds, totalBidsSoFar);
		    } 
		}

		String[] availableBidsString = new String[availableBids.size()];
		for (int i = 0; i < availableBids.size(); i++) {
			availableBidsString[i] = Integer.toString(availableBids.get(i));
		}

		/* check if it is the player's turn */
		if (waitingUser()) {
			waitingUser = true;
		} else {
			waitingUser = false;
		}

		/* prompt the user to select a bid with dialog box */
    	int bidSelected = JOptionPane.showOptionDialog(null,
                        "Select one from the available bids:", "BID WINDOW", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, availableBidsString, availableBidsString[0]);
		
		displayBidUI(bidSelected);

		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()){
			if (!(p instanceof Computer)){
				p.setBid(bidSelected);
			    gameLogic.getScoreboard().addPrediction(round, p.getPlayerId(), bidSelected);
			}
		}
    	estimationGame.validate();
		estimationGame.repaint();
	}


	/**
	 * Displays the position message UI
	 */
	public void displayPositionUI(){
		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {

			if (p.getPlayerId() == 0) {
				if (!(positionList.isEmpty())) {
					for (JLabel item : positionList) {
						estimationGame.getContentPane().remove(item);
					}
					positionList.clear();
				}

				JLabel lblPosition = new JLabel("Position: " + (p.getPosition()+1));

				springLayout.putConstraint(SpringLayout.WEST, lblPosition, 295, SpringLayout.WEST,
						estimationGame.getContentPane());
				lblPosition.setForeground(Color.WHITE);
				springLayout.putConstraint(SpringLayout.NORTH, lblPosition, 555, SpringLayout.NORTH,
						estimationGame.getContentPane());
				lblPosition.setFont(new Font("Tahoma", Font.PLAIN, 15));

				positionList.add(lblPosition);
				estimationGame.getContentPane().add(lblPosition);
			}

			if (p.getPlayerId() == 1) {
				if (!(positionList1.isEmpty())) {
					for (JLabel item : positionList1) {
						estimationGame.getContentPane().remove(item);
					}
					positionList1.clear();
				}

				JLabel lblPosition1 = new JLabel("Position: " + (p.getPosition()+1));

				springLayout.putConstraint(SpringLayout.WEST, lblPosition1, 190, SpringLayout.WEST,
						estimationGame.getContentPane());
				lblPosition1.setForeground(Color.WHITE);
				springLayout.putConstraint(SpringLayout.NORTH, lblPosition1, 440, SpringLayout.NORTH,
						estimationGame.getContentPane());
				lblPosition1.setFont(new Font("Tahoma", Font.PLAIN, 15));

				positionList1.add(lblPosition1);
				estimationGame.getContentPane().add(lblPosition1);
			}

			if (p.getPlayerId() == 2) {
				if (!(positionList2.isEmpty())) {
					for (JLabel item : positionList2) {
						estimationGame.getContentPane().remove(item);
					}
					positionList2.clear();
				}

				JLabel lblPosition2 = new JLabel("Position: " + (p.getPosition()+1));

				springLayout.putConstraint(SpringLayout.WEST, lblPosition2, 275, SpringLayout.WEST,
						estimationGame.getContentPane());
				lblPosition2.setForeground(Color.WHITE);
				springLayout.putConstraint(SpringLayout.NORTH, lblPosition2, 150, SpringLayout.NORTH,
						estimationGame.getContentPane());
				lblPosition2.setFont(new Font("Tahoma", Font.PLAIN, 15));

				positionList2.add(lblPosition2);
				estimationGame.getContentPane().add(lblPosition2);
			}

			if (p.getPlayerId() == 3) {
				if (!(positionList3.isEmpty())) {
					for (JLabel item : positionList3) {
						estimationGame.getContentPane().remove(item);
					}
					positionList3.clear();
				}

				JLabel lblPosition3 = new JLabel("Position: " + (p.getPosition()+1));

				springLayout.putConstraint(SpringLayout.WEST, lblPosition3, 620, SpringLayout.WEST,
						estimationGame.getContentPane());
				lblPosition3.setForeground(Color.WHITE);
				springLayout.putConstraint(SpringLayout.NORTH, lblPosition3, 440, SpringLayout.NORTH,
						estimationGame.getContentPane());
				lblPosition3.setFont(new Font("Tahoma", Font.PLAIN, 15));

				positionList3.add(lblPosition3);
				estimationGame.getContentPane().add(lblPosition3);
			}
		}

		estimationGame.validate();
		estimationGame.repaint();
	}


	/**
     * Displays a message that informs the player the tricks amount he selected
     */
	public void displayBidUI(int bidSelected) {
		if (!(bidList.isEmpty())) {
			for (JLabel item: bidList) {
				estimationGame.getContentPane().remove(item);
			}
			bidList.clear();
		}

		JLabel lblBid = new JLabel("Tricks to win: " + bidSelected);

        springLayout.putConstraint(SpringLayout.WEST, lblBid, 295, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblBid.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblBid, 575, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblBid.setFont(new Font("Tahoma", Font.PLAIN, 15));

		bidList.add(lblBid);
		estimationGame.getContentPane().add(lblBid);

		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
			if (p instanceof Computer) {

				if (p.getPlayerId() == 1) {
					if (!(bidList1.isEmpty())) {
						for (JLabel item : bidList1) {
							estimationGame.getContentPane().remove(item);
						}
						bidList1.clear();
					}

					JLabel lblBid1 = new JLabel("Tricks to win: " + p.getBid());

					springLayout.putConstraint(SpringLayout.WEST, lblBid1, 190, SpringLayout.WEST,
							estimationGame.getContentPane());
					lblBid1.setForeground(Color.WHITE);
					springLayout.putConstraint(SpringLayout.NORTH, lblBid1, 460, SpringLayout.NORTH,
							estimationGame.getContentPane());
					lblBid1.setFont(new Font("Tahoma", Font.PLAIN, 15));

					bidList1.add(lblBid1);
					estimationGame.getContentPane().add(lblBid1);
				}

				if (p.getPlayerId() == 2) {
					if (!(bidList2.isEmpty())) {
						for (JLabel item : bidList2) {
							estimationGame.getContentPane().remove(item);
						}
						bidList2.clear();
					}

					JLabel lblBid2 = new JLabel("Tricks to win: " + p.getBid());

					springLayout.putConstraint(SpringLayout.WEST, lblBid2, 275, SpringLayout.WEST,
							estimationGame.getContentPane());
					lblBid2.setForeground(Color.WHITE);
					springLayout.putConstraint(SpringLayout.NORTH, lblBid2, 170, SpringLayout.NORTH,
							estimationGame.getContentPane());
					lblBid2.setFont(new Font("Tahoma", Font.PLAIN, 15));

					bidList2.add(lblBid2);
					estimationGame.getContentPane().add(lblBid2);
				}

				if (p.getPlayerId() == 3) {
					if (!(bidList3.isEmpty())) {
						for (JLabel item : bidList3) {
							estimationGame.getContentPane().remove(item);
						}
						bidList3.clear();
					}

					JLabel lblBid3 = new JLabel("Tricks to win: " + p.getBid());

					springLayout.putConstraint(SpringLayout.WEST, lblBid3, 620, SpringLayout.WEST,
							estimationGame.getContentPane());
					lblBid3.setForeground(Color.WHITE);
					springLayout.putConstraint(SpringLayout.NORTH, lblBid3, 460, SpringLayout.NORTH,
							estimationGame.getContentPane());
					lblBid3.setFont(new Font("Tahoma", Font.PLAIN, 15));

					bidList3.add(lblBid3);
					estimationGame.getContentPane().add(lblBid3);
				}
			}
		}
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
	 * Displays a message that informs the player how many tricks he won that round
	 */
	public void displayBidsWonUI() {
		if (!(bidWonList.isEmpty())) {
			for (JLabel item: bidWonList) {
				estimationGame.getContentPane().remove(item);
			}
			bidWonList.clear();
		}

		JLabel lblBidsWon = new JLabel("Tricks won: " + roundBidsWon);

		springLayout.putConstraint(SpringLayout.WEST, lblBidsWon, 295, SpringLayout.WEST,
				estimationGame.getContentPane());
		lblBidsWon.setForeground(Color.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, lblBidsWon, 595, SpringLayout.NORTH,
				estimationGame.getContentPane());
		lblBidsWon.setFont(new Font("Tahoma", Font.PLAIN, 15));

		if (!(bidWonList1.isEmpty())) {
			for (JLabel item: bidWonList1) {
				estimationGame.getContentPane().remove(item);
			}
			bidWonList1.clear();
		}

		JLabel lblBidsWon1 = new JLabel("Tricks won: " + roundBidsWon1);

		springLayout.putConstraint(SpringLayout.WEST, lblBidsWon1, 190, SpringLayout.WEST,
				estimationGame.getContentPane());
		lblBidsWon1.setForeground(Color.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, lblBidsWon1, 480, SpringLayout.NORTH,
				estimationGame.getContentPane());
		lblBidsWon1.setFont(new Font("Tahoma", Font.PLAIN, 15));

		if (!(bidWonList2.isEmpty())) {
			for (JLabel item: bidWonList2) {
				estimationGame.getContentPane().remove(item);
			}
			bidWonList2.clear();
		}

		JLabel lblBidsWon2 = new JLabel("Tricks won: " + roundBidsWon2);

		springLayout.putConstraint(SpringLayout.WEST, lblBidsWon2, 275, SpringLayout.WEST,
				estimationGame.getContentPane());
		lblBidsWon2.setForeground(Color.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, lblBidsWon2, 190, SpringLayout.NORTH,
				estimationGame.getContentPane());
		lblBidsWon2.setFont(new Font("Tahoma", Font.PLAIN, 15));

		if (!(bidWonList3.isEmpty())) {
			for (JLabel item: bidWonList3) {
				estimationGame.getContentPane().remove(item);
			}
			bidWonList3.clear();
		}

		JLabel lblBidsWon3 = new JLabel("Tricks won: " + roundBidsWon3);

		springLayout.putConstraint(SpringLayout.WEST, lblBidsWon3, 620, SpringLayout.WEST,
				estimationGame.getContentPane());
		lblBidsWon3.setForeground(Color.WHITE);
		springLayout.putConstraint(SpringLayout.NORTH, lblBidsWon3, 480, SpringLayout.NORTH,
				estimationGame.getContentPane());
		lblBidsWon3.setFont(new Font("Tahoma", Font.PLAIN, 15));

		bidWonList.add(lblBidsWon);
		estimationGame.getContentPane().add(lblBidsWon);
		bidWonList1.add(lblBidsWon1);
		estimationGame.getContentPane().add(lblBidsWon1);
		bidWonList2.add(lblBidsWon2);
		estimationGame.getContentPane().add(lblBidsWon2);
		bidWonList3.add(lblBidsWon3);
		estimationGame.getContentPane().add(lblBidsWon3);

		estimationGame.validate();
		estimationGame.repaint();
	}


	/**
     * Displays the card UI of the cards played by the player and the computer
     */
	public void displayTableHandUI() {
		List<PlayerCardArray> tableHandCards = gameLogic.getTableHand().getTableHand();
		drawNoti(null);
		btnCardA.setVisible(false);
        btnCardB.setVisible(false);
        btnCardC.setVisible(false);
        btnCardD.setVisible(false);

		for (PlayerCardArray playerCard: tableHandCards){
			Card playedCard = playerCard.getPlayerCard();
			
			if (playerCard.getPlayerId() == 0){
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardA.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardA.setText("Not found");
		        }

		        btnCardA.setVisible(true);

			} else if (playerCard.getPlayerId() == 1){
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardB.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardB.setText("Not found");
		        }

		        btnCardB.setVisible(true);
			}

			if (playerCard.getPlayerId() == 2) {
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardC.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardC.setText("Not found");
		        }

		        btnCardC.setVisible(true);
			}

			if (playerCard.getPlayerId() == 3) {
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardD.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardD.setText("Not found");
		        }

		        btnCardD.setVisible(true);
			}
		}
		estimationGame.validate();
		estimationGame.repaint();
	}


	/**
     * Checks whether the user is the first player of the round
     */
	public Boolean waitingUser(){
		if (currentPlayer == 0){
			System.out.println("You are the first player, changing waitingUser to true");
			return true;
		} else {
			return false;
		}
	}


	/**
     * Updates and display the UI of the total score displayed
     */
	public void updateScoreUI() {
		Map<Integer, Integer> scoreMap = gameLogic.getScoreboard().getTotalScore();

		lblScoreA.setText(String.valueOf(scoreMap.get(0)));
        lblScoreB.setText(String.valueOf(scoreMap.get(1)));
        lblScoreC.setText(String.valueOf(scoreMap.get(2)));
        lblScoreD.setText(String.valueOf(scoreMap.get(3)));
	}


	/**
     * Displays a message informing player of the round number
     */
	public void displayRoundUI() {

		if (!(roundList.isEmpty())){
			for (JLabel item: roundList){
				estimationGame.getContentPane().remove(item);
			}
			roundList.clear();
		}

		JLabel lblRound = new JLabel("Round: " + round);

        springLayout.putConstraint(SpringLayout.WEST, lblRound, 10, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblRound.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblRound, 50, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblRound.setFont(new Font("Tahoma", Font.PLAIN, 15));

        roundList.add(lblRound);
        estimationGame.getContentPane().add(lblRound);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
     * Displays a message informing player of the subround number
     */
	public void displaySubRoundUI() {

		if (!(subRoundList.isEmpty())){
			for (JLabel item: subRoundList){
				estimationGame.getContentPane().remove(item);
			}
			subRoundList.clear();
		}

		JLabel lblSubRound = new JLabel("Subround: " + (roundCounter+1) + " / " + cardsToDealPerRound[round-1]);

        springLayout.putConstraint(SpringLayout.WEST, lblSubRound, 10, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblSubRound.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblSubRound, 70, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblSubRound.setFont(new Font("Tahoma", Font.PLAIN, 15));

        subRoundList.add(lblSubRound);
        estimationGame.getContentPane().add(lblSubRound);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
     * Displays a message informing player of the trump suit
     */
	public void displayTrumpUI() {
		String trumpSuitString = gameLogic.getTrumpCard().getSuit().getName();

		if (!(trumpList.isEmpty())){
			for (JLabel item: trumpList){
				estimationGame.getContentPane().remove(item);
			}
			trumpList.clear();
		}

		JLabel lblTrump = new JLabel("The trump suit is: " + trumpSuitString);

        springLayout.putConstraint(SpringLayout.WEST, lblTrump, 10, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblTrump.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblTrump, 10, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblTrump.setFont(new Font("Tahoma", Font.PLAIN, 15));

        trumpList.add(lblTrump);
        estimationGame.getContentPane().add(lblTrump);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
     * Display a message informing player of the lead suit
     */
	public void displayLead(String leadSuitString) {
		if (!(leadList.isEmpty())) {
			for (JLabel item: leadList) {
				estimationGame.getContentPane().remove(item);
			}
			leadList.clear();
		}

		JLabel lblLead = new JLabel("The lead suit is: " + leadSuitString);
        springLayout.putConstraint(SpringLayout.WEST, lblLead, 10, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblLead.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblLead, 30, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblLead.setFont(new Font("Tahoma", Font.PLAIN, 15));

        leadList.add(lblLead);
        estimationGame.getContentPane().add(lblLead);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
	 * Clears the a message informing player of the lead suit
	 */
	public void clearLeadUI() {
		for (JLabel item : leadList) {
			estimationGame.getContentPane().remove(item);
		}
		leadList.clear();
	}


	/**
     * Sets the UI for notification messages
     */
    private void initNoti() {
        lblNoti = new JLabel("");

        springLayout.putConstraint(SpringLayout.WEST, lblNoti, 335, SpringLayout.WEST, estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, lblNoti, -10, SpringLayout.NORTH, btnAvatarA);
        lblNoti.setForeground(new Color(224, 255, 255));
        lblNoti.setFont(new Font("Tahoma", Font.PLAIN, 15));

        estimationGame.getContentPane().add(lblNoti);
	}


	/**
     * Changes the notification message according to the player turn
     */
	private void drawNoti(String noti) {
        if (noti != null) {
            notification = noti;
        }

        lblNoti.setText(notification);
    }


	/**
     * Sets the Ui for player
     */
	private void initPlayer0() {
        btnAvatarA = new JButton();

        springLayout.putConstraint(SpringLayout.NORTH, btnAvatarA, 550, SpringLayout.NORTH,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnAvatarA, 425, SpringLayout.WEST,
                estimationGame.getContentPane());
        btnAvatarA.setFocusPainted(false);

        try {
            ImageIcon img = new ImageIcon(GameUI.class.getResource("/resource/dog_avatar.jpg"));
            btnAvatarA.setIcon(new ImageIcon(img.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
        } catch (NullPointerException e) {
            System.out.println("Image not found");
        }

        estimationGame.getContentPane().add(btnAvatarA);

		lblNameA = new JLabel("Player 1");

        springLayout.putConstraint(SpringLayout.NORTH, lblNameA, 5, SpringLayout.SOUTH, btnAvatarA);
        springLayout.putConstraint(SpringLayout.WEST, lblNameA, 0, SpringLayout.WEST, btnAvatarA);
        springLayout.putConstraint(SpringLayout.EAST, lblNameA, 0, SpringLayout.EAST, btnAvatarA);
        lblNameA.setHorizontalAlignment(SwingConstants.CENTER);
        lblNameA.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNameA.setForeground(Color.CYAN);
        lblNameA.setFont(new Font("Tahoma", Font.PLAIN, 15));

        estimationGame.getContentPane().add(lblNameA);

        lblScoreA = new JLabel();
        lblScoreA.setText("0");

        lblScoreA.setHorizontalAlignment(SwingConstants.CENTER);
        lblScoreA.setBorder(new LineBorder(Color.ORANGE, 3, true));
        lblScoreA.setForeground(new Color(255, 153, 255));
        lblScoreA.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblScoreA.setBackground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.WEST, lblScoreA, 0, SpringLayout.WEST, btnAvatarA);
        springLayout.putConstraint(SpringLayout.NORTH, lblScoreA, 5, SpringLayout.SOUTH, lblNameA);
        springLayout.putConstraint(SpringLayout.EAST, lblScoreA, 0, SpringLayout.EAST, btnAvatarA);
        
        estimationGame.getContentPane().add(lblScoreA);

        btnCardA = new JButton();

        int cardA_top = 415;
        int cardA_left = 425;

        springLayout.putConstraint(SpringLayout.WEST, btnCardA, cardA_left, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnCardA, cardA_left + cardWidth, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, btnCardA, cardA_top, SpringLayout.NORTH,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnCardA, cardA_top + cardHeight, SpringLayout.NORTH,
                estimationGame.getContentPane());

        btnCardA.setVisible(false);
        estimationGame.getContentPane().add(btnCardA);
	}


	/**
     * Sets the UI for computer
     */
    private void initPlayer1() {
        JButton btnAvatarB = new JButton();

        springLayout.putConstraint(SpringLayout.NORTH, btnAvatarB, 315, SpringLayout.NORTH,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnAvatarB, 200, SpringLayout.WEST,
                estimationGame.getContentPane());
        btnAvatarB.setFocusPainted(false);

        try {
            ImageIcon img = new ImageIcon(GameUI.class.getResource("/resource/bot_avatar.jpg"));
            btnAvatarB.setIcon(new ImageIcon(img.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
        } catch (NullPointerException e) {
            System.out.println("Image not found");

        }

        estimationGame.getContentPane().add(btnAvatarB);

		lblNameB = new JLabel("Robot A");

        springLayout.putConstraint(SpringLayout.EAST, lblNameB, 0, SpringLayout.EAST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.WEST, lblNameB, 0, SpringLayout.WEST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.NORTH, lblNameB, 5, SpringLayout.SOUTH, btnAvatarB);
        lblNameB.setHorizontalAlignment(SwingConstants.CENTER);
        lblNameB.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNameB.setForeground(Color.CYAN);
        lblNameB.setFont(new Font("Tahoma", Font.PLAIN, 15));

        estimationGame.getContentPane().add(lblNameB);

        lblScoreB = new JLabel();

        lblScoreB.setHorizontalAlignment(SwingConstants.CENTER);
        lblScoreB.setBorder(new LineBorder(Color.ORANGE, 3, true));
        lblScoreB.setForeground(new Color(255, 153, 255));
        lblScoreB.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblScoreB.setText("0");
        lblScoreB.setBackground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.WEST, lblScoreB, 0, SpringLayout.WEST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.EAST, lblScoreB, 0, SpringLayout.EAST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.NORTH, lblScoreB, 5, SpringLayout.SOUTH, lblNameB);

        estimationGame.getContentPane().add(lblScoreB);

        btnCardB = new JButton();

        int cardB_top = 0;
        int cardB_left = 50;

        springLayout.putConstraint(SpringLayout.WEST, btnCardB, cardB_left, SpringLayout.EAST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.EAST, btnCardB, cardB_left + cardWidth, SpringLayout.EAST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.NORTH, btnCardB, cardB_top, SpringLayout.NORTH, btnAvatarB);
        springLayout.putConstraint(SpringLayout.SOUTH, btnCardB, cardB_top + cardHeight, SpringLayout.NORTH,
                btnAvatarB);

        btnCardB.setVisible(false);
        estimationGame.getContentPane().add(btnCardB);
    }


	/**
     * Sets the UI for computer
     */
    private void initPlayer2() {
        JButton btnAvatarC = new JButton();

        springLayout.putConstraint(SpringLayout.NORTH, btnAvatarC, 150, SpringLayout.NORTH,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnAvatarC, 400, SpringLayout.WEST,
                estimationGame.getContentPane());
        btnAvatarC.setFocusPainted(false);

        try {
            ImageIcon img = new ImageIcon(GameUI.class.getResource("/resource/bot_avatar.jpg"));
            btnAvatarC.setIcon(new ImageIcon(img.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
        } catch (NullPointerException e) {
            System.out.println("Image not found");

        }

        estimationGame.getContentPane().add(btnAvatarC);

        lblScoreC = new JLabel();
        lblScoreC.setHorizontalAlignment(SwingConstants.CENTER);
        lblScoreC.setBorder(new LineBorder(Color.ORANGE, 3, true));
        lblScoreC.setForeground(new Color(255, 153, 255));
        lblScoreC.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblScoreC.setText("0");
        lblScoreC.setBackground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.WEST, lblScoreC, 10, SpringLayout.EAST, btnAvatarC);
        springLayout.putConstraint(SpringLayout.SOUTH, lblScoreC, 0, SpringLayout.SOUTH, btnAvatarC);
        springLayout.putConstraint(SpringLayout.EAST, lblScoreC, 100, SpringLayout.EAST, btnAvatarC);

        estimationGame.getContentPane().add(lblScoreC);

		lblNameC = new JLabel("Robot B");

        springLayout.putConstraint(SpringLayout.NORTH, lblScoreC, 5, SpringLayout.SOUTH, lblNameC);
        springLayout.putConstraint(SpringLayout.NORTH, lblNameC, 5, SpringLayout.NORTH, btnAvatarC);
        springLayout.putConstraint(SpringLayout.WEST, lblNameC, 0, SpringLayout.WEST, lblScoreC);
        lblNameC.setHorizontalAlignment(SwingConstants.CENTER);
        lblNameC.setAlignmentX(Component.CENTER_ALIGNMENT);
        springLayout.putConstraint(SpringLayout.EAST, lblNameC, 0, SpringLayout.EAST, lblScoreC);
        lblNameC.setForeground(Color.CYAN);
        lblNameC.setFont(new Font("Tahoma", Font.PLAIN, 15));

        estimationGame.getContentPane().add(lblNameC);

        btnCardC = new JButton();

        int cardC_top = 5;
        int cardC_left = 20;

        springLayout.putConstraint(SpringLayout.WEST, btnCardC, cardC_left, SpringLayout.WEST, btnAvatarC);
        springLayout.putConstraint(SpringLayout.EAST, btnCardC, cardC_left + cardWidth, SpringLayout.WEST, btnAvatarC);
        springLayout.putConstraint(SpringLayout.NORTH, btnCardC, cardC_top, SpringLayout.SOUTH, btnAvatarC);
        springLayout.putConstraint(SpringLayout.SOUTH, btnCardC, cardC_top + cardHeight, SpringLayout.SOUTH,
                btnAvatarC);
        btnCardC.setVisible(false);

        estimationGame.getContentPane().add(btnCardC);
    }


	/**
     * Sets the UI for computer
     */
    private void initPlayer3() {
        btnCardD = new JButton();

        int cardD_top = 315;
        int cardD_left = 530;

        springLayout.putConstraint(SpringLayout.NORTH, btnCardD, cardD_top, SpringLayout.NORTH,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.WEST, btnCardD, cardD_left, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnCardD, cardD_left + cardWidth, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnCardD, cardD_top + cardHeight, SpringLayout.NORTH,
                estimationGame.getContentPane());

        btnCardD.setVisible(false);

        estimationGame.getContentPane().add(btnCardD);

        JButton btnAvatarD = new JButton();

        springLayout.putConstraint(SpringLayout.NORTH, btnAvatarD, -5, SpringLayout.NORTH, btnCardD);
        springLayout.putConstraint(SpringLayout.WEST, btnAvatarD, 20, SpringLayout.EAST, btnCardD);
        btnAvatarD.setFocusPainted(false);

        try {
            ImageIcon img = new ImageIcon(GameUI.class.getResource("/resource/bot_avatar.jpg"));
            btnAvatarD.setIcon(new ImageIcon(img.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
        } catch (NullPointerException e) {
            System.out.println("Image not found");
        }

        estimationGame.getContentPane().add(btnAvatarD);

		lblNameD = new JLabel("Robot C");

		springLayout.putConstraint(SpringLayout.NORTH, lblNameD, 5, SpringLayout.SOUTH, btnAvatarD);
        springLayout.putConstraint(SpringLayout.WEST, lblNameD, 0, SpringLayout.WEST, btnAvatarD);
        springLayout.putConstraint(SpringLayout.EAST, lblNameD, 0, SpringLayout.EAST, btnAvatarD);
        lblNameD.setHorizontalAlignment(SwingConstants.CENTER);
        lblNameD.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNameD.setForeground(Color.CYAN);
        lblNameD.setFont(new Font("Tahoma", Font.PLAIN, 15));

        estimationGame.getContentPane().add(lblNameD);

        lblScoreD = new JLabel();

        lblScoreD.setHorizontalAlignment(SwingConstants.CENTER);
        lblScoreD.setBorder(new LineBorder(Color.ORANGE, 3, true));
        lblScoreD.setForeground(new Color(255, 153, 255));
        lblScoreD.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblScoreD.setText("0");
        lblScoreD.setBackground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.NORTH, lblScoreD, 5, SpringLayout.SOUTH, lblNameD);
        springLayout.putConstraint(SpringLayout.WEST, lblScoreD, 0, SpringLayout.WEST, btnAvatarD);
        springLayout.putConstraint(SpringLayout.EAST, lblScoreD, 0, SpringLayout.EAST, btnAvatarD);
        
        estimationGame.getContentPane().add(lblScoreD);
    }
}
