package estimate.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main menu GUI of the game. Contains the method to launch GameUI
 * @author Elias Lim
 * @version 2.0
 * @since   Nov 4, 2019
 */

@SuppressWarnings("serial")
public class MainMenuUI {
	private JFrame estimationGame;

	public MainMenuUI(){
		initComponents();
	}

    /**
     * Initialises the UI components for starting a new game in GameUI
     */
	private void initComponents(){
		estimationGame = new JFrame();
		estimationGame.getContentPane().setBackground(new Color(7,99,36));
        estimationGame.setTitle("Estimation Game");
        estimationGame.setBounds(100, 100, 450, 300);
        estimationGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SpringLayout springLayout = new SpringLayout();
        estimationGame.getContentPane().setLayout(springLayout);

        JLabel lblGameName = new JLabel("Estimation Game");

        springLayout.putConstraint(SpringLayout.WEST, lblGameName, 80, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, lblGameName, -80, SpringLayout.EAST,
                estimationGame.getContentPane());

        lblGameName.setHorizontalAlignment(SwingConstants.CENTER);
        lblGameName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblGameName.setForeground(Color.WHITE);
        springLayout.putConstraint(SpringLayout.NORTH, lblGameName, 10, SpringLayout.NORTH,
                estimationGame.getContentPane());
        lblGameName.setFont(new Font("Segoe Script", Font.PLAIN, 30));

        estimationGame.getContentPane().add(lblGameName);

        JButton btnNewGame = new JButton("New Game");

        btnNewGame.setFocusable(false);
        springLayout.putConstraint(SpringLayout.NORTH, btnNewGame, 30, SpringLayout.SOUTH, lblGameName);
        springLayout.putConstraint(SpringLayout.WEST, btnNewGame, 80, SpringLayout.WEST,
                estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnNewGame, -80, SpringLayout.EAST,
                estimationGame.getContentPane());
        btnNewGame.setForeground(new Color(224, 255, 255));
        btnNewGame.setOpaque(false);
        btnNewGame.setContentAreaFilled(false);
        btnNewGame.setBorderPainted(false);
        btnNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNewGame.setFont(new Font("Segoe Script", Font.PLAIN, 18));

        estimationGame.getContentPane().add(btnNewGame); 

        JButton btnInfo = new JButton("Game Info");

        btnInfo.setFocusable(false);
        springLayout.putConstraint(SpringLayout.NORTH, btnInfo, 130, SpringLayout.SOUTH, lblGameName);
        springLayout.putConstraint(SpringLayout.WEST, btnInfo, 80, SpringLayout.WEST, estimationGame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnInfo, -80, SpringLayout.EAST, estimationGame.getContentPane());
        btnInfo.setForeground(Color.WHITE);
        btnInfo.setOpaque(false);
        btnInfo.setContentAreaFilled(false);
        btnInfo.setBorderPainted(false);
        btnInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnInfo.setBackground(SystemColor.desktop);
        btnInfo.setFont(new Font("Segoe Script", Font.PLAIN, 18));

        estimationGame.getContentPane().add(btnInfo);

        /**
         * Launches the game on button click
         */
        btnNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameUI.main(null);
                btnNewGame.setVisible(false);
            }
        });

        /**
         * Displays an info message on button click
         */
        btnInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(estimationGame, "ESTIMATION GAME MADE BY:\n - ABEL \n - ELIAS \n - MADHUMITHA \n\nThanks for playing", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }


	public static void main(String args[]){
		try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
            e.printStackTrace();
        }

		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					MainMenuUI window = new MainMenuUI();
					window.estimationGame.setVisible(true);
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}
}
