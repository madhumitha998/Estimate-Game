package gamelogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


@SuppressWarnings("serial")
class GUI extends JFrame{
	private JLabel label1;
	private JMenu menu1;
	private JMenuBar menuBar1;
	private JMenuItem menuItem1;
	private JMenuItem menuItem2;
	private JMenuItem menuItem3;
	private JMenuItem menuItem4;
	private JMenuItem menuItem5;


	public GUI(){
		initComponents();
	}

	private void initComponents(){
		menu1 = new JMenu();
		menuItem1.setText("New Game");
		menuItem1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				menuItem1ActionPerformed(e);
			}
		});
	}

	private void menuItem1ActionPerformed(ActionEvent e){

	}
}