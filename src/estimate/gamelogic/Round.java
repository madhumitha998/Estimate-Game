/**
* The Round class that represents 1 round of the game
* 
*
* @author  Elias
* @version 1.1
* @since   2019-10-18 
*/

package gamelogic;

import java.util.ArrayList;
import java.util.HashMap;
import cards.*;

public class Round {
	private int roundNumber;
	private HashMap<Integer, Integer> roundCards;
	public Deck roundDeck;

	public Round(int roundNumber){
		this.roundNumber = roundNumber;
	}

	public Round(){
		roundDeck = new Deck();
	}

	public void setRound(int roundNumber){
		this.roundNumber = roundNumber;
	}

	public int getRound(){
		return roundNumber;
	}

	public void roundCards(){
		// hashmap of round to number of cards dealt
		// key: round
		// value: no. cards dealt
		for (int i=1;i<=6;i++){
			roundCards.put(i,i);
		}
		int excess = 2;
		for (int i=7;i<=11;i--){
			roundCards.put(i,i-excess);
			excess+=2;
		}
	}
}