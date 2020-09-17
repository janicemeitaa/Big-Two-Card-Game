import java.util.ArrayList;

/**
 * The Quad class is a subclass of the Hand class, and is used to model a hand of cards consisting of five cards,
 * with four having the same rank. 
 * 
 * @author janicemeita
 *
 */
public class Quad extends Hand {
	
	private static final long serialVersionUID = -6513678899946621301L;

	/**
	 * A constructor for building a Quad hand with the specified player and list of cards.
	 * 
	 * @param player
	 * 		the player of this hand
	 * @param cards
	 * 		a list of cards
	 */
	public Quad(CardGamePlayer player, CardList cards) {
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
		//Store the four cards with the same rank in findtop array list of type Card.
		ArrayList<Card> findtop = new ArrayList<Card>();
		int thisi=0;
		while (count!=4) {
			for (int i=0; i<this.size(); i++) {
				if (this.getCard(i).rank==cardranks.get(thisi)) {
					findtop.add(this.getCard(i));
					count++;
				}
			}
			if (count!=4) {
				count=0;
				findtop.remove(0);				
				thisi++;
				count=0;
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
			if (count==1 || count==4) {
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
		return "Quad";
	}
	

}