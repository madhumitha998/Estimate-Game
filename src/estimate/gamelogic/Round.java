package gamelogic;

import java.util.ArrayList;
import cards.*;

public class Round {
	private ArrayList<Prediction> playerPrediction = new ArrayList<Prediction>();
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