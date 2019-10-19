package estimate.gamelogic;

import estimate.player.*;

public class Tricks{
	private int playerTrickCounter;

	public int tricksWon(Player player){
		if (player.getTrickWinner()){
			playerTrickCounter++;
		}		
		return playerTrickCounter;
	}
}