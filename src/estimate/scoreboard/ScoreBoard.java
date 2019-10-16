package scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;

public class ScoreBoard{
	private ArrayList<Rounds> scoreBoard;
	private HashMap<Integer, Integer> totalScore;
	// HashMap<Integer, Integer> totalScore = new HashMap<Integer, Integer>();

	public ScoreBoard(Player[] players){
		HashMap<Integer, Integer> scoreBoard = new HashMap<Integer, Integer>();
		for(Player p: players){
			scoreBoard.put(p.id, 0);
		}
	}

	public void setScore(Player[] players) {
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