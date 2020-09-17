import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * The BigTwoClient class implements the CardGame interface and NetworkGame interface. 
 * It is used to model a Big Two card game that supports 4 players playing over the internet.
 * 
 * @author janicemeita
 *
 */
class BigTwoClient implements CardGame, NetworkGame {
	
	private int numOfPlayers;
	private Deck deck;
	private ArrayList<CardGamePlayer> playerList;
	private ArrayList<Hand> handsOnTable;
	private int playerID;
	private String playerName;
	private String serverIP;
	private int serverPort;
	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private int currentIdx;
	private BigTwoTable table;
	private int previous;
	
	/**
	 * A constructor for creating a Big Two client.
	 */
	BigTwoClient() {
		playerList=new ArrayList<CardGamePlayer>();
		CardGamePlayer player1 = new CardGamePlayer();
		CardGamePlayer player2 = new CardGamePlayer();
		CardGamePlayer player3 = new CardGamePlayer();
		CardGamePlayer player4 = new CardGamePlayer();
		playerList.add(player1);
		playerList.add(player2);
		playerList.add(player3);
		playerList.add(player4);
		handsOnTable = new ArrayList<Hand>();
		table = new BigTwoTable(this);
		table.disable();
		
		String name = (String) JOptionPane.showInputDialog("Please enter your name: ");

		if (name==null) {
			setPlayerName("Default");
		} else {
			setPlayerName(name);
		}
		
		setServerIP("127.0.0.1");
		setServerPort(2396);
		makeConnection();
	}
	
	/**
	 * A method to check whether or not the game is connected to the server.
	 * 
	 * @return a boolean value specifying if the game is connected to the server
	 */
	public boolean isConnected() {
		if (sock.isClosed()) {
			return false;
		}
		return true;
	}
	
	/**
	 * A method for getting the number of players.
	 * 
	 * @return number of players
	 */
	public int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	/**
	 * A method for retrieving the deck of cards being used.
	 * 
	 * @return a deck of cards
	 */
	public Deck getDeck() {
		return deck;
	}
	
	/**
	 * A method for retrieving the index of the current player.
	 * 
	 * @return an integer specifying the index of the current player
	 */
	public int getCurrentIdx() {
		return currentIdx;
	}
	
	/**
	 * A method for starting/restarting the game with a given shuffled deck of cards.
	 * 
	 * @param deck
	 * 		a shuffled deck of cards
	 */
	public void start(Deck deck) {
		int index=0;
		previous = -1;
		handsOnTable.clear();
		
		for (int i=0; i<4; i++) {
			playerList.get(i).removeAllCards();
		}
		
		//Assign 13 cards randomly to each of the four players.
		for (int i=0; i<4; i++) {
			for (int j=index; j<index+13; j++) {
				playerList.get(i).addCard(deck.getCard(j));
			}
			index=index+12;
		}
		
		//Sort the 13 cards that each player holds.
		for (int i=0; i<4; i++) {
			playerList.get(i).getCardsInHand().sort();
		}
		
		//Check which player holds the 3 of diamonds. This player starts the game.
		for (int i=0; i<4; i++) {
			if (playerList.get(i).getCardsInHand().contains(new Card(0, 2))) {
				table.setActivePlayer(i);
				previous=i;
				currentIdx=i;
			}
		}

		table.printMsg("All players are ready. Game starts.");
		table.printMsg("\n");
		table.printMsg(this.getPlayerList().get(currentIdx).getName()+"'s turn:");
		table.printMsg("\n");
		table.repaint();
		if(this.playerID==currentIdx)
		{
			table.enable();
		}
		
		else
		{
			table.disable();
		}
	}
	
	/**
	 * A method for making a move by a player with the specified playerID using the cards specified by the list of indices.
	 * 
	 * @param playerID
	 * 		specified playerID
	 * @param cardIdx
	 * 		the cards being used to make the move
	 */
	public void makeMove(int playerID, int[] cardIdx) {
		CardGameMessage move = new CardGameMessage(CardGameMessage.MOVE, -1, cardIdx);
		sendMessage(move);
	}
	
	/**
	 * A method for checking a move made by a player.
	 * 
	 * @param playerID
	 * 		specified playerID
	 * @param cardIdx
	 * 		the cards being used to make the move
	 */
	public void checkMove(int playerID, int[] cardIdx) {
		boolean legal=false; //Determines whether or not it is a legal move.
		boolean pass=false; //Set to true whenever the player passes his/her turn.
		
		if (cardIdx==null) {
			//If the current player did not make the last move, and the current move is not the first move of the game.
			if (currentIdx!=previous && !handsOnTable.isEmpty()) {
				table.printMsg("{pass}");
				table.printMsg("\n");
				//Sets index of next player.
				currentIdx++;
				if (currentIdx>3) {
					currentIdx=0;
				}
				pass=true;
				table.setActivePlayer(currentIdx);
				table.printMsg(this.getPlayerList().get(currentIdx).getName()+"'s turn:");
				if(this.playerID==currentIdx)
				{
					table.enable();
				}
				
				else
				{
					table.disable();
				}
				table.printMsg("\n");
			} else {
				table.printMsg("Not a legal move!!!");
				table.printMsg("\n");
			}
		//If input is not null
		} else {
			CardList cards=playerList.get(currentIdx).play(cardIdx);
			Hand thishand=composeHand(playerList.get(currentIdx), cards);
			//Start of game
			if (handsOnTable.isEmpty()) {
				if (thishand!=null && thishand.contains(new Card(0, 2))) {
					legal=true;
				} else {
					legal=false;
				}
			} else {
				if (currentIdx!=previous) {
					if (thishand!=null) {
						//Check if this hand beats the previous hand
						if (thishand.beats(handsOnTable.get(handsOnTable.size()-1))) {
							legal=true;
						} else {
							legal=false;
						}
					} else {
						legal=false;
					}
				} else {
					if (thishand!=null) {
						legal=true;
					} else {
						legal=false;
					}
				}
			}
			
			//If it is a legal move
			if (legal) {
				//Card(s) removed from player
				for (int i=0; i<cards.size(); i++) {
					playerList.get(currentIdx).getCardsInHand().removeCard(cards.getCard(i));
				}
				//Hand is stored in handsOnTable
				handsOnTable.add(thishand);
				table.printMsg("{"+thishand.getType()+"} "+thishand);
				table.printMsg("\n");
				previous=currentIdx;
				currentIdx++;
				if (currentIdx>3) {
					currentIdx=0;
				}
				//Set next active player
				if (!endOfGame()) {
					table.setActivePlayer(currentIdx);
					table.printMsg(this.getPlayerList().get(currentIdx).getName()+"'s turn:");
					table.printMsg("\n");
				}
			} else {
				table.printMsg("Not a legal move!!!");
				table.printMsg("\n");
			}
			
			//Repaint if a legal move is made or a player passes his turn.
			if ((!endOfGame() && legal) || (!endOfGame() && pass)) {
				pass=false;
				if(this.playerID==currentIdx)
				{
					table.enable();
				}
				
				else
				{
					table.disable();
				}
				table.repaint();
			} 
		}
		//When the game ends
		if (endOfGame()) {
			table.repaint();
			String message="";
			message+="\n";
			message+="Game ends";
			message+="\n";
			for (int i=0; i<4; i++) {
				if(playerList.get(i).getCardsInHand().size() == 0)
				{
					message+="Player " + i + " wins the game.";
					message+="\n";
				}
				
				else
				{
					message+="Player " + i + " has " + playerList.get(i).getCardsInHand().size() + " cards in hand.";
					message+="\n";
				}
			}
			table.disable();
			table.end();
			JOptionPane.showMessageDialog(null, message);
			sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
		}
	}
	
	/**
	 * A method for checking if the game ends.
	 * 
	 * @return boolean value specifying whether or not the game has ended.
	 */
	public boolean endOfGame() {
		for (int i=0; i<4; i++) {
			if (playerList.get(i).getCardsInHand().size()==0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A method for getting the playerID (i.e., index) of the local player.
	 * 
	 * @return player ID
	 */
	@Override
	public int getPlayerID() {
		return playerID;
	}

	/**
	 * A method for setting the playerID (i.e., index) of the local player.
	 * 
	 * @param playerID
	 * 		ID of the player
	 */
	@Override
	public void setPlayerID(int playerID) {
		this.playerID=playerID;
		
	}

	/**
	 * A method for getting the name of the local player.
	 * 
	 * @return player name
	 */
	@Override
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * A method for setting the name of the local player.
	 * 
	 * @param playerName
	 * 		name of the local player
	 */
	@Override
	public void setPlayerName(String playerName) {
		this.playerName=playerName;
	}

	/**
	 * A method for getting the IP address of the game server.
	 * 
	 * @return IP address of the server
	 */
	@Override
	public String getServerIP() {
		return serverIP;
	}

	/**
	 * A method for setting the IP address of the game server.
	 * 
	 * @param serverIP
	 * 		IP address of the game server
	 */
	@Override
	public void setServerIP(String serverIP) {
		this.serverIP=serverIP;
	}

	/**
	 * A method for getting the TCP port of the game server.
	 * 
	 * @return TCP port of the game server
	 */
	@Override
	public int getServerPort() {
		return serverPort;
	}

	/**
	 * A method for setting the TCP port of the game server.
	 * 
	 * @param serverPort
	 * 		TCP port of the game server
	 */
	@Override
	public void setServerPort(int serverPort) {
		this.serverPort=serverPort;
	}

	/**
	 * A method for making a socket connection with the game server.
	 */
	@Override
	public void makeConnection() {
		try {
			sock = new Socket(serverIP, serverPort);
			oos = new ObjectOutputStream(sock.getOutputStream());
			Thread msgthread = new Thread(new ServerHandler());
			msgthread.start();
			sendMessage(new CardGameMessage(CardGameMessage.JOIN, -1, playerName));
			sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * A method for parsing the messages received from the game server.
	 */
	@Override
	public void parseMessage(GameMessage message) {
		if (message.getType()==CardGameMessage.PLAYER_LIST) {
			playerID=message.getPlayerID();
			table.setActivePlayer(playerID);
			for (int i=0; i<4; i++) {
				if (((String[])message.getData())[i]!=null) {
					this.getPlayerList().get(i).setName(((String[])message.getData())[i]);
				}
			}
		} else if (message.getType()==CardGameMessage.JOIN) {
			this.getPlayerList().get(message.getPlayerID()).setName(message.getData().toString());
			table.repaint();
			table.printMsg("Player "+this.getPlayerList().get(message.getPlayerID()).getName()+" has joined the game!");
			table.printMsg("\n");
		} else if (message.getType()==CardGameMessage.FULL) {
			table.printMsg("The server is full. The game cannot be joined.");
			table.printMsg("\n");
			try {
				sock.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (message.getType()==CardGameMessage.QUIT) {
			table.printMsg(this.getPlayerList().get(message.getPlayerID()).getName()+" has quit the game.");
			table.printMsg("\n");
			this.getPlayerList().get(message.getPlayerID()).setName("");
			if (!this.endOfGame()) {
				table.disable();
				sendMessage(new CardGameMessage(CardGameMessage.READY, -1, null));
			}
		} else if (message.getType()==CardGameMessage.READY) {
			table.printMsg(this.getPlayerList().get(message.getPlayerID()).getName()+" is ready.");
			table.printMsg("\n");
		} else if (message.getType()==CardGameMessage.START) {
			this.start((BigTwoDeck)message.getData());
		} else if (message.getType()==CardGameMessage.MOVE) {
			this.checkMove(message.getPlayerID(), (int[])message.getData());
		} else if (message.getType()==CardGameMessage.MSG) {
			table.printChatMsg((String)message.getData());
			table.printChatMsg("\n");
		}
	}

	/**
	 * A method for sending the specified message to the game server.
	 */
	@Override
	public void sendMessage(GameMessage message) {
		try {
			oos.writeObject(message);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * A method for getting the list of players.
	 */
	@Override
	public ArrayList<CardGamePlayer> getPlayerList() {
		return playerList;
	}

	/**
	 * A method for getting the list of hands played on the table.
	 */
	@Override
	public ArrayList<Hand> getHandsOnTable() {
		return handsOnTable;
	}
	
	/**
	 * An inner class that implements the Runnable interface.
	 * 
	 * @author janicemeita
	 *
	 */
	class ServerHandler implements Runnable {
		
		/**
		 * Implementing the run() method from the Runnable interface to receive messages from the game server. 
		 */
		public void run() {
			CardGameMessage message;
			try {
				ois=new ObjectInputStream(sock.getInputStream());
				while ((message = (CardGameMessage) ois.readObject()) != null) {
					parseMessage(message);
				}
				ois.close();
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * A method for creating an instance of BigTwoClient.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BigTwoClient bigTwoClient = new BigTwoClient();
	}
	
	/**
	 * A method for returning a valid hand from the specified list of cards of the player.
	 * 
	 * @param player
	 * 		player of the game
	 * @param cards
	 * 		specified list of cards
	 * 
	 * @return the valid hand
	 */
	public Hand composeHand(CardGamePlayer player, CardList cards) {
		Single single = new Single(player, cards);
		Pair pair = new Pair(player, cards);
		Triple triple = new Triple(player, cards);
		Straight straight = new Straight(player, cards);
		Flush flush = new Flush(player, cards);
		FullHouse fullhouse = new FullHouse(player, cards);
		Quad quad = new Quad(player, cards);
		StraightFlush straightflush = new StraightFlush(player, cards);
		//Call isValid() method to check what type of hand
		if (single.isValid()) {
			return single;
		} else if (pair.isValid()) {
			return pair;
		} else if (triple.isValid()) {
			return triple;
		} else if (straightflush.isValid()) {
			return straightflush;
		} else if (quad.isValid()) {
			return quad;
		} else if (fullhouse.isValid()) {
			return fullhouse;
		} else if (flush.isValid()) {
			return flush;
		} else if (straight.isValid()) {
			return straight;
		}
		return null;
	}

}