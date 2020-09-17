/**
 * The Triple class is a subclass of the Hand class, and is used to model a hand of cards consisting of three cards with the same rank. 
 * 
 * @author janicemeita
 *
 */
public class Triple extends Hand {

	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor for building a Triple hand with the specified player and list of cards.
	 * 
	 * @param player
	 * 		the player of this hand
	 * @param cards
	 * 		a list of cards
	 */
	public Triple(CardGamePlayer player, CardList cards) {
		super(player, cards);
	}
	
	/**
	 * A method for retrieving the top card of this hand.
	 * 
	 * @return Card
	 * 		top card of this hand
	 */
	public Card getTopCard() {
		//Comparing which card has the highest suit.
		if (this.getCard(0).suit>this.getCard(1).suit) {
			if (this.getCard(1).suit>this.getCard(2).suit) {
				return this.getCard(0);
			}
			if (this.getCard(0).suit>this.getCard(2).suit) {
				return this.getCard(0);
			}
			return this.getCard(2);
		} else {
			if (this.getCard(0).suit>this.getCard(2).suit) {
				return this.getCard(1);
			}
			if (this.getCard(1).suit>this.getCard(2).suit) {
				return this.getCard(1);
			}
			return this.getCard(2);
		}
	}
	
	/**
	 * A method for checking if this is a valid hand.
	 * 
	 * @return a boolean variable indicating whether or not the hand is valid.
	 */
	public boolean isValid() {
		//Check size and if all cards have the same rank.
		if (this.size()==3 && (this.getCard(0).rank==this.getCard(1).rank) && (this.getCard(1).rank==this.getCard(2).rank) && (this.getCard(0).rank==this.getCard(2).rank)) {
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
		return "Triple";
	}
}