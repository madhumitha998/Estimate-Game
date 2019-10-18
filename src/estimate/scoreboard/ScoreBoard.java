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

public class ScoreBoard{
	private HashMap<Integer, ArrayList<Integer>> prediction;
	private ArrayList<HashMap<Integer, ArrayList<Integer>>> roundScore;
	private ArrayList<ArrayList<HashMap<Integer, ArrayList<Integer>>>> scoreBoard;
	private HashMap<Integer, Integer> totalScore;

	// HashMap<Integer, Integer> totalScore = new HashMap<Integer, Integer>();

	public ScoreBoard(ArrayList<Player> players){
		for(Player p: players){
			scoreBoard.put(p.id, 0);
		}
	}

	public void setPrediction(ArrayList<Player> players){
		// set a hashmap of predictions according to player
		// value: array of bids and tricks
		// key: player id
		for (Player p: players){
			ArrayList<Integer> bidAndTricksWon = new ArrayList<Integer>();
			bidAndTricksWon.add(p.getBid(), trickswon);
			
			prediction.put(p.id, bidAndTricksWon);
			roundScore.add(prediction);	//append prediction to roundScore, based on number of rounds
			scoreBoard.add(roundScore);
		}
	}

	public void setScore(ArrayList<Player> players) {
		int playerScore;
		for(Player p: players){
			if (p.getTrickWinner()){
				playerScore = playerScore + (10 + p.getBid());
			}else{
				playerScore = playerScore - (10 + p.getBid());
			}
			totalScore.put(p.id, playerScore);
		}
	}

	public HashMap getWinner(Player player, int round){
		HashMap<Integer, Integer> gameWinner = new HashMap<Integer, Integer>();
		if(round<11){
			for (Integer i: totalScore.values()) {
				if (i >= 100){
					gameWinner.put(player.id, i);
				}
			}
		}else{
			int maxScore = (Collections.max(totalScore.values()));
			gameWinner.put(player.id, maxScore);
		}
		return gameWinner;	
	}
}