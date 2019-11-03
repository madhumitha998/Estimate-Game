package estimate.gamelogic;

import estimate.player.*;

public class Tricks{
	private int trick;

	public Tricks(){
		int trick = 0;
	}

	public int calculateTricksWon(Player player){
		if (player.getTrickWinner()){
			trick++;
		}
		return trick;
	}
}