package estimate.gamelogic;

import java.util.ArrayList;
import java.lang.Comparable;
import cards.*;
/**
 * PlayerCardArray is an object made up of the playerId and the Card that a player plays
 * 
 * @author abelwong2017
 * @version 1.0
 */
public class PlayerCardArray implements Comparable<PlayerCardArray> {

    int playerId;
    Card playerCard;

    /**
     * Consists of a playerId and the Card the player played
     * @param playerId
     * @param playerCard
     */
    public PlayerCardArray(int playerId, Card playerCard) {
        this.playerId = playerId;
        this.playerCard = playerCard;
    }

    public int getPlayerId(){
        return this.playerId;
    }

    public Card getPlayerCard(){
        return this.playerCard;
    }

    /**
     * Compares two cards
     * a negative integer, zero, or a positive integer is this card is less than, equal to, or greater than the
     * referenced card.
     * @param o1
     * @return
     */
    @Override
    public int compareTo(PlayerCardArray o1) {
        return (this.playerCard.compareTo(o1.playerCard));
    }

    public String toString() {
        return String.format(" PlayerID: %d, Player's Card: %s %s \n", playerId,
                playerCard.rankToString(), playerCard.suitToString());
    }
}