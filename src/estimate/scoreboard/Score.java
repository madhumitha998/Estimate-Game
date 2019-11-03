package estimate.scoreboard;

import java.util.*;

// import estimate.player.*;
// import estimate.gamelogic.*;


public class Score {
	private ArrayList<Integer> tricksWon;
	private ArrayList<Integer> roundScore;
	private ArrayList<Integer> roundBid;
	private HashMap<String, ArrayList<Integer>> score;

	public Score(ArrayList<Integer> roundBid, ArrayList<Integer> tricksWon, ArrayList<Integer> roundScore){
		this.roundBid = roundBid;
		this.tricksWon = tricksWon;
		this.roundScore = roundScore;
	}

	public void setScore(){
		Map<String, ArrayList<Integer>> score = new HashMap<String, ArrayList<Integer>>();
		score.put("Bids", roundBid);
		score.put("Tricks Won", tricksWon);
		score.put("Score", roundScore);
	}
}