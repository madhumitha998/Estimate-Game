package estimate.player;
import cards.*;
import java.util.*;
import java.lang.Math;

public class SelectCard {

    public pickSmallestCard(Playerhand playerhand, Suit trumpSuit){
        ArrayList<Card> cardArrayList = playerhand.getHand();
        
        //List 1
        List<String> suitRankingList = Arrays.asList( "Clubs", "Diamonds", "Hearts","Spades");
          

        //get Trump suit for round
        //dummy value for now
        String trumpSuitName = trumpSuit.getname();
        suitRanking.remove(trumpSuitName);
        suitRanking.add(trumpSuitName);

        //hashmap for rankValue
        final java.util.List VALUES_ACE_HIGH =
        Collections.unmodifiableList( 
            Arrays.asList( new Rank[] { TWO, THREE, FOUR, FIVE, SIX, SEVEN,
                                     EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE } ) );

        Card smallestCard;
        int smallestValue = 0;
        for (Card c : cardArrayList) {
            Rank cardRank = c.getRank();
            int rankValue = VALUES_ACE_HIGH.indexOf( cardRank )+1;
            int suitValue = suitRankingList.indexof(c.getSuit().getName())*10;
            int cardValue = rankValue + suitValue;
            if(smallestValue==0){
                smallestValue = cardValue;
                smallestCard = c;
            } else {
                if(cardValue < smallestValue){
                    smallestValue = cardValue;
                    smallestCard = c;
                }
            }
        }
        return smallestCard;
    }
}