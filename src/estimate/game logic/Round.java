import java.util.ArrayList;
import cards.*;

public class Round {
	private ArrayList<Prediction> playerPrediction = new ArrayList<Prediction>();
	private ArrayList<Card> playedCards = new ArrayList<Card>();
	private int roundNumber;

	public Round(){
		roundDeck = new Deck();
	}

	public void setRound(int inputRoundNumber){
		this.inputRoundNumber = inputRoundNumber;
	}

	public int getRound(){
		return roundNumber;
	}
}