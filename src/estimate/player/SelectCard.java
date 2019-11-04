package estimate.player;
import cards.*;
import java.util.*;
import java.lang.Math;

public class SelectCard {

    public SelectCard(){
        
    }
    //This method returns the smallest Card in hand taking into account trump suit only 
    //Meant for when computer is first player
    public Card pickSmallestCard(PlayerHand playerhand, Suit trumpSuit){
        ArrayList<Card> cardArrayList = playerhand.getHand();
        
        //List for suit
        List<String> suitList = Arrays.asList( "Clubs", "Diamonds", "Hearts","Spades");
        ArrayList<String> suitArrayList = new ArrayList<>();
        suitArrayList.addAll(suitList);

        //get Trump suit for round
        //dummy value for now
        String trumpSuitName = trumpSuit.getName();
        suitArrayList.remove(trumpSuitName);
        suitArrayList.add(trumpSuitName);

        //List for ranking
        List<String> rankList = Arrays.asList( "Two", "Three", "Four","Five","Six","Seven",
                "Eight","Nine","Ten","Jack","Queen","King","Ace");

        Card smallestCard = null;
        int smallestValue = 0;
        for (Card c : cardArrayList) {
            String cardRankName = c.getRank().getName();
            int rankValue = rankList.indexOf( cardRankName ) + 1;
            int suitValue = suitArrayList.indexOf(c.getSuit().getName())*10;
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

    //This method returns the highest Card in hand excluding trump suit
    //Meant for when computer is first player
    //trump suit is only played after all options are exhausted
    public Card pickLargestCard(PlayerHand playerhand, Suit trumpSuit){
        ArrayList<Card> cardArrayList = playerhand.getHand();
        
        //List for suit
        List<String> suitList = Arrays.asList( "Clubs", "Diamonds", "Hearts","Spades");
        ArrayList<String> suitArrayList = new ArrayList<>();
        suitArrayList.addAll(suitList);

        //get Trump suit for round
        //dummy value for now
        String trumpSuitName = trumpSuit.getName();
        suitArrayList.remove(trumpSuitName);
        suitArrayList.add(0,trumpSuitName);

        //List for ranking
        List<String> rankList = Arrays.asList( "Two", "Three", "Four","Five","Six","Seven",
                "Eight","Nine","Ten","Jack","Queen","King","Ace");

        Card largestCard = null;
        int largestValue = 0;
        for (Card c : cardArrayList) {
            String cardRankName = c.getRank().getName();
            int rankValue = rankList.indexOf( cardRankName ) + 1;
            int suitValue = suitArrayList.indexOf(c.getSuit().getName())*10;
            int cardValue = rankValue + suitValue;
            if(largestValue==0){
                largestValue = cardValue;
                largestCard = c;
            } else {
                if(cardValue > largestValue){
                    largestValue = cardValue;
                    largestCard = c;
                }
            }
        }
        return largestCard;
    }
}