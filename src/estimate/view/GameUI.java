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

public class GameUI {
	private JFrame estimationGame;
	private SpringLayout springLayout;

	private int whoNext;
	private int round = 1;
	private int selectedBid;
	private int subRound = 0;
	private GameLogic gameLogic;
	private Boolean waitingUser;
	private int roundCounter = 0;
	private int numberOfSubRounds;
	private Scoreboard scoreboard;
	private String leadSuitString;
	private String trumpSuitString;
	private int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};

	private ArrayList<JLabel> bidList = new ArrayList<JLabel>();
	private ArrayList<JLabel> leadList = new ArrayList<JLabel>();
	private ArrayList<JLabel> trumpList = new ArrayList<JLabel>();
	private ArrayList<JButton> listButton = new ArrayList<JButton>();
	private ArrayList<JButton> listButton1 = new ArrayList<JButton>();
	private ArrayList<JButton> listButton2 = new ArrayList<JButton>();
	private ArrayList<JButton> listButton3 = new ArrayList<JButton>();
	private ArrayList<JButton> listButton4 = new ArrayList<JButton>();

	private int card_width = 75;
    private int card_height = 108;


	private JButton btnAvatarA;
    private JLabel lblNameA;
    private JLabel lblScoreA;
    private JButton btnCardA;
    private JLabel lblNameB;
    private JLabel lblScoreB;
    private JButton btnCardB;
    private JButton btnCardC;
    private JLabel lblNameC;
    private JLabel lblScoreC;
    private JLabel lblNameD;
    private JLabel lblScoreD;
    private JButton btnCardD;
    private JLabel lblNoti;
    private JLabel lblLead;
    private JLabel lblBid;
	private String notification;


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
		// initalizeGame();
	}


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

		initA();
        initB();
        initC();
        initD();

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


	private void newRound(){

		System.out.println("PREPPING ROUND " + round + " SETUP. Game Logic round (should be aligned) : " + gameLogic.getRound());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~");
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

		displayTrump();
		playPlayerCards();
		displayAvailableBids();
		displayTableHand();

		System.out.println("BEGIN ROUND " + round);
		todoThread();
	}


	public Boolean completeSubRound(){
		// ArrayList<PlayerCardArray> tableHandCards = gameLogic.getTableHand().getTableHand();
		if (subRound != 1){
			return false;
		} else {
			System.out.println("###########################SUBROUND COMPLETED##############################");
			subRound = 0;
			return true;
		}
	}

	public Boolean completeRound(){
		int[] roundsTotal = {1,2,3,4,5,6,5,4,3,2,1};
		System.out.println("*********ROUND : "+ round);
		System.out.println("*********ROUNDCOUNTER : "+ roundCounter);
		System.out.println("*********NUM SUBROUNDS : "+ roundsTotal[round-1]);

		if (roundsTotal[round-1] == roundCounter){
			roundCounter = 0;
			round += 1;
			gameLogic.setRound(round);
			System.out.println("#################################ROUND COMPLETED############################");
			return true;
		}else{
			// roundCounter += 1;
			return false;
		}
	}


	public Boolean completeGame(int[] winner){
		if (winner != null){
			System.out.println("CONGRATZZZ THE WINNER IS: " + winner[0] + " with " + winner[1] + " points!!!!");
			return true;
		}
		return false;
	}


	public void todoThread(){
		while (true){
			numberOfSubRounds = cardsToDealPerRound[round - 1];

			if (completeSubRound()){
				finishSubRound();
				roundCounter += 1;

				displayTableHand();
				playPlayerCards();

				if (completeRound()){

					gameLogic.getScoreboard().calculateRoundScore(round);
					gameLogic.getScoreboard().calculateTotalScore();
					updateScoreUI();
            		System.out.println(gameLogic.getScoreboard().getTotalScore());
            		int[] winner = gameLogic.getScoreboard().getWinner(round);

            		if (completeGame(winner)){
            			JOptionPane.showMessageDialog(new JFrame(), "CONGRATZZZ THE WINNER IS: " + winner[0] + " with " + winner[1] + " points!!!!", "Info",
                                JOptionPane.INFORMATION_MESSAGE);
            			return;
            		} else {
            			newRound();
            		}
            		return;
				}

			}

			if (waitingUser){
				drawNoti("Your turn");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~PLAYER'S TURN~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				break;
			} else {
				drawNoti("...");
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~COMPUTER'S TURN~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				playSubRound(-1);
				displayTableHand();

				if (waitingUser()){
					displayTableHand();
					waitingUser = true;
				}
			}
		}
	}


	public void playSubRound(int index){
		System.out.println();
		System.out.println("PRINTING ARRAY OF PLAYERS:");
		System.out.println(gameLogic.getArrayOfPlayers().getArrayOfPlayers());

		if (whoNext ==  gameLogic.getArrayOfPlayers().getArrayOfPlayers().get( gameLogic.getArrayOfPlayers().getArrayOfPlayers().size()-1).getPlayerId()){
			subRound += 1;
		}

		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()){
			if (whoNext == p.getPlayerId()){
				System.out.println("CURRENT TURN PLAYER HAND : " + gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(whoNext).getHand());
				if (whoNext == 3){
					whoNext = 0;
				} else {
					whoNext += 1;
				}


		        Card highestPlayedCard;
		        Suit leadSuit2;
		        System.out.println("TESTING 001 " + gameLogic.getLeadSuit());
		        if (gameLogic.getLeadSuit() == null) {
		            leadSuit2 = null;
		        } else {
		        	System.out.println("TESTING 002 " + gameLogic.getLeadSuit().getSuit());
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
		        System.out.println("\n Player ID: " + p.getPlayerId() );
		        System.out.println("PLAYER POSITION: " + p.getPosition());

		        if (p instanceof Computer) {
	                System.out.println("Entered Computer");
	                
	                System.out.println("TESTING 005 " + gameLogic.getTrumpSuit().getSuit() + " " + leadSuit2 + " " + highestPlayedCard);

	                Computer pComputer = (Computer) p;
	                System.out.println(pComputer);

	                System.out.println("CHECKING THE FKING HAND" + p.getHand());
	                System.out.println("TESTTT");

	                System.out.println("TESTING 006 " + gameLogic.getTrumpSuit().getSuit() + " " + leadSuit2 + " " + highestPlayedCard);
	                Card cardForCompToPlay = pComputer.playCard(gameLogic.getTrumpSuit().getSuit(), leadSuit2, highestPlayedCard);
	                System.out.println("Computer's Hand" + p.getHand() + "\n");


	                if (p.getPosition() == 0) {
	                    gameLogic.setLeadSuit(cardForCompToPlay);

	                	String leadSuitString = "" + gameLogic.getLeadSuit().getSuit();
	                    displayLead(leadSuitString);

	                }

	                // System.out.println("Lead SUit: " + this.leadSuit.getSuit());
	                gameLogic.getTableHand().addCard(p, p.removeFromHand(cardForCompToPlay));
	                displayTableHand();
	                // Display Table Hand
	                System.out.println(gameLogic.getTableHand().toString());
	                break;

	            } else {
	                System.out.println("Entered Player");
	                //Display Hand to user
	                System.out.println("Player's Hand: " + p.getHand());

	                // Get input from user

	                playPlayerCards();
	                System.out.println("Enter Your Card Index You want to play \n" + index);

	                // int cardIndex = p.getHand().findCard(selectedCard);
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
	                // System.out.println("Lead SUit: " + this.leadSuit.getSuit());
	                displayTableHand();
	                // Display Table Hand
	                System.out.println(gameLogic.getTableHand().toString());
            		break;
	            }
	        }
	    }
	    System.out.println("Lead SUit : " + gameLogic.getLeadSuit().getSuit());
	}


	public void finishSubRound(){
        ArrayList<PlayerCardArray> sortedTableHand = gameLogic.getTableHand().sortedTableHand(gameLogic.getTrumpSuit().getSuit(),
                gameLogic.getLeadSuit().getSuit());

        System.out.println(sortedTableHand);

        PlayerCardArray winner = sortedTableHand.get(0);

        // Display winner of the round
        System.out.println("The winner of this round is player ID: " + winner.getPlayerId() + " " + (11 - round) + " more rounds to go!");
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

        System.out.println("SCORE : " + gameLogic.getScoreboard().getTotalScore());

        //Set position for players
        if (round != 11) {
            gameLogic.setPlayerOrder(round);
        }

        // Clear tableHand at end of subround
        gameLogic.getTableHand().clearTableHand();
	}


	public ArrayList<Card> getPlayableCards(Player p){
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


	public Boolean waitingUser(){
		if (whoNext == 0){
			return true;
		} else {
			return false;
		}
	}


	// public void displayScoreboard(){
	// 	JButton scoreBtn = new JButton("Scoreboard");  
	// 	scoreBtn.setBounds(10,50,95,30);  

	// 	estimationGame.getContentPane().add(scoreBtn);
 //        estimationGame.validate();
	//     estimationGame.repaint();

	// 	scoreBtn.addActionListener(new ActionListener() {
 //        	@Override
 //        	public void actionPerformed(ActionEvent e){
        		
	// 		}
	// 	});

	// }


	public void updateScoreUI(){
		Map<Integer, Integer> scoreMap = gameLogic.getScoreboard().getTotalScore();

		lblScoreA.setText(String.valueOf(scoreMap.get(0)));
        lblScoreB.setText(String.valueOf(scoreMap.get(1)));
        lblScoreC.setText(String.valueOf(scoreMap.get(2)));
        lblScoreD.setText(String.valueOf(scoreMap.get(3)));
	}


	public void displayAvailableBids(){
		System.out.println("BIDDING TIME");
        ArrayList<Integer> availableBids = new ArrayList<Integer>();
        numberOfSubRounds = cardsToDealPerRound[round - 1];

		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()){
			if (p instanceof Computer){
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
		for (int i = 0; i < availableBids.size(); i++){
			availableBidsString[i] = Integer.toString(availableBids.get(i));
		}

		// waitingUser = true;
		if (waitingUser()){
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


	public void displayTrump(){
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


	public void displayBidUI(int bidSelected){
		System.out.println("DISASLAOJSFASFBAKFB " + bidSelected);
		if (!(bidList.isEmpty())){
			for (JLabel item: bidList){
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


	public void displayLead(String leadSuitString){

		if (!(leadList.isEmpty())){
			for (JLabel item: leadList){
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


	public void displayTableHand(){
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
		        estimationGame.validate();
				estimationGame.repaint();
		        // tableHandCardList.add(btnCard);

			} else if (playerCard.getPlayerId() == 1){
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardB.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardB.setText("Not found");
		        }
		        btnCardB.setVisible(true);
		        estimationGame.validate();
				estimationGame.repaint();
		        // tableHandCardList.add(btnCard);
			}

			if (playerCard.getPlayerId() == 2){
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardC.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardC.setText("Not found");
		        }
		        btnCardC.setVisible(true);
		        estimationGame.validate();
				estimationGame.repaint();
		        // tableHandCardList.add(btnCard);
			}

			if (playerCard.getPlayerId() == 3){
		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCardD.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCardD.setText("Not found");
		        }
		        btnCardD.setVisible(true);
		        estimationGame.validate();
				estimationGame.repaint();
		        // tableHandCardList.add(btnCard);
			}
		}
	}


	public void displayCards(){
		for (Player player: gameLogic.getArrayOfPlayers().getArrayOfPlayers()){
			PlayerHand playerHand = player.getHand();
			ArrayList<Card> playerHandCards = playerHand.getHand();
			if (player instanceof Computer){
				// int playerID = player.getPlayerId();
				displayComputerCards(player, playerHandCards.size());
			}else{
				displayPlayerCards(playerHandCards, player);
			}
		}
		estimationGame.validate();
	    estimationGame.repaint();
	}


	public void playPlayerCards(){
		for (Player player: gameLogic.getArrayOfPlayers().getArrayOfPlayers()){
			PlayerHand playerHand = player.getHand();
			ArrayList<Card> playerHandCards = playerHand.getHand();

			if (player.getPlayerId() == 1){
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

			if (player.getPlayerId() == 2){
				float cardIndex = -1;
				int cardLeft = 300;
				int cardTop = 40;

				for (JButton item: listButton2){
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

			if (player.getPlayerId() == 3){
				float cardIndex = -1;
				int cardLeft = 700;
				int cardTop = 235;

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

			if (!(player instanceof Computer)){
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
			        			// Card selectedCard = listCard.get(index);
			        			passSelectedCard(index);
			        		}
		        			// selectedCard = card;
		        			// passSelectedCard();
		        			// playSubRound();
		        			// btnCard.setVisible(false);
		        			// estimationGame.getContentPane().remove(btnCard);
		        			// estimationGame.getContentPane().repaint();
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


	public void displayPlayerCards(ArrayList<Card> playerHandCards, Player p){

		int cardLeft = 300;
		int cardTop = 645;

		float cardIndex = -1;

		for (int i = 0; i < playerHandCards.size(); i++) {
            cardIndex++;

            Card card = playerHandCards.get(i);
	        JButton btnCard = new JButton();

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

	        ArrayList<Card> playableCards = getPlayableCards(p);
	        if (!(playableCards.contains(card))){
	        	btnCard.setEnabled(false);
	        }

	        estimationGame.getContentPane().add(btnCard);
	    }
	}


	public void passSelectedCard(int index){
		System.out.println("PLAYER SELECTED CARD INDEX " + index);
		waitingUser = false;
		playSubRound(index);

		todoThread();
	}


	public void displayComputerCards(Player player, int numCards){
		// waitingUser = false;
		// Computer pComputer = (Computer) player;
		if (player.getPlayerId() == 1){
			float cardIndex = -1;
			int cardLeft = 120;
			int cardTop = 400;

			for (int i = 0; i < numCards; i++) {
	            cardIndex++;
				JButton btnCard = new JButton();

				springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + cardIndex * card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + (cardIndex + 1) * card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
		            estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + card_height, SpringLayout.NORTH,
		            estimationGame.getContentPane());

		        try {
	                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
	                btnCard.setIcon(new ImageIcon(
	                        img.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
	            } catch (NullPointerException e) {
	                System.out.println("Image not found");
	                btnCard.setText("Not found");
	            }

		        estimationGame.getContentPane().add(btnCard);
		    }
		}
		
		if (player.getPlayerId() == 2){
			float cardIndex = -1;
			int cardLeft = 300;
			int cardTop = 80;

			for (int i = 0; i < numCards; i++) {
	            cardIndex++;
				JButton btnCard = new JButton();

				springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + cardIndex * card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + (cardIndex + 1) * card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
		            estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + card_height, SpringLayout.NORTH,
		            estimationGame.getContentPane());

		        try {
	                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
	                btnCard.setIcon(new ImageIcon(
	                        img.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
	            } catch (NullPointerException e) {
	                System.out.println("Image not found");
	                btnCard.setText("Not found");
	            }

		        estimationGame.getContentPane().add(btnCard);
	    	}
		}

		if (player.getPlayerId() == 3){
			float cardIndex = -1;
			int cardLeft = 700;
			int cardTop = 400;

			for (int i = 0; i < numCards; i++) {
	            cardIndex++;
				JButton btnCard = new JButton();

				springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + cardIndex * card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + (cardIndex + 1) * card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
		            estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + card_height, SpringLayout.NORTH,
		            estimationGame.getContentPane());

		        try {
	                ImageIcon img = new ImageIcon(this.getClass().getResource("/resource/cards/b.gif"));
	                btnCard.setIcon(new ImageIcon(
	                        img.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
	            } catch (NullPointerException e) {
	                System.out.println("Image not found");
	                btnCard.setText("Not found");
	            }

		        estimationGame.getContentPane().add(btnCard);
	    	}
		}
	}


	private void initA(){
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


    private void initB() {
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


    private void initC() {
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

    private void initD() {
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

    private void initNoti() {
        lblNoti = new JLabel("");
        springLayout.putConstraint(SpringLayout.WEST, lblNoti, 300, SpringLayout.WEST, estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.SOUTH, lblNoti, -10, SpringLayout.NORTH, btnAvatarA);
        lblNoti.setForeground(new Color(224, 255, 255));
        lblNoti.setFont(new Font("Tahoma", Font.PLAIN, 15));
        estimationGame.getContentPane().add(lblNoti);
	}

	private void drawNoti(String noti) {
        if (noti != null) {
            notification = noti;
        }
        lblNoti.setText(notification);
    }
}