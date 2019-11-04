package estimate.player;
import cards.*;
import java.util.*;
import java.lang.Math;

public class SelectCard {

    public SelectCard(){
        
    }

    public Card pickSmallestCard(PlayerHand playerhand, Suit trumpSuit){
        ArrayList<Card> cardArrayList = playerhand.getHand();
        
        //List for suit
        List<String> suitList = Arrays.asList( "Clubs", "Diamonds", "Hearts","Spades");
          

        //get Trump suit for round
        //dummy value for now
        String trumpSuitName = trumpSuit.getName();
        suitList.remove(trumpSuitName);
        suitList.add(trumpSuitName);

        //List for ranking
        List<String> rankList = Arrays.asList( "Two", "Three", "Four","Five","Six","Seven",
                "Eight","Nine","Ten","Jack","Queen","King","Ace");

        Card smallestCard = null;
        int smallestValue = 0;
        for (Card c : cardArrayList) {
            Rank cardRank = c.getRank();
            int rankValue = rankList.indexOf( cardRank )+1;
            int suitValue = suitList.indexOf(c.getSuit().getName())*10;
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