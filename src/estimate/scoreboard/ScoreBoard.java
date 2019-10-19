/**
* The ScoreBoard class keeps track of the scores of the players
* 
*
* @author  Elias
* @version 1.3
* @since   2019-10-18 
*/

package scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

import estimate.player.*;
import estimate.gamelogic.*;
import helperFunctions.*;

public class ScoreBoard{
	private HashMap<Integer, ArrayList<Integer>> prediction;
	private ArrayList<HashMap<Integer, ArrayList<Integer>>> roundScore;
	private ArrayList<ArrayList<HashMap<Integer, ArrayList<Integer>>>> scoreBoard;
	private HashMap<Integer, Integer> totalScore;
	private int playerScore;
	private Tricks t;

	// HashMap<Integer, Integer> totalScore = new HashMap<Integer, Integer>();

	// public ScoreBoard(ArrayList<Player> players){
	// 	for(Player p: players){
	// 		scoreBoard.put(p.getPlayerId(), 0);
	// 	}
	// }

	public void setPrediction(ArrayList<Player> players){
		// set a hashmap of predictions according to player
		// value: array of bids and tricks
		// key: player id
		for (Player p: players){
			ArrayList<Integer> bidAndTricksWon = new ArrayList<Integer>();
			bidAndTricksWon.add(p.getBid(), t.tricksWon(p));
			
			prediction.put(p.getPlayerId(), bidAndTricksWon);
			roundScore.add(prediction);	//append prediction to roundScore, based on number of rounds
			scoreBoard.add(roundScore);
		}
	}

	public void setScore(ArrayList<Player> players) {
		// calculates the score of each player after each round
		for(Player p: players){
			if (p.getTrickWinner()){
				playerScore = playerScore + (10 + t.tricksWon(p));
			}else{
				playerScore = playerScore - (10 + t.tricksWon(p));
			}
			totalScore.put(p.getPlayerId(), playerScore);
		}
	}

	public HashMap getWinner(int round){
		// gets the game winner if player score >= 100 or round == 11
		HashMap<Integer, Integer> gameWinner = new HashMap<Integer, Integer>();
		if(round<11){
			for (Integer i: totalScore.values()) {
				if (i >= 100){
					gameWinner.put(MapUtil.getKey(totalScore, i), i);
				}
			}
		}else{
			int maxScore = (Collections.max(totalScore.values()));
			gameWinner.put(MapUtil.getKey(totalScore, maxScore), maxScore);
		}
		return gameWinner;	
	}

	public HashMap viewScore(){
		return totalScore;
	}
}