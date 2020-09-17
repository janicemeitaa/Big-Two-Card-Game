import java.util.ArrayList;

/**
 * The FullHouse class is a subclass of the Hand class, and is used to model a hand of cards consisting of five cards,
 * with two having the same rank and three having another same rank. 
 * 
 * @author janicemeita
 *
 */
public class FullHouse extends Hand {

	private static final long serialVersionUID = -7679346592798697368L;
	
	/**
	 * A constructor for building a FullHouse hand with the specified player and list of cards.
	 * 
	 * @param player
	 * 		the player of this hand
	 * @param cards
	 * 		a list of cards
	 */
	public FullHouse(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}

	/**
	 * A method for retrieving the top card of this hand.
	 * 
	 * @return Card
	 * 		top card of this hand
	 */
	public Card getTopCard() {
		int count=0;
		//Store all ranks in an array list of integers.
		ArrayList<Integer> cardranks = new ArrayList<Integer>();
		for (int i=0; i<this.size(); i++) {
			if (!cardranks.contains(this.getCard(i).rank)) {
				cardranks.add(this.getCard(i).rank);
			}
		}
		//Store the triple in findtop array list of type Card.
		ArrayList<Card> findtop = new ArrayList<Card>();
		int thisi=0;
		while (count!=3) {
			for (int i=0; i<this.size(); i++) {
				if (this.getCard(i).rank==cardranks.get(thisi)) {
					findtop.add(this.getCard(i));
					count++;
				}
			}
			//If not the triple is found
			if (count!=3) {
				count=0;
				for (int i=0; i<findtop.size(); i++) {
					findtop.remove(i);
				}		
				thisi++;
			}
		}
		int highestsuit=-1;
		int index=0;
		//Loop through findtop array list to find the card with the highest suit.
		for (int i=0; i<findtop.size(); i++) {
			if (findtop.get(i).suit>highestsuit) {
				highestsuit=findtop.get(i).suit;
				index=i;
			}
		}
		return findtop.get(index);
	}

	/**
	 * A method for checking if this is a valid hand.
	 * 
	 * @return a boolean variable indicating whether or not the hand is valid.
	 */
	public boolean isValid() {
		if (this.size()==5) {
			int count=0;
			ArrayList<Integer> cardranks = new ArrayList<Integer>();
			//Store the 2 different ranks in cardranks array list.
			for (int i=0; i<this.size(); i++) {
				if (!cardranks.contains(this.getCard(i).rank)) {
					cardranks.add(this.getCard(i).rank);
				}
			}
			//If the number of different ranks is not 2
			if (cardranks.size()!=2) {
				return false;
			}
			//Choose one of the ranks and count the number of cards with that rank
			for (int i=0; i<this.size(); i++) {
				if (this.getCard(i).rank==cardranks.get(0)){
					count++;
				}
			}
			if (count==2 || count==3) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * A method for returning a string specifying the type of this hand.
	 * 
	 * @return the type of this hand
	 */
	public String getType() {
		return "FullHouse";
	}
	

}