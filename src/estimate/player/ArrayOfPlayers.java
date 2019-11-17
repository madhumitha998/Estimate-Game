package estimate.player;

import java.util.ArrayList;

/**
 * ArrayOfPlayers Set at the start of game to initialise the group of players in the game. 
 * All edits of player attributes has to be done in this array of players
 * 
 * @author abelwong2017
 * @version 1.0
 */
public class ArrayOfPlayers {

    private ArrayList<Player> players = new ArrayList<>();


    public void addPlayer(Player player){
        players.add(player);
    }

    public Player getPlayerByIndex(int index) {
        return this.players.get(index);
    }

    public int getNumberOfPlayers() {
        return this.players.size();
    }

    //ID 0 will always be human
    public ArrayList<Player> getArrayOfPlayers() {
        return this.players;
    }

    public void updatePlayerStates(ArrayList<Player> players) {
        this.players = new ArrayList<>(players);
    }

}