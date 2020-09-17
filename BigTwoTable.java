import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;

import javax.swing.*;

/**
 * The BigTwoTable class implements the CardGameTable interface. 
 * It is used to build a GUI for the Big Two card game and handle all user actions.
 * 
 * @author janicemeita
 *
 */
public class BigTwoTable implements CardGameTable {
	
	/**
	 * A constructor for creating a BigTwoTable.
	 * 
	 * @param game
	 */
	BigTwoTable(CardGame game){
		this.game= (BigTwoClient) game;
		selected=new boolean[13];
		avatar_xc = new int[] {20, 980, 20, 980};
		avatar_yc = new int[] {30, 30, 560, 560};
		startcardsx = new int[] {20, 820, 20, 820};
		startcardsy = new int[] {233, 233, 414, 414};
		this.setUpGame();
		this.setUpImages();
	}
	
	private void setUpGame() {
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel buttonPanel = new JPanel();
		playButton = new JButton("Play");
		playButton.addActionListener(new PlayButtonListener());
		buttonPanel.add(playButton);
		passButton = new JButton("Pass");
		passButton.addActionListener(new PassButtonListener());
		buttonPanel.add(passButton);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
		bigTwoPanel=new BigTwoPanel();
		frame.add(bigTwoPanel, BorderLayout.CENTER);
		
		JMenuBar menuBar=new JMenuBar();
		JMenu menu=new JMenu("Game");
		JMenuItem item1=new JMenuItem("Connect");
		item1.addActionListener(new ConnectMenuItemListener());
		menu.add(item1);
		JMenuItem item2=new JMenuItem("Quit");
		item2.addActionListener(new QuitMenuItemListener());
		menu.add(item2);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		JPanel messages = new JPanel();
		messages.setLayout(new BoxLayout(messages, BoxLayout.PAGE_AXIS));
		
		msgArea=new JTextArea();
		JScrollPane scroll = new JScrollPane (msgArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(300, 600));
		msgArea.setEditable(false);
		messages.add(scroll);
		
		chatMsgArea=new JTextArea();
		JScrollPane chatScroll = new JScrollPane (chatMsgArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatScroll.setPreferredSize(new Dimension(300, 600));
		chatMsgArea.setEditable(false);
		messages.add(chatScroll);
		
		JPanel chat = new JPanel();
		chat.setPreferredSize(new Dimension(300, 20));
		chat.add(new JLabel("Message:", SwingConstants.LEFT));
		sendChatMsg=new JTextField();
		sendChatMsg.addActionListener(new EnterMsgListener());
		sendChatMsg.setPreferredSize(new Dimension(210, 20));
		chat.add(sendChatMsg);
		messages.add(chat);
		
		frame.add(messages, BorderLayout.EAST);

		frame.setSize(1450, 1450);
		frame.setVisible(true);
	}
	
	private void setUpImages() {
		
		cardImages=new Image[4][13];
		avatars=new Image[4];
		
		avatars[0] = new ImageIcon("avatars/alice.png").getImage();
		avatars[1] = new ImageIcon("avatars/cheshire.png").getImage();
		avatars[2] = new ImageIcon("avatars/queen.png").getImage();
		avatars[3] = new ImageIcon("avatars/rabbit.png").getImage();
		
		cardImages[0][0] = new ImageIcon("cards/AD.png").getImage();
		cardImages[1][0] = new ImageIcon("cards/AC.png").getImage();
		cardImages[2][0] = new ImageIcon("cards/AH.png").getImage();
		cardImages[3][0] = new ImageIcon("cards/AS.png").getImage();
		
		cardImages[0][1] = new ImageIcon("cards/2D.png").getImage();
		cardImages[1][1] = new ImageIcon("cards/2C.png").getImage();
		cardImages[2][1] = new ImageIcon("cards/2H.png").getImage();
		cardImages[3][1] = new ImageIcon("cards/2S.png").getImage();
		
		cardImages[0][2] = new ImageIcon("cards/3D.png").getImage();
		cardImages[1][2] = new ImageIcon("cards/3C.png").getImage();
		cardImages[2][2] = new ImageIcon("cards/3H.png").getImage();
		cardImages[3][2] = new ImageIcon("cards/3S.png").getImage();
		
		cardImages[0][3] = new ImageIcon("cards/4D.png").getImage();
		cardImages[1][3] = new ImageIcon("cards/4C.png").getImage();
		cardImages[2][3] = new ImageIcon("cards/4H.png").getImage();
		cardImages[3][3] = new ImageIcon("cards/4S.png").getImage();
		
		cardImages[0][4] = new ImageIcon("cards/5D.png").getImage();
		cardImages[1][4] = new ImageIcon("cards/5C.png").getImage();
		cardImages[2][4] = new ImageIcon("cards/5H.png").getImage();
		cardImages[3][4] = new ImageIcon("cards/5S.png").getImage();
		
		cardImages[0][5] = new ImageIcon("cards/6D.png").getImage();
		cardImages[1][5] = new ImageIcon("cards/6C.png").getImage();
		cardImages[2][5] = new ImageIcon("cards/6H.png").getImage();
		cardImages[3][5] = new ImageIcon("cards/6S.png").getImage();
		
		cardImages[0][6] = new ImageIcon("cards/7D.png").getImage();
		cardImages[1][6] = new ImageIcon("cards/7C.png").getImage();
		cardImages[2][6] = new ImageIcon("cards/7H.png").getImage();
		cardImages[3][6] = new ImageIcon("cards/7S.png").getImage();
		
		cardImages[0][7] = new ImageIcon("cards/8D.png").getImage();
		cardImages[1][7] = new ImageIcon("cards/8C.png").getImage();
		cardImages[2][7] = new ImageIcon("cards/8H.png").getImage();
		cardImages[3][7] = new ImageIcon("cards/8S.png").getImage();
		
		cardImages[0][8] = new ImageIcon("cards/9D.png").getImage();
		cardImages[1][8] = new ImageIcon("cards/9C.png").getImage();
		cardImages[2][8] = new ImageIcon("cards/9H.png").getImage();
		cardImages[3][8] = new ImageIcon("cards/9S.png").getImage();
		
		cardImages[0][9] = new ImageIcon("cards/10D.png").getImage();
		cardImages[1][9] = new ImageIcon("cards/10C.png").getImage();
		cardImages[2][9] = new ImageIcon("cards/10H.png").getImage();
		cardImages[3][9] = new ImageIcon("cards/10S.png").getImage();
		
		cardImages[0][10] = new ImageIcon("cards/JD.png").getImage();
		cardImages[1][10] = new ImageIcon("cards/JC.png").getImage();
		cardImages[2][10] = new ImageIcon("cards/JH.png").getImage();
		cardImages[3][10] = new ImageIcon("cards/JS.png").getImage();
		
		cardImages[0][11] = new ImageIcon("cards/QD.png").getImage();
		cardImages[1][11] = new ImageIcon("cards/QC.png").getImage();
		cardImages[2][11] = new ImageIcon("cards/QH.png").getImage();
		cardImages[3][11] = new ImageIcon("cards/QS.png").getImage();
		
		cardImages[0][12] = new ImageIcon("cards/KD.png").getImage();
		cardImages[1][12] = new ImageIcon("cards/KC.png").getImage();
		cardImages[2][12] = new ImageIcon("cards/KH.png").getImage();
		cardImages[3][12] = new ImageIcon("cards/KS.png").getImage();
		
		cardBackImage=new ImageIcon("cards/back.png").getImage();
		bg=new ImageIcon("bg2.png").getImage();
		
	}

	private BigTwoClient game;
	private boolean[] selected;
	private int activePlayer;
	private JFrame frame;
	private JPanel bigTwoPanel;
	private JButton playButton;
	private JButton passButton;
	private JTextArea msgArea;
	private JTextArea chatMsgArea;
	private JTextField sendChatMsg;
	private int[] avatar_xc;
	private int[] avatar_yc;
	private int[] startcardsx;
	private int[] startcardsy;
	private Image[][] cardImages;
	private Image cardBackImage;
	private Image bg;
	private Image[] avatars;
	
	/**
	 * A method for showing a dialog message during the end of the game.
	 */
	public void end() {
		JOptionPane.showMessageDialog(frame,
			    "End of game! Please click Restart or Quit.");
	}
	
	/**
	 * A method for setting the index of the active player.
	 * 
	 * @param activePlayer
	 * 		the index of the active player
	 */
	@Override
	public void setActivePlayer(int activePlayer) {
		this.activePlayer=activePlayer;
	}
	
	/**
	 * A method for getting an array of indices of the cards selected.
	 * 
	 * @return the cards selected
	 */
	@Override
	public int[] getSelected() {
		int [] cardsSelected;
		int c=0, numofcards=0;
		for (int i=0; i<13; i++) {
			if (selected[i]==true) {
				numofcards++;
			}
		}
		if (numofcards>0) {
			cardsSelected = new int[numofcards];
			for (int i=0; i<13; i++) {
				if (selected[i]==true) {
					cardsSelected[c]=i;
					c++;
				}
			}
		} else {
			return null;
		}
		return cardsSelected;
	}
	
	/**
	 * A method for resetting the list of selected cards.
	 */
	@Override
	public void resetSelected() {
		for (int i=0; i<selected.length; i++) {
			selected[i]=false;
		}
	}
	
	/**
	 * A method for repainting the GUI.
	 */
	@Override
	public void repaint() {
		resetSelected();
		frame.repaint();
	}
	
	/**
	 * A method for printing the specified string to the message area of the GUI.
	 */
	@Override
	public void printMsg(String msg) {
		msgArea.append(msg);
	}
	
	public void printChatMsg(String msg) {
		chatMsgArea.append(msg);
	}
	
	/**
	 * A method for clearing the message area of the GUI.
	 */
	@Override
	public void clearMsgArea() {
		msgArea.setText("");
	}
	
	/**
	 * A method for clearing the chat message area of the GUI.
	 */
	public void clearChatMsgArea() {
		chatMsgArea.setText("");
	}
	
	/**
	 * A method for resetting the GUI.
	 */
	@Override
	public void reset() {
		clearMsgArea();
		clearChatMsgArea();
		resetSelected();
		enable();
	}
	
	/**
	 * A method for enabling user interactions with the GUI.
	 */
	@Override
	public void enable() {
		playButton.setEnabled(true);
		passButton.setEnabled(true);
		bigTwoPanel.setEnabled(true);
	}
	
	/**
	 * A method for disabling user interactions with the GUI.
	 */
	@Override
	public void disable() {
		playButton.setEnabled(false);
		passButton.setEnabled(false);
		bigTwoPanel.setEnabled(false);
	}
	
	/**
	 * An inner class that extends the JPanel class and implements the MouseListener interface. 
	 * 
	 * @author janicemeita
	 *
	 */
	class BigTwoPanel extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;
		
		/**
		 * A constructor for creating the BigTwoPanel.
		 */
		public BigTwoPanel() {
			this.addMouseListener(this);
		}
		
		/**
		 * Overriding the paintComponent() method inherited from the JPanel class to draw the card game table.
		 */
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(bg, 0, 0, null);
			g.setColor(Color.WHITE);
			Font thisfont=new Font("Arial", 1, 12);
			g.setFont(thisfont);
			for (int i=0; i<4; i++) {
				g.drawImage(avatars[i], avatar_xc[i], avatar_yc[i], 150, 150, this);
			}
			g.drawString(game.getPlayerList().get(0).getName(), 20, 20);
			g.drawString(game.getPlayerList().get(1).getName(), 1085, 20);
			g.drawString(game.getPlayerList().get(2).getName(), 20, 730);
			g.drawString(game.getPlayerList().get(3).getName(), 1085, 730);
			int sx, sy, suit=-1, rank=-1;
			for (int i=0; i<4; i++) {
				sx=startcardsx[i];
				sy=startcardsy[i];
				if (activePlayer==i) {
					for (int j=0; j<game.getPlayerList().get(i).getNumOfCards(); j++) {
						suit=game.getPlayerList().get(i).getCardsInHand().getCard(j).getSuit();
						rank=game.getPlayerList().get(i).getCardsInHand().getCard(j).getRank();
						if (selected[j]==true) {
							g.drawImage(cardImages[suit][rank], sx, sy-30, 70, 107, this);
						} else {
							g.drawImage(cardImages[suit][rank], sx, sy, 70, 107, this);
						}
						sx+=20;
					}
				} else {
					for (int j=0; j<game.getPlayerList().get(i).getNumOfCards(); j++) {
						g.drawImage(cardBackImage, sx, sy, 70, 107, this);
						sx+=20;
					}
				}
			}
			if (game.getHandsOnTable().size()>0) {
				int size=game.getHandsOnTable().size();
				Hand thishand=game.getHandsOnTable().get(size-1);
				int startingpos;
				String playername=thishand.getPlayer().getName();
				g.drawString("HAND ON TABLE", 523, 304);
				g.drawString(playername, 550, 319);
				if (thishand.size()==1) {
					g.drawImage(cardImages[thishand.getCard(0).getSuit()][thishand.getCard(0).getRank()], 540, 334, 70, 107, this);
				} else if (thishand.size()==2) {
					startingpos=530;
					for (int i=0; i<2; i++) {
						g.drawImage(cardImages[thishand.getCard(i).getSuit()][thishand.getCard(i).getRank()], startingpos, 334, 70, 107, this);
						startingpos+=20;
					}
				} else if (thishand.size()==3) {
					startingpos=520;
					for (int i=0; i<3; i++) {
						g.drawImage(cardImages[thishand.getCard(i).getSuit()][thishand.getCard(i).getRank()], startingpos, 334, 70, 107, this);
						startingpos+=20;
					}
				} else if (thishand.size()==5) {
					startingpos=500;
					for (int i=0; i<5; i++) {
						g.drawImage(cardImages[thishand.getCard(i).getSuit()][thishand.getCard(i).getRank()], startingpos, 334, 70, 107, this);
						startingpos+=20;
					}
				}
			}
			
			repaint();
		}
		
		/**
		 * Implements the mouseClicked() method from the MouseListener interface to handle mouse click events.
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			int startx=startcardsx[activePlayer];
			int starty=startcardsy[activePlayer];
			int mouseX=e.getX();
			int mouseY=e.getY();
			int counter=0;
			int numofcards=game.getPlayerList().get(activePlayer).getNumOfCards();
			//msgArea.append(String.valueOf(numofcards));
			for (int i=0; i<numofcards; i++) {
				if (i==numofcards-1) {
					if (mouseY>starty && mouseY<starty+107 && mouseX>startx && mouseX<startx+70 && !selected[counter]) {
						selected[counter]=true;
					} else if (mouseY>starty-30 && mouseY<starty-30+107 && mouseX>startx && mouseX<startx+70 && selected[counter]) {
						selected[counter]=false;
					}
				} else {
					if (mouseY>starty && mouseY<starty+107 && mouseX>startx && mouseX<startx+20 && !selected[counter]) {
						selected[counter]=true;
					} else if (mouseY>starty-30 && mouseY<starty-30+107 && mouseX>startx && mouseX<startx+20 && selected[counter]) {
						selected[counter]=false;
					}
				}
				startx+=20;
				counter++;
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * An inner class for playButton that implements the ActionListener interface.
	 * 
	 * @author janicemeita
	 *
	 */
	class PlayButtonListener implements ActionListener {

		/**
		 * Implements the actionPerformed() method from the ActionListener interface to handle 
		 * button-click events for the “Play” button.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (getSelected()==null) {
				JOptionPane.showMessageDialog(frame, "Please select cards to play.", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			} else {
				game.makeMove(activePlayer, getSelected());
			}
			resetSelected();
			repaint();
		}
		
	}
	
	/**
	 * An inner class for passButton that implements the ActionListener interface.
	 * 
	 * @author janicemeita
	 *
	 */
	class PassButtonListener implements ActionListener {

		/**
		 * Implements the actionPerformed() method from the ActionListener interface to handle 
		 * button-click events for the “Pass” button.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(frame, "Are you sure you want to pass your turn?", "WARNING",
				    JOptionPane.WARNING_MESSAGE);
			game.makeMove(activePlayer, null);
			resetSelected();
			repaint();
		}
		
	}
	
	/**
	 * An inner class for the connect menu item that implements the ActionListener interface.
	 * 
	 * @author janicemeita
	 *
	 */
	class ConnectMenuItemListener implements ActionListener {

		/**
		 * Implements the actionPerformed() method from the ActionListener interface to handle 
		 * menu-item-click events for the “Restart” menu item.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!game.isConnected()) 
			{
				reset();
				game.makeConnection();
			}
		}
		
	}
	
	/**
	 * An inner class for the quit menu item that implements the ActionListener interface.
	 * 
	 * @author janicemeita
	 *
	 */
	class QuitMenuItemListener implements ActionListener {

		/**
		 *  Implements the actionPerformed() method from the ActionListener interface to handle 
		 *  menu-item-click events for the “Quit” menu item.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	/**
	 * An inner class for the enter click event that implements the ActionListener interface.
	 * 
	 * @author janicemeita
	 *
	 */
	class EnterMsgListener implements ActionListener {

		/**
		 * Implements the actionPerformed() method from the ActionListener interface to handle 
		 * enter click events for the chat message area.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			game.sendMessage(new CardGameMessage(CardGameMessage.MSG, -1, sendChatMsg.getText()));
			sendChatMsg.setText("");
		}
		
	}
}
