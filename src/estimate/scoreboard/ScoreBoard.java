/**
* The ScoreBoard class keeps track of the scores of the players
* 
*
* @author  Elias
* @version 2
* @since   2019-10-18 
*/

package estimate.scoreboard;

import java.util.*;

import estimate.player.*;
import estimate.gamelogic.*;
import helperFunctions.*;

public class ScoreBoard{
	private ArrayList<Integer> roundPlayerScores;
	private ArrayList<Integer> roundBids;
	private ArrayList<Integer> tricksWon;
	private HashMap<Integer, Score> totalScore;
	private ArrayList<Integer> cumulativePlayerScore;
	private Tricks tricks;
	private Round round;

	public ScoreBoard(){
		totalScore = new HashMap<Integer, Score>();
		cumulativePlayerScore = new ArrayList<Integer>(Collections.nCopies(4,0));
	}

    /**
     * calculates the score of each player after each round
	 * Assumes that players array is ascending-ly sorted according to ID of player
     */
	public List calculateRoundPlayerScore(ArrayList<Player> players) {
		List roundPlayerScores = new ArrayList<Integer>();
		for (Player p: players){
			int playerScore = 0;
			if (p.getTrickWinner()){
				playerScore = playerScore + (10 + tricks.calculateTricksWon(p));
			}else{
				playerScore = playerScore - (10 + tricks.calculateTricksWon(p));
			}
			roundPlayerScores.add(playerScore);
		}
		return roundPlayerScores;
	}


	/**
     * gets the bid amount of each player after each round
     */
	public List getRoundBids(ArrayList<Player> players){
		List roundBids = new ArrayList<Integer>();
		for (Player p: players){
			roundBids.add(p.getBid());
		}
		return roundBids;
	}


	/**
     * gets the total tricks won of each player after each round
     */
	public List getTricksWon(ArrayList<Player> players){
		List tricksWon = new ArrayList<Integer>();
		for (Player p: players){
			tricksWon.add(tricks.calculateTricksWon(p));
		}
		return tricksWon;
	}


	/**
     * calculates the cumulative score for each player
     */
	public void getCumulativePlayerScore(){
		for (int i = 0; i < 4; i++){
			roundPlayerScores.set(i, (roundPlayerScores.get(i) + cumulativePlayerScore.get(i)));
		}
	}


	/**
     * gets the score for the current round
     */
	public Score getCurrentScore(ArrayList<Player> players){
		Score score = new Score(roundBids, tricksWon, roundPlayerScores);
		return score;
	}


	/**
     * updates the total score with the current score for the round
     */
	public void updateFullScore(Score score){
		int roundNumber = round.getRound();
		totalScore.put(roundNumber, score);
	}


	/**
     * returns the totalscore
     */
	public HashMap viewScore(){
		return totalScore;
	}


	/**
     * returns the game winner if player score >= 100 or round == 11
     */
	public HashMap getWinner(){
		HashMap<Integer, Integer> gameWinner = new HashMap<Integer, Integer>();

		if (round.getRound() < 11){
			for (int i = 0; i < cumulativePlayerScore.size(); i++) {
				if (cumulativePlayerScore.get(i) >= 100){
					gameWinner.put(i, cumulativePlayerScore.get(i));
				}
			}
		}else{
			int maxScore = Collections.max(cumulativePlayerScore);
			gameWinner.put(cumulativePlayerScore.indexOf(maxScore), maxScore);
		}
		return gameWinner;	
	}

	/**
	 * Adds a round to the ScoreBoard hashmap
	 */
	public void addRoundToScoreboard(Score roundScore) {

	}

	/**
	 * Get current round results
	 */

	/**
	 * Add winner of trick to current round
	 */



}