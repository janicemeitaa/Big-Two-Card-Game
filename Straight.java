import java.util.ArrayList;

/**
 * The Straight class is a subclass of the Hand class, and is used to model a hand of cards consisting of five cards with consecutive ranks. 
 * 
 * @author janicemeita
 *
 */
public class Straight extends Hand {

	private static final long serialVersionUID = 6964828635397843851L;

	/**
	 * A constructor for building a Straight hand with the specified player and list of cards.
	 * 
	 * @param player
	 * 		the player of this hand
	 * @param cards
	 * 		a list of cards
	 */
	public Straight(CardGamePlayer player, CardList cards) {
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
			int thisnewrank=this.getCard(i).rank;
			//Change the ranks of the card to fit BigTwo card game rules.
			if (thisnewrank==0) {
				thisnewrank=13;
			} else if (thisnewrank==1) {
				thisnewrank=14;
			}
			if (thisnewrank>highestrank) {
				highestrank=thisnewrank;
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
			//Store all the ranks in array list of integers.
			ArrayList<Integer> allranks = new ArrayList<Integer>();
			allranks.add(this.getCard(0).rank);
			allranks.add(this.getCard(1).rank);
			allranks.add(this.getCard(2).rank);
			allranks.add(this.getCard(3).rank);
			allranks.add(this.getCard(4).rank);
			//Change ranks to fit BigTwo card game rules.
			for (int i=0; i<allranks.size(); i++) {
				if (allranks.get(i)==0) {
					allranks.remove(i);
					allranks.add(13);
				}
				if (allranks.get(i)==1) {
					allranks.remove(i);
					allranks.add(14);
				}
			}
			//Sort all ranks
			allranks.sort(null);
			//Check if the ranks are consecutive
			for (int i=1; i<allranks.size(); i++) {
				if (allranks.get(i)!=allranks.get(i-1)+1) {
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
		return "Straight";
	}
}