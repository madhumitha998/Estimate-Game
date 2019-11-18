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
	private ArrayOfPlayers players;
	private Card trumpSuit;
	private int round = 1;
	private int[] cardsToDealPerRound = {1,2,3,4,5,6,5,4,3,2,1};

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

        // gameLogic = new GameLogic();
        // players = gameLogic.startNewGame();
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
		gameLogic = new GameLogic();
		players = gameLogic.startNewGame();

		// for (int round = 1; round <= 11; round++){
			// this.round = round;
		int round = 1;
		int numberOfSubRounds = cardsToDealPerRound[round - 1];

		gameLogic.setDealer(round);
		gameLogic.setPlayersHand(round);
		gameLogic.setTrump();
		gameLogic.setPlayerOrder(round);

		ArrayList<Player> playerArray = gameLogic.getArrayOfPlayers().getArrayOfPlayers();

		displayCards(playerArray);
		// }
	}


	public void displayAvailableBids(ArrayList<Player> playerArray, int numberOfSubRounds){
		for (Player p: playerArray){
			if (p instanceof Computer){
				Computer pComputer = (Computer) p;
				pComputer.bidWinningTricks(numberOfSubRounds, scoreboard.getTotalBidForRound(round),
                            this.trumpSuit.getSuit());
				int predictedBid = p.getBid();
				scoreboard.addPrediction(round,p.getPlayerId(),predictedBid);
			}else{
				// User needs to set the predicted Bids
                int totalBidsSoFar = scoreboard.getTotalBidForRound(round);
                ArrayList<Integer> availableBids = p.getAvailableBids(numberOfSubRounds, totalBidsSoFar);
			
				int buttonIndex = 1;
				for (int bid: availableBids){
					JButton btnBid = new JButton("" + bid);
			        btnBid.setFocusable(false);
			        springLayout.putConstraint(SpringLayout.NORTH, btnBid, 30, SpringLayout.SOUTH, estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.WEST, btnBid, 80, SpringLayout.WEST,
			                estimationGame.getContentPane());
			        springLayout.putConstraint(SpringLayout.EAST, btnBid, -80, SpringLayout.EAST,
			                estimationGame.getContentPane());
			        btnBid.setForeground(new Color(224, 255, 255));
			        btnBid.setOpaque(false);
			        btnBid.setContentAreaFilled(false);
			        btnBid.setBorderPainted(false);
			        btnBid.setAlignmentX(Component.CENTER_ALIGNMENT);
			        btnBid.setFont(new Font("Segoe Script", Font.PLAIN, 18));
			        estimationGame.getContentPane().add(btnBid); 
					
					btnBid.addActionListener(new ActionListener() {
			        	@Override
			            public void actionPerformed(ActionEvent e) {
			            	selectedBid = bid;
			                btnBid.setVisible(false);
			                System.exit(0);
			            }
			        });
				}
			}
		}
	}


	public void displayTrump(String trumpSuit){
		JLabel lblTrump = new JLabel("The trump suit is: " + trumpSuit);
        springLayout.putConstraint(SpringLayout.WEST, lblTrump, 80, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblTrump, -80, SpringLayout.EAST,
                estimationGame.getContentPane());
        lblTrump.setHorizontalAlignment(SwingConstants.CENTER);
        lblTrump.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTrump.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblTrump, 10, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblTrump.setFont(new Font("Segoe Script", Font.PLAIN, 10));
        estimationGame.getContentPane().add(lblTrump);
	}


	public void clearTableHand(List<JButton> tableHandCardList){
		for (JButton item: tableHandCardList){
			estimationGame.getContentPane().remove(item);
		}
		tableHandCardList.clear();
	}


	public void displayTableHand(ArrayList<PlayerCardArray> tableHand){
		for (PlayerCardArray playerCard: tableHand){
			if (playerCard.getPlayerId() == 0){
				int cardLeft = 300;
				int cardTop = 625;

				Card playedCard = playerCard.getPlayerCard();

				JButton btnCard = new JButton();

		        springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
		            estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + card_height, SpringLayout.NORTH,
		            estimationGame.getContentPane());

		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCard.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCard.setText("Not found");
		        }

		        tableHandCardList.add(btnCard);
		        estimationGame.getContentPane().add(btnCard);
			}

			if (playerCard.getPlayerId() == 1){
				int cardLeft = 300;
				int cardTop = 625;

				Card playedCard = playerCard.getPlayerCard();

				JButton btnCard = new JButton();

		        springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
		            estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + card_height, SpringLayout.NORTH,
		            estimationGame.getContentPane());

		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCard.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCard.setText("Not found");
		        }

		        tableHandCardList.add(btnCard);
		        estimationGame.getContentPane().add(btnCard);
			}

			if (playerCard.getPlayerId() == 2){
				int cardLeft = 300;
				int cardTop = 625;

				Card playedCard = playerCard.getPlayerCard();

				JButton btnCard = new JButton();

		        springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
		            estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + card_height, SpringLayout.NORTH,
		            estimationGame.getContentPane());

		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCard.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCard.setText("Not found");
		        }

		        tableHandCardList.add(btnCard);
		        estimationGame.getContentPane().add(btnCard);
			}

			if (playerCard.getPlayerId() == 3){
				int cardLeft = 300;
				int cardTop = 625;

				Card playedCard = playerCard.getPlayerCard();

				JButton btnCard = new JButton();

		        springLayout.putConstraint(SpringLayout.WEST, btnCard, (int) (cardLeft + card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.EAST, btnCard, (int) (cardLeft + card_width),
		            SpringLayout.WEST, estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.NORTH, btnCard, cardTop, SpringLayout.NORTH,
		            estimationGame.getContentPane());
		        springLayout.putConstraint(SpringLayout.SOUTH, btnCard, cardTop + card_height, SpringLayout.NORTH,
		            estimationGame.getContentPane());

		        try {
		        	ImageIcon cardImg = playedCard.getCardImage();
		        	btnCard.setIcon(new ImageIcon(cardImg.getImage().getScaledInstance(card_width, card_height, java.awt.Image.SCALE_SMOOTH)));
		        } catch (NullPointerException e){
		        	System.out.println("Image not found");
		        	btnCard.setText("Not found");
		        }

		        tableHandCardList.add(btnCard);
		        estimationGame.getContentPane().add(btnCard);
			}
		}
	}


	public void displayCards(ArrayList<Player> playerArray){
		for (Player player: playerArray){
			PlayerHand playerHand = player.getHand();
			ArrayList<Card> playerHandCards = playerHand.getHand();
			if (player instanceof Computer){
				int playerID = player.getPlayerId();
				displayComputerCards(playerID, playerHandCards.size());
			}else{
				displayPlayerCards(playerHandCards);
			}
		}
	}


	public void displayPlayerCards(ArrayList<Card> playerHandCards){
		int cardLeft = 300;
		int cardTop = 625;

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
	        	System.out.println(cardImg);
	        } catch (NullPointerException e){
	        	System.out.println("Image not found");
	        	btnCard.setText("Not found");
	        }

	        // btnCard.addActionListener(new ActionListener() {
	        // 	public void actionPerformed(ActionEvent e){

	        // 	}
	        // });

        	System.out.println(btnCard);
	        listButton.add(btnCard);
	        estimationGame.getContentPane().add(btnCard);
	    }
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
        springLayout.putConstraint(SpringLayout.NORTH, btnAvatarA, 520, SpringLayout.NORTH,
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

        int cardA_top = 280;
        int cardA_left = 350;

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
        springLayout.putConstraint(SpringLayout.WEST, btnAvatarB, 250, SpringLayout.WEST,
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

        int cardB_top = 5;
        int cardB_left = 20;

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
        springLayout.putConstraint(SpringLayout.NORTH, btnAvatarC, 190, SpringLayout.NORTH,
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

        int cardC_top = 10;
        int cardC_left = 50;

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

        int cardD_top = 175;
        int cardD_left = 450;

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
        springLayout.putConstraint(SpringLayout.NORTH, btnAvatarD, 140, SpringLayout.NORTH, btnCardD);
        springLayout.putConstraint(SpringLayout.WEST, btnAvatarD, 100, SpringLayout.EAST, btnCardD);
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