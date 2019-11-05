/**
* The Score class that keeps track of the score for 1 round
* 
*
* @author  Elias
* @version 1
* 
*/

package estimate.scoreboard;

import estimate.player.Player;

import java.util.*;


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

	public void setRoundBid (ArrayList<Integer> roundBid) {
		this.roundBid = roundBid;
	}

	/**
	 * Adds to the existing Score function the tricks won in the current trick
	 * @param trickScore
	 */
	public void addTricksWon(ArrayList<Integer> trickScore) {
		for (int i = 0 ; i < trickScore.size() ; i ++ ) {
			Integer currentScore = tricksWon.get(i);
			Integer newScore = currentScore + trickScore.get(i);
			tricksWon.set(i, newScore);
		}
	}

	/**
	 * Calculate Score based on the current variables
	 */
//	public void calculateScore(){
//
//	}



}