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
import cards.*;

public class Round {
	private int roundNumber;
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
}