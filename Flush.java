import java.util.ArrayList;

/**
 * The Flush class is a subclass of the Hand class, and is used to model a hand of cards consisting of five cards with the same suit.
 * 
 * @author janicemeita
 *
 */
public class Flush extends Hand {

	private static final long serialVersionUID = 1608338181994909183L;
	
	/**
	 * A constructor for building a Flush hand with the specified player and list of cards.
	 * 
	 * @param player
	 * 		the player of this hand
	 * @param cards
	 * 		a list of cards
	 */
	public Flush(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A method for retrieving the top card of this hand.
	 * 
	 * @return Card
	 * 		top card of this hand
	 */
	public Card getTopCard() {
		int highestrank=-1;
		int index=0;
		for (int i=0; i<5; i++) {
			if (this.getCard(i).rank>highestrank) {
				highestrank=this.getCard(i).rank;
				index=i;
			}
		}
		return this.getCard(index);
	}

	/**
	 * A method for checking if this is a valid hand.
	 * 
	 * @return a boolean variable indicating whether or not the hand is valid.
	 */
	public boolean isValid() {
		if (this.size()==5) {
			//Store all suits in an array list of integers.
			ArrayList<Integer> allsuits = new ArrayList<Integer>();
			allsuits.add(this.getCard(0).suit);
			allsuits.add(this.getCard(1).suit);
			allsuits.add(this.getCard(2).suit);
			allsuits.add(this.getCard(3).suit);
			allsuits.add(this.getCard(4).suit);
			//Loop from element 1 to the last element, if suit doesn't equal to the suit of the previous element, return false.
			for (int i=1; i<allsuits.size(); i++) {
				if (allsuits.get(i-1)!=allsuits.get(i)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * A method for returning a string specifying the type of this hand.
	 * 
	 * @return the type of this hand
	 */
	public String getType() {
		return "Flush";
	}
	
}