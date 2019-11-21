package estimate.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;
import java.util.*;

import cards.*;
import estimate.gamelogic.*;
import estimate.player.*;
import estimate.scoreboard.*;

/**
 * Main GUI of the game. Contains all the methods to display selected options by player.
 * GameUI is called by the main menu, GUI. The logic is controlled by GameLogic.
 * @author Elias Lim
 * @version 2.0 
 * @since   Nov 21, 2019
 */

public class GameUI {
	private JFrame estimationGame;
	private SpringLayout springLayout;

	private GameLogic gameLogic;
	private Scoreboard scoreboard;
	private int selectedBid;
	private int numberOfSubRounds;
	private String trumpSuitString;
	private int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};

	private int round = 1;
	private int subRound = 0;
	private int roundCounter = 0;
	private int whoNext;
	private Boolean waitingUser;

	private int card_width = 75;
    private int card_height = 108;

	private JButton btnAvatarA;
    private JButton btnCardA;
    private JButton btnCardB;
    private JButton btnCardC;
    private JButton btnCardD;
    private JLabel lblNameA;
    private JLabel lblScoreA;
    private JLabel lblNameB;
    private JLabel lblScoreB;
    private JLabel lblNameC;
    private JLabel lblScoreC;
    private JLabel lblNameD;
    private JLabel lblScoreD;
    private JLabel lblLead;
    private JLabel lblBid;
    private JLabel lblNoti;
	private String notification;

	private ArrayList<JLabel> bidList = new ArrayList<JLabel>();
	private ArrayList<JLabel> leadList = new ArrayList<JLabel>();
	private ArrayList<JLabel> trumpList = new ArrayList<JLabel>();
	private ArrayList<JLabel> roundList = new ArrayList<JLabel>();
	private ArrayList<JLabel> subRoundList = new ArrayList<JLabel>();
	private ArrayList<JButton> listButton = new ArrayList<JButton>();
	private ArrayList<JButton> listButton1 = new ArrayList<JButton>();
	private ArrayList<JButton> listButton2 = new ArrayList<JButton>();
	private ArrayList<JButton> listButton3 = new ArrayList<JButton>();
	private ArrayList<JButton> listButton4 = new ArrayList<JButton>();


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
                    window.initalizeGame();

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
	private void initalizeGame(){
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

        initPlayer0();
        initPlayer1();
        initPlayer2();
        initPlayer3();

        initNoti();
        drawNoti(null);

        gameLogic = new GameLogic();
        gameLogic.getScoreboard();
		gameLogic.getTableHand();
		gameLogic.startNewGame();

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
		
		gameLogic.initialiseDeck();
		gameLogic.setDealer(round);
		
		System.out.println("Players Hand (Should be empty): " + gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(0).getHand());
		
		gameLogic.setPlayersHand(round);
		gameLogic.setTrump();
		gameLogic.setPlayerOrder(round);
		gameLogic.getTableHand().clearTableHand();

		waitingUser = false;
		if (waitingUser()){
			waitingUser = true;
		}

		whoNext = gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(0).getPlayerId();

		displayRoundUI();
		displaySubRoundUI();
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

			if (completeSubRound()) {
				finishSubRound();
				displayTableHandUI();
				displayCardUI();

				roundCounter += 1;

				if (completeRound()) {
					gameLogic.getScoreboard().calculateRoundScore(round);
					gameLogic.getScoreboard().calculateTotalScore();
					updateScoreUI();
            		System.out.println(gameLogic.getScoreboard().getTotalScore());
            		int[] winner = gameLogic.getScoreboard().getWinner(round);

            		if (completeGame(winner)){
            			JOptionPane.showMessageDialog(new JFrame(), "THE GRAND WINNER IS: " + winner[0] + " with " + winner[1] + " POINTS!!!!", "Info",
                                JOptionPane.INFORMATION_MESSAGE);
            			return;
            		} else {
            			newRound();
            		}
            		return;
				}
			}

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

		if (whoNext ==  gameLogic.getArrayOfPlayers().getArrayOfPlayers().get( gameLogic.getArrayOfPlayers().getArrayOfPlayers().size()-1).getPlayerId()){
			subRound += 1;
		}

		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
			if (whoNext == p.getPlayerId()) {
				System.out.println("CURRENT TURN PLAYER HAND : " + gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(whoNext).getHand());
				if (whoNext == 3){
					whoNext = 0;
				} else {
					whoNext += 1;
				}

		        Card highestPlayedCard;
		        Suit leadSuit2;

		        if (gameLogic.getLeadSuit() == null) {
		            leadSuit2 = null;
		        } else {
		            leadSuit2 = gameLogic.getLeadSuit().getSuit();
		        }

		        System.out.println("TESTING 003 " + gameLogic.getTrumpSuit().getSuit() + " " + leadSuit2);
		        System.out.println("TESTING 003.1 " + gameLogic.getTableHand().sortedTableHand( gameLogic.getTrumpSuit().getSuit(), leadSuit2 ).size());
		        if (gameLogic.getTableHand().sortedTableHand( gameLogic.getTrumpSuit().getSuit(), leadSuit2 ).size() == 0 ) {
		            highestPlayedCard = null;
		        } else {
		        	System.out.println("TESTING 004 " + gameLogic.getLeadSuit().getSuit() + " " + gameLogic.getTrumpSuit().getSuit() + " ");
		            highestPlayedCard = gameLogic.getTableHand().sortedTableHand(gameLogic.getTrumpSuit().getSuit(), gameLogic.getLeadSuit().getSuit()).get(0).getPlayerCard();
		        }

		        if (p instanceof Computer) {
	                System.out.println("TESTING 005 " + gameLogic.getTrumpSuit().getSuit() + " " + leadSuit2 + " " + highestPlayedCard);

	                Computer pComputer = (Computer) p;
	                System.out.println("Printing computer: " + pComputer);
	                System.out.println("CHECKING THE FKING HAND" + p.getHand());

	                System.out.println("TESTING 006 " + gameLogic.getTrumpSuit().getSuit() + " " + leadSuit2 + " " + highestPlayedCard);
	                Card cardForCompToPlay = pComputer.playCard(gameLogic.getTrumpSuit().getSuit(), leadSuit2, highestPlayedCard);
	                System.out.println("Computer's Hand" + p.getHand() + "\n");

	                if (p.getPosition() == 0) {
	                    gameLogic.setLeadSuit(cardForCompToPlay);
	                	String leadSuitString = "" + gameLogic.getLeadSuit().getSuit();
	                    displayLead(leadSuitString);
	                }

	                gameLogic.getTableHand().addCard(p, p.removeFromHand(cardForCompToPlay));
	                displayTableHandUI();
	                // Display Table Hand
	                System.out.println(gameLogic.getTableHand().toString());
	                break;

	            } else {
	                //Display Hand to user
	                System.out.println("Player's Hand: " + p.getHand());

	                // Get input from user
	                displayCardUI();
	                System.out.println("Enter Your Card Index You want to play \n" + index);

	                try {
		                System.out.println(index);

		                if (p.getPosition() == 0) {
		                    gameLogic.setLeadSuit(p.getHand().getCard(index));
		                	String leadSuitString = "" + gameLogic.getLeadSuit().getSuit();
		                    displayLead(leadSuitString);
		                }
                		gameLogic.getTableHand().addCard(p,p.removeFromHand(p.getHand().getCard(index)));
		            } catch (IndexOutOfBoundsException error){
		            }

	                displayTableHandUI();
	                // Display Table Hand
	                System.out.println(gameLogic.getTableHand().toString());
            		break;
	            }
	        }
	    }
	}


	/**
     * After each SubRound, this method is called to calculate the scores and get the trick winner
     */
	public void finishSubRound(){
        ArrayList<PlayerCardArray> sortedTableHand = gameLogic.getTableHand().sortedTableHand(gameLogic.getTrumpSuit().getSuit(),
                gameLogic.getLeadSuit().getSuit());

        System.out.println(sortedTableHand);

        PlayerCardArray winner = sortedTableHand.get(0);

        // Display winner of the round
        System.out.println("The winner of this round is player ID: " + winner.getPlayerId());
        gameLogic.getScoreboard().addTricksWon(round, winner.getPlayerId());

        JOptionPane.showMessageDialog(null, "The winner is player ID: " + winner.getPlayerId() + "\n " + (11 - round) + " more rounds to go!");

        //set Winner for player
        for (Player p : gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
            if ( p.getPlayerId() == winner.getPlayerId() ) {
                p.setTrickWinner(true);
            } else {
                p.setTrickWinner(false);
            }
        }

        //Set position for players
        System.out.println("OLD PLAYER ORDER: " + gameLogic.getArrayOfPlayers().getArrayOfPlayers());
        if (round != 11) {
        	System.out.println("SETTING PLAYER ORDER: ");
            gameLogic.setPlayerOrder(round);
            whoNext = gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(0).getPlayerId();
            if (whoNext == 0){
            	waitingUser = true;
            }
            System.out.println("PRINTING WHO NEXT: " + whoNext);

            System.out.println(gameLogic.getArrayOfPlayers().getArrayOfPlayers());
        }

        // Clear tableHand at end of subround
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
		System.out.println("Round: " + round + " SubRound: " + roundCounter + " Total number of SubRounds: " + roundsTotal[round-1]);

		if (roundsTotal[round-1] == roundCounter) {
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

			if (player.getPlayerId() == 1) {
				float cardIndex = -1;
				int cardLeft = 80;
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
			        springLayout.putConstraint(SpringLayout.EAST, btnCard1, (int) (cardLeft + card_height),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.NORTH, btnCard1, (int) (cardTop + cardIndex * card_width), SpringLayout.NORTH,
			            estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.SOUTH, btnCard1, (int) (cardTop + (cardIndex + 1) *  card_width), SpringLayout.NORTH,
			            estimationGame.getContentPane());

			        try {
		                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
		                btnCard1.setIcon(new ImageIcon(
		                        img.getImage().getScaledInstance(card_height, card_width, java.awt.Image.SCALE_SMOOTH)));
		            } catch (NullPointerException e) {
		                System.out.println("Image not found");
		                btnCard1.setText("Not found");
		            }

		            listButton1.add(btnCard1);
			        estimationGame.getContentPane().add(btnCard1);
			    }
			}

			if (player.getPlayerId() == 2) {
				float cardIndex = -1;
				int cardLeft = 300;
				int cardTop = 40;

				for (JButton item: listButton2) {
					estimationGame.getContentPane().remove(item);
				}
				listButton2.clear();

				for (int i = 0; i < playerHandCards.size(); i++) {
		            cardIndex++;

					JButton btnCard2 = new JButton();

					springLayout.putConstraint(SpringLayout.WEST, btnCard2, (int) (cardLeft + cardIndex * card_width),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.EAST, btnCard2, (int) (cardLeft + (cardIndex + 1) * card_width),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.NORTH, btnCard2, cardTop, SpringLayout.NORTH,
			            estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.SOUTH, btnCard2, cardTop + card_height, SpringLayout.NORTH,
			            estimationGame.getContentPane());

			        try {
		                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
		                btnCard2.setIcon(new ImageIcon(
		                        img.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		            } catch (NullPointerException e) {
		                System.out.println("Image not found");
		                btnCard2.setText("Not found");
		            }

		            listButton2.add(btnCard2);
			        estimationGame.getContentPane().add(btnCard2);
		    	}
			}

			if (player.getPlayerId() == 3) {
				float cardIndex = -1;
				int cardLeft = 700;
				int cardTop = 235;

				for (JButton item: listButton3) {
					estimationGame.getContentPane().remove(item);
				}
				listButton3.clear();

				for (int i = 0; i < playerHandCards.size(); i++) {
		            cardIndex++;

					JButton btnCard3 = new JButton();

			        springLayout.putConstraint(SpringLayout.WEST, btnCard3, (int) (cardLeft),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.EAST, btnCard3, (int) (cardLeft + card_height),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.NORTH, btnCard3, (int) (cardTop + cardIndex * card_width), SpringLayout.NORTH,
			            estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.SOUTH, btnCard3, (int) (cardTop + (cardIndex + 1) *  card_width), SpringLayout.NORTH,
			            estimationGame.getContentPane());

			        try {
		                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
		                btnCard3.setIcon(new ImageIcon(
		                        img.getImage().getScaledInstance(card_height, card_width, java.awt.Image.SCALE_SMOOTH)));
		            } catch (NullPointerException e) {
		                System.out.println("Image not found");
		                btnCard3.setText("Not found");
		            }

		            listButton3.add(btnCard3);
			        estimationGame.getContentPane().add(btnCard3);
		    	}
			}

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

				for (int i = 0; i < listCard.size(); i++) {
		            cardIndex++;

		            Card card = listCard.get(i);
			        JButton btnCard = new JButton();
			        btnCard.setFocusPainted(false);
			        btnCard.setName(i + "");

			        springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + cardIndex * card_width),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + (cardIndex + 1) * card_width),
			            SpringLayout.WEST, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
			            estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + card_height, SpringLayout.NORTH,
			            estimationGame.getContentPane());

			        try {
			        	ImageIcon cardImg = card.getCardImage();
			        	btnCard.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
			        } catch (NullPointerException e){
			        	System.out.println("Image not found");
			        	btnCard.setText("Not found");
			        }

			        ArrayList<Card> playableCards = getPlayableCards(player);
			        if (!(playableCards.contains(card))){
			        	btnCard.setEnabled(false);
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
	    //Display playableHand to user
	    if (gameLogic.getLeadSuit() == null) {
	        playableCards = p.getPlayableHand(null, gameLogic.getTrumpSuit().getSuit());
	        System.out.println(playableCards);
	        System.out.println("Player's playable Cards: " + p.getPlayableHand(null, gameLogic.getTrumpSuit().getSuit()));
	    } else {
	        playableCards = p.getPlayableHand(gameLogic.getLeadSuit().getSuit(), gameLogic.getTrumpSuit().getSuit());
	        System.out.println("Player's playable Cards: " + p.getPlayableHand(gameLogic.getLeadSuit().getSuit(),
	                gameLogic.getTrumpSuit().getSuit()));
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

		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
			if (p instanceof Computer) {
				waitingUser = false;
				Computer pComputer = (Computer) p;

				pComputer.bidWinningTricks(numberOfSubRounds, gameLogic.getScoreboard().getTotalBidForRound(round),
                            gameLogic.getTrumpSuit().getSuit());
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

		if (waitingUser()) {
			waitingUser = true;
		} else {
			waitingUser = false;
		}
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
     * Displays a message that informs the player the bid amount he selected
     */
	public void displayBidUI(int bidSelected) {
		if (!(bidList.isEmpty())) {
			for (JLabel item: bidList) {
				estimationGame.getContentPane().remove(item);
			}
			bidList.clear();
		}

		lblBid = new JLabel("You predicted you will win " + bidSelected + " times");

        springLayout.putConstraint(SpringLayout.WEST, lblBid, 165, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblBid.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblBid, 645, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblBid.setFont(new Font("Segoe Script", Font.PLAIN, 15));

        bidList.add(lblBid);
        estimationGame.getContentPane().add(lblBid);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
     * Displays the card UI of the cards played by the player and the computer
     */
	public void displayTableHandUI() {
		ArrayList<PlayerCardArray> tableHandCards = gameLogic.getTableHand().getTableHand();
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
		        	btnCardA.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardA.setText("Not found");
		        }

		        btnCardA.setVisible(true);

			} else if (playerCard.getPlayerId() == 1){
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardB.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardB.setText("Not found");
		        }

		        btnCardB.setVisible(true);
			}

			if (playerCard.getPlayerId() == 2) {
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardC.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardC.setText("Not found");
		        }

		        btnCardC.setVisible(true);
			}

			if (playerCard.getPlayerId() == 3) {
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardD.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
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
		if (whoNext == 0){
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
     * Displays a message informing player of the trump suit
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
        lblRound.setFont(new Font("Segoe Script", Font.PLAIN, 15));

        roundList.add(lblRound);
        estimationGame.getContentPane().add(lblRound);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
     * Displays a message informing player of the trump suit
     */
	public void displaySubRoundUI() {

		if (!(subRoundList.isEmpty())){
			for (JLabel item: subRoundList){
				estimationGame.getContentPane().remove(item);
			}
			subRoundList.clear();
		}

		JLabel lblSubRound = new JLabel("Subround: " + roundCounter);

        springLayout.putConstraint(SpringLayout.WEST, lblSubRound, 10, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblSubRound.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblSubRound, 70, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblSubRound.setFont(new Font("Segoe Script", Font.PLAIN, 15));

        subRoundList.add(lblSubRound);
        estimationGame.getContentPane().add(lblSubRound);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
     * Displays a message informing player of the trump suit
     */
	public void displayTrumpUI() {
		trumpSuitString = gameLogic.getTrumpSuit().getSuit().getName();

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
        lblTrump.setFont(new Font("Segoe Script", Font.PLAIN, 15));

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

		lblLead = new JLabel("The lead suit is: " + leadSuitString);
        springLayout.putConstraint(SpringLayout.WEST, lblLead, 10, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblLead.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblLead, 30, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblLead.setFont(new Font("Segoe Script", Font.PLAIN, 15));

        leadList.add(lblLead);
        estimationGame.getContentPane().add(lblLead);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	/**
     * Sets the UI for notification messages
     */
    private void initNoti() {
        lblNoti = new JLabel("");

        springLayout.putConstraint(SpringLayout.WEST, lblNoti, 300, SpringLayout.WEST, estimationGame.getContentPane());
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

        lblNameA = new JLabel("You");

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

        int cardA_top = 435;
        int cardA_left = 425;

        springLayout.putConstraint(SpringLayout.WEST, btnCardA, cardA_left, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnCardA, cardA_left + card_width, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.NORTH, btnCardA, cardA_top, SpringLayout.NORTH,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnCardA, cardA_top + card_height, SpringLayout.NORTH,
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

        lblNameB = new JLabel("Robot B");

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

        int cardB_top = 25;
        int cardB_left = 50;

        springLayout.putConstraint(SpringLayout.WEST, btnCardB, cardB_left, SpringLayout.EAST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.EAST, btnCardB, cardB_left + card_width, SpringLayout.EAST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.NORTH, btnCardB, cardB_top, SpringLayout.NORTH, btnAvatarB);
        springLayout.putConstraint(SpringLayout.SOUTH, btnCardB, cardB_top + card_height, SpringLayout.NORTH,
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

        lblNameC = new JLabel("Robot C");

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
        springLayout.putConstraint(SpringLayout.EAST, btnCardC, cardC_left + card_width, SpringLayout.WEST, btnAvatarC);
        springLayout.putConstraint(SpringLayout.NORTH, btnCardC, cardC_top, SpringLayout.SOUTH, btnAvatarC);
        springLayout.putConstraint(SpringLayout.SOUTH, btnCardC, cardC_top + card_height, SpringLayout.SOUTH,
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
        springLayout.putConstraint(SpringLayout.EAST, btnCardD, cardD_left + card_width, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, btnCardD, cardD_top + card_height, SpringLayout.NORTH,
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

        lblNameD = new JLabel("Robot D");

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