package estimate.player;
import cards.*;
import java.util.*;
import java.lang.Math;

public class Comparator3 implements Comparator<Card>{

    @Override
    public int compare(Card firstCard, Card secondCard){
        // If computer is not the first player to play,
        //  if bids predicted is positive,
        //     1. Pick a card of the lead suit whose rank is higher than that is played. Pick the lowest
        //         rank card among your cards that can fulfill the condition.
        //     2. Pick a card of the TRUMP suit whose rank is higher than that is played.
        //     3. If there is no choice, pick the lowest rank card of the lead suit, followed by TRUMP suit.
        //     4. If no card of TRUMP/lead suit available, pick the lowest ranked card of another suit
        //     (in the ascending order).
        return 0;
    }
}