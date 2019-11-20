package estimate.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.LineBorder;
import java.util.ArrayList;
import java.util.List;

import cards.*;
import estimate.gamelogic.*;
import estimate.player.*;
import estimate.scoreboard.*;

public class GameUI {
	private GameLogic gameLogic;
	private Scoreboard scoreboard;
	private String trumpSuitString;
	private String leadSuitString;
	private int subRound = 0;
	private int round = 1;
	private int roundCounter = 0;
	private int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};
	private int whoNext;
	private Boolean waitingUser;
	private Card selectedCard;

	private int card_width = 75;
    private int card_height = 108;

    private List<JButton> listButton = new ArrayList<JButton>();
    private List<JButton> tableHandCardList = new ArrayList<JButton>();

    private SpringLayout springLayout;
	private JFrame estimationGame;

	private int selectedBid;

	private String notification;
	private JButton btnAvatarA;
    private JLabel lblNameA;
    private JLabel lblTrickA;
    private JButton btnCardA;
    private JLabel lblNameB;
    private JLabel lblTrickB;
    private JButton btnCardB;
    private JButton btnCardC;
    private JLabel lblNameC;
    private JLabel lblTrickC;
    private JLabel lblNameD;
    private JLabel lblTrickD;
    private JButton btnCardD;
    private JLabel lblNoti;


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

        notification = "Click icon below to start game";

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
        // ArrayList<Player> playerArray = players.getArrayOfPlayers();

        btnAvatarA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notification = "";
                newGame();
            }
        });

	}


	private void newGame(){
		// players = gameLogic.startNewGame();
		// playerArray = gameLogic.getArrayOfPlayers().getArrayOfPlayers();

		// int round = 1;
		// int numberOfSubRounds = cardsToDealPerRound[round - 1];

		gameLogic.setDealer(round);
		gameLogic.setPlayersHand(round);
		gameLogic.setTrump();
		gameLogic.setPlayerOrder(round);

		waitingUser = false;
		if (waitingUser()){
			waitingUser = true;
		}

		whoNext = gameLogic.getArrayOfPlayers().getArrayOfPlayers().get(0).getPlayerId();

		displayTrump();
		displayCards();
		displayAvailableBids();
		displayTableHand();


		todoThread();
	}


	public Boolean completeSubRound(){
		// ArrayList<PlayerCardArray> tableHandCards = gameLogic.getTableHand().getTableHand();
		if (subRound != 1){
			return false;
		} else {
			System.out.println("######SUBROUND COMPLETED#######");
			subRound = 0;
			return true;
		}
	}

	public Boolean completeRound(int roundCounter){
		int[] roundsTotal = {1,2,3,4,5,6,5,4,3,2,1};

		if (roundsTotal[round] == roundCounter){
			round += 1;
			roundCounter = 0;
			System.out.println("######ROUND COMPLETED######");
			return true;
		}else{
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
			int numberOfSubRounds = cardsToDealPerRound[round - 1];
			if (completeSubRound()){
				// finishSubRound();
				roundCounter += 1;
				System.out.println();
				System.out.println("ROUNDCOUNTER IS " + roundCounter);
				System.out.println("ROUND IS " + round);
				System.out.println();
				displayCards();
				displayTableHand();
				System.out.println("test");
				if (completeRound(roundCounter)){

					gameLogic.getScoreboard().calculateRoundScore(round);
					gameLogic.getScoreboard().calculateTotalScore();
            		System.out.println(gameLogic.getScoreboard().getTotalScore());
            		int[] winner = gameLogic.getScoreboard().getWinner(round);

            		if (completeGame(winner)){
            			JOptionPane.showMessageDialog(new JFrame(), "CONGRATZZZ THE WINNER IS: " + winner[0] + " with " + winner[1] + " points!!!!", "Info",
                                JOptionPane.INFORMATION_MESSAGE);
            		} else {
            			newGame();
            		}

				}
			}

			if (waitingUser){
				drawNoti("Your turn");
				System.out.println("~~~PLAYER'S TURN~~~");
				break;
			} else {
				drawNoti("...");
				System.out.println("~~~COMPUTER'S TURN~~~");
				displayCards();
				playSubRound();
				displayTableHand();

				if (waitingUser()){
					displayAvailableBids();
					displayTableHand();
					displayCards();
					waitingUser = true;
				}
			}
		}
	}


	public void playSubRound(){

		gameLogic.getTableHand().clearTableHand();
		System.out.println();
		System.out.println("PRINTING ARRAY OF PLAYERS");
		System.out.println(gameLogic.getArrayOfPlayers().getArrayOfPlayers());


		if (whoNext ==  gameLogic.getArrayOfPlayers().getArrayOfPlayers().get( gameLogic.getArrayOfPlayers().getArrayOfPlayers().size()-1).getPlayerId()){
			System.out.println("PRINTING LAST FKER " + whoNext);
			subRound += 1;
		}

		for (Player p: gameLogic.getArrayOfPlayers().getArrayOfPlayers()){
			// System.out.println();
			// System.out.println("PRINTING WHO NEXT " + whoNext);
			// System.out.println("PRINTING PLAYER ID " + p.getPlayerId());
			if (whoNext == p.getPlayerId()){
				if (whoNext == 3){
					whoNext = 0;
				} else {
					whoNext += 1;
				}

		        Card highestPlayedCard;
		//           Card leadSuit;
		        Suit leadSuit2;

		        if (gameLogic.getLeadSuit() == null) {
		            leadSuit2 = null;
		        } else {
		            leadSuit2 = gameLogic.getLeadSuit().getSuit();
		        }

		        // System.out.println("TESTING2 " + gameLogic.getTrumpSuit().getSuit() + " " + leadSuit2);
		        // System.out.println("TESTING3 " + gameLogic.getTableHand().sortedTableHand( gameLogic.getTrumpSuit().getSuit(), leadSuit2 ).size());
		        if (gameLogic.getTableHand().sortedTableHand( gameLogic.getTrumpSuit().getSuit(), leadSuit2 ).size() == 0 ) {
		            highestPlayedCard = null;
		//                leadSuit = null;
		        } else {
		//                leadSuit = this.leadSuit;
		            highestPlayedCard = gameLogic.getTableHand().sortedTableHand(gameLogic.getTrumpSuit().getSuit(), gameLogic.getLeadSuit().getSuit()).get(0).getPlayerCard();
		        }
		        System.out.println("\n Player ID: " + p.getPlayerId() );
		        System.out.println("PLAYER POSITION: " + p.getPosition());

		        if (p instanceof Computer) {
	                System.out.println("Entered Computer");
	                
	                // System.out.println("TESTING " + gameLogic.getTrumpSuit().getSuit() + " " + leadSuit2 + " " + highestPlayedCard);

	                Computer pComputer = (Computer) p;
	                System.out.println(pComputer);

	                // System.out.println("TESTING4 " + pComputer.playCard(gameLogic.getTrumpSuit().getSuit(), leadSuit2, highestPlayedCard));
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
	                displayCards();
	                System.out.println("Enter Your Card Index You want to play \n");

	                System.out.println("PRINTING CARD");
	                System.out.println(selectedCard);

	                int cardIndex = p.getHand().findCard(selectedCard);
	                System.out.println(cardIndex);

	                if (p.getPosition() == 0) {
	                    gameLogic.setLeadSuit(p.getHand().getCard(cardIndex));
	                	String leadSuitString = "" + gameLogic.getLeadSuit().getSuit();
	                    displayLead(leadSuitString);
	                }
	                // System.out.println("Lead SUit: " + this.leadSuit.getSuit());
	                gameLogic.getTableHand().addCard(p,p.removeFromHand(p.getHand().getCard(cardIndex)));
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
        System.out.println("The winner is player ID: "+winner.getPlayerId());
        gameLogic.getScoreboard().addTricksWon(round, winner.getPlayerId());

        //set Winner for player
        for (Player p : gameLogic.getArrayOfPlayers().getArrayOfPlayers()) {
            if ( p.getPlayerId() == winner.getPlayerId() ) {
                p.setTrickWinner(true);
            } else {
                p.setTrickWinner(false);
            }
        }

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


	public void displayAvailableBids(){
		System.out.println("BIDDING TIME");
        ArrayList<Integer> availableBids = new ArrayList<Integer>();
        int numberOfSubRounds = cardsToDealPerRound[round - 1];

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
    	int bidSelected = JOptionPane.showOptionDialog(null,
                        "Select from available bids", "input", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, availableBidsString, availableBidsString[0]);
		
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

		JLabel lblTrump = new JLabel("The trump suit is: " + trumpSuitString);
        springLayout.putConstraint(SpringLayout.WEST, lblTrump, 10, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblTrump.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblTrump, 10, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblTrump.setFont(new Font("Segoe Script", Font.PLAIN, 15));

        estimationGame.getContentPane().add(lblTrump);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	public void displayLead(String leadSuitString){

		JLabel lblLead = new JLabel("The lead suit is: " + leadSuitString);
        springLayout.putConstraint(SpringLayout.WEST, lblLead, 10, SpringLayout.WEST,
                estimationGame.getContentPane());
        lblLead.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblLead, 30, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblLead.setFont(new Font("Segoe Script", Font.PLAIN, 15));

        estimationGame.getContentPane().add(lblLead);
        estimationGame.validate();
	    estimationGame.repaint();
	}


	public void clearTableHand(List<JButton> tableHandCardList){
		for (JButton item: tableHandCardList){
			estimationGame.getContentPane().remove(item);
		}
		tableHandCardList.clear();
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
				int playerID = player.getPlayerId();
				displayComputerCards(playerID, playerHandCards.size());
			}else{
				displayPlayerCards(playerHandCards, player);
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

	        listButton.add(btnCard);
	        estimationGame.getContentPane().add(btnCard);

	        btnCard.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e){
	        		if (waitingUser){
	        			System.out.println("CLICKING BUTTON");
	        			System.out.println(card);
	        			selectedCard = card;
	        			// passSelectedCard(selectedCard);
	        			playSubRound();
	        			btnCard.setVisible(false);

	        		}
	        	}
	        });      
	    }
	}

	// public Card getSelectedCard(){
	// 	 btnCard.addActionListener(new ActionListener() {
	//         	@Override
	//         	public void actionPerformed(ActionEvent e){
	//         		if (waitingUser){
	//         			System.out.println("CLICKING BUTTON");
	//         			System.out.println(card);
	//         			selectedCard = card;
	//         			// passSelectedCard(selectedCard);
	//         			playSubRound(selectedCard);
	//         			btnCard.setVisible(false);

	//         		}
	//         	}
	//         });
	// }


	public void passSelectedCard(Card selectedCard){
		waitingUser = false;
		playSubRound();
		todoThread();
	}


	public void displayComputerCards(int playerID, int numCards){
		if (playerID == 1){
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

	            listButton.add(btnCard);
		        estimationGame.getContentPane().add(btnCard);
		    }
		}
		
		if (playerID == 2){
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

	            listButton.add(btnCard);
		        estimationGame.getContentPane().add(btnCard);
	    	}
		}

		if (playerID == 3){
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

	            listButton.add(btnCard);
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

        lblTrickA = new JLabel();
        lblTrickA.setText("0");
        lblTrickA.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrickA.setBorder(new LineBorder(Color.ORANGE, 3, true));
        lblTrickA.setForeground(new Color(255, 153, 255));
        lblTrickA.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblTrickA.setBackground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.WEST, lblTrickA, 0, SpringLayout.WEST, btnAvatarA);
        springLayout.putConstraint(SpringLayout.NORTH, lblTrickA, 5, SpringLayout.SOUTH, lblNameA);
        springLayout.putConstraint(SpringLayout.EAST, lblTrickA, 0, SpringLayout.EAST, btnAvatarA);
        estimationGame.getContentPane().add(lblTrickA);

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

        lblTrickB = new JLabel();
        lblTrickB.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrickB.setBorder(new LineBorder(Color.ORANGE, 3, true));
        lblTrickB.setForeground(new Color(255, 153, 255));
        lblTrickB.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblTrickB.setText("0");
        lblTrickB.setBackground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.WEST, lblTrickB, 0, SpringLayout.WEST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.EAST, lblTrickB, 0, SpringLayout.EAST, btnAvatarB);
        springLayout.putConstraint(SpringLayout.NORTH, lblTrickB, 5, SpringLayout.SOUTH, lblNameB);
        estimationGame.getContentPane().add(lblTrickB);

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
        lblTrickC = new JLabel();
        lblTrickC.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrickC.setBorder(new LineBorder(Color.ORANGE, 3, true));
        lblTrickC.setForeground(new Color(255, 153, 255));
        lblTrickC.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblTrickC.setText("0");
        lblTrickC.setBackground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.WEST, lblTrickC, 10, SpringLayout.EAST, btnAvatarC);
        springLayout.putConstraint(SpringLayout.SOUTH, lblTrickC, 0, SpringLayout.SOUTH, btnAvatarC);
        springLayout.putConstraint(SpringLayout.EAST, lblTrickC, 100, SpringLayout.EAST, btnAvatarC);
        estimationGame.getContentPane().add(lblTrickC);

        lblNameC = new JLabel("Robot C");
        springLayout.putConstraint(SpringLayout.NORTH, lblTrickC, 5, SpringLayout.SOUTH, lblNameC);
        springLayout.putConstraint(SpringLayout.NORTH, lblNameC, 5, SpringLayout.NORTH, btnAvatarC);
        springLayout.putConstraint(SpringLayout.WEST, lblNameC, 0, SpringLayout.WEST, lblTrickC);
        lblNameC.setHorizontalAlignment(SwingConstants.CENTER);
        lblNameC.setAlignmentX(Component.CENTER_ALIGNMENT);
        springLayout.putConstraint(SpringLayout.EAST, lblNameC, 0, SpringLayout.EAST, lblTrickC);
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

        lblTrickD = new JLabel();
        lblTrickD.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrickD.setBorder(new LineBorder(Color.ORANGE, 3, true));
        lblTrickD.setForeground(new Color(255, 153, 255));
        lblTrickD.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblTrickD.setText("0");
        lblTrickD.setBackground(Color.GRAY);
        springLayout.putConstraint(SpringLayout.NORTH, lblTrickD, 5, SpringLayout.SOUTH, lblNameD);
        springLayout.putConstraint(SpringLayout.WEST, lblTrickD, 0, SpringLayout.WEST, btnAvatarD);
        springLayout.putConstraint(SpringLayout.EAST, lblTrickD, 0, SpringLayout.EAST, btnAvatarD);
        estimationGame.getContentPane().add(lblTrickD);
    }

    private void initNoti() {
        lblNoti = new JLabel("");
        springLayout.putConstraint(SpringLayout.WEST, lblNoti, 370, SpringLayout.WEST, estimationGame.getContentPane());
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