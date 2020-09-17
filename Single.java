/**
 * The Single class is a subclass of the Hand class, and is used to model a hand of cards consisting of only one single card.
 * 
 * @author janicemeita
 *
 */
public class Single extends Hand {

	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor for building a Single hand with the specified player and list of cards.
	 * 
	 * @param player
	 * 		the player of this hand
	 * @param cards
	 * 		a list of cards
	 */
	public Single(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A method for retrieving the top card of this hand.
	 * 
	 * @return Card
	 * 		top card of this hand
	 */
	public Card getTopCard() {
		return this.getCard(0);
	}

	/**
	 * A method for checking if this is a valid hand.
	 * 
	 * @return a boolean variable indicating whether or not the hand is valid.
	 */
	public boolean isValid() {
		//Check size
		if (this.size()==1) {
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
		return "Single";
	}
}