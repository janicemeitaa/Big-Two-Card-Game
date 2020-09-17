/**
 * The Hand class is a subclass of the CardList class, and is used to model a hand of cards.
 * 
 * @author janicemeita
 *
 */
public abstract class Hand extends CardList {

	private static final long serialVersionUID = 1L;
	
	/**
	 * A constructor for building a hand with the specified player and list of cards.
	 * 
	 * @param player
	 * 		the player of this hand
	 * @param cards
	 * 		a list of cards
	 */
	public Hand(CardGamePlayer player, CardList cards) {
		this.player=player;
		for (int i=0; i<cards.size(); i++) {
			this.addCard(cards.getCard(i));
		}
	}
	
	private CardGamePlayer player;
	
	/**
	 * A method for retrieving the player of this hand.
	 * 
	 * @return player
	 * 		the player of this hand
	 */
	public CardGamePlayer getPlayer() {
		return player;
	}
	
	/**
	 * A method for retrieving the top card of this hand.
	 * 
	 * @return Card
	 * 		top card of this hand
	 */
	public Card getTopCard() {
		return null;
	}
	
	/**
	 * A method for checking if this hand beats a specified hand.
	 * 
	 * @param hand
	 * 		specified hand
	 * 
	 * @return boolean variable indicating if this hand beats the specified hand.
	 */
	public boolean beats(Hand hand) {
		if (this.size()!=hand.size()) {
			return false;
		}
		int thisnewrank=this.getTopCard().rank;
		int handnewrank=hand.getTopCard().rank;
		//Change ranks of card to follow the BigTwo rules.
		if (thisnewrank==0) {
			thisnewrank=13;
		} else if (thisnewrank==1) {
			thisnewrank=14;
		} 
		if (handnewrank==0){
			handnewrank=13;
		} else if (handnewrank==1) {
			handnewrank=14;
		}
		//If Single or Double
		if (this.size()==1 || this.size()==2) {
			if (thisnewrank==handnewrank) {
				return this.getTopCard().suit>hand.getTopCard().suit;
			} else {
				return thisnewrank>handnewrank;
			}
		//If Triple
		} else if (this.size()==3) {
			return thisnewrank>handnewrank;
		//If 5 cards
		} else {
			String type=this.getType();
			//Check each type, if type matches the specified hand's type
			if (type==hand.getType()) {
				if (type=="Straight") {
					if (thisnewrank==handnewrank) {
						return this.getTopCard().suit>hand.getTopCard().suit;
					} else {
						return thisnewrank>handnewrank;
					}
				} else if (type=="Flush") {
					if (this.getTopCard().suit==hand.getTopCard().suit) {
						return thisnewrank>handnewrank;
					} else {
						return this.getTopCard().suit>hand.getTopCard().suit;
					}
				} else if (type=="FullHouse") {
					return thisnewrank>handnewrank;
				} else if (type=="Quad") {
					return thisnewrank>handnewrank;
				} else if (type=="StraightFlush") {
					if (thisnewrank==handnewrank) {
						return this.getTopCard().suit>hand.getTopCard().suit;
					} else {
						return thisnewrank>handnewrank;
					}
				}
			//If type of this hand and type of the specified hand is different
			} else {
				if (type=="StraightFlush") {
					return true;
				} else if ((type=="Quad") && ((hand.getType()=="Straight") || (hand.getType()=="Flush") || (hand.getType()=="FullHouse"))) {
					return true;
				} else if ((type=="FullHouse") && ((hand.getType()=="Straight") || (hand.getType()=="Flush"))) {
					return true;
				} else if ((type=="Flush") && (hand.getType()=="Straight")) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * A method for checking if this is a valid hand.
	 * 
	 * @return a boolean variable indicating whether or not the hand is valid.
	 */
	public abstract boolean isValid();
	
	/**
	 * A method for returning a string specifying the type of this hand.
	 * 
	 * @return the type of this hand
	 */
	public abstract String getType();
}