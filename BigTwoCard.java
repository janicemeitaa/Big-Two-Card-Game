public class BigTwoCard extends Card {
	
	/**
	 * A subclass of the Card class, and is used to model a card used in a Big Two card game.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A constructor for building a card with the specified suit and rank.
	 * 
	 * @param suit
	 * @param rank
	 */
	public BigTwoCard(int suit, int rank) {
		super(suit, rank);
	}
	
	/**
	 * A method for comparing the order of this card with the specified card.
	 */
	public int compareTo(Card card) {
		int newrank = this.rank;
		int newcardrank = card.rank;
		//Change the ranks of the card to fit the order in the BigTwo card game rules
		if (this.rank==0) {
			newrank=13;
		} else if (this.rank==1) {
			newrank=14;
		}
		if (card.rank==0) {
			newcardrank=13;
		} else if (card.rank==1) {
			newcardrank=14;
		}
		if (newrank > newcardrank) {
			return 1;
		} else if (newrank < newcardrank) {
			return -1;
		} else if (this.suit > card.suit) {
			return 1;
		} else if (this.suit < card.suit) {
			return -1;
		} else {
			return 0;
		}
	}
	
}