/**
 * The Pair class is a subclass of the Hand class, and is used to model a hand of cards consisting of two cards with the same rank. 
 * 
 * @author janicemeita
 *
 */
public class Pair extends Hand {

	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor for building a Pair hand with the specified player and list of cards.
	 * 
	 * @param player
	 * 		the player of this hand
	 * @param cards
	 * 		a list of cards
	 */
	public Pair(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A method for retrieving the top card of this hand.
	 * 
	 * @return Card
	 * 		top card of this hand
	 */
	public Card getTopCard() {
		if (this.getCard(0).suit > this.getCard(1).suit) {
			return this.getCard(0);		
		}
		return this.getCard(1);
	}
	
	/**
	 * A method for checking if this is a valid hand.
	 * 
	 * @return a boolean variable indicating whether or not the hand is valid.
	 */
	public boolean isValid() {
		//Check size and if the ranks are equal
		if (this.size()==2 && (this.getCard(0).rank==this.getCard(1).rank)) {
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
		return "Pair";
	}
}