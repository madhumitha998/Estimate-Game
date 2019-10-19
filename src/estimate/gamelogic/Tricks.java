package gamelogic;

import player.*;

public class Tricks{
	private int playerTrickCounter;

	public int tricksWon(Player player){
		if (player.getTrickWinner()){
			playerTrickCounter++;
		}		
		return playerTrickCounter;
	}
}