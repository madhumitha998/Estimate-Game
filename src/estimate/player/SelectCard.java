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
            int suitValue = suitArrayList.indexOf(c.getSuit().getName())*13;
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
            int suitValue = suitArrayList.indexOf(c.getSuit().getName())*13;
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

//    public Card pickBestCard1(PlayerHand playerhand, Suit trumpSuit, Suit leadSuit, Card HighestPlayedCard){
//        ArrayList<Card> cardArrayList = playerhand.getHand();
//
//        //List for suit
//        List<String> suitList = Arrays.asList( "Clubs", "Diamonds", "Hearts","Spades");
//        ArrayList<String> suitArrayList = new ArrayList<>();
//        suitArrayList.addAll(suitList);
//
//        //List for ranking
//        List<String> rankList = Arrays.asList( "Two", "Three", "Four","Five","Six","Seven",
//                "Eight","Nine","Ten","Jack","Queen","King","Ace");
//
//        //get Trump suit for round
//        //dummy value for now
//        String trumpSuitName = trumpSuit.getName();
//
//        //get Lead suit for round
//        //dummy value for now
//        String leadSuitName = leadSuit.getName();
//
//        //Rearrange suit order
//        suitArrayList.remove(trumpSuitName);
//        suitArrayList.remove(leadSuitName);
//        suitArrayList.add(leadSuitName);
//        if(!trumpSuitName.equals(leadSuitName)){
//            suitArrayList.add(trumpSuitName);
//        }
//
//        //get Card Suit, rank and cardValue
//        String highestCardSuitName = HighestPlayedCard.getSuit().getName();
//        String highestCardRankName = HighestPlayedCard.getRank().getName();
//        int highestCardRankValue = rankList.indexOf( cardRankName ) + 1;
//        int highestCardSuitValue = suitArrayList.indexOf(c.getSuit().getName())*13;
//        int highestCardCardValue = highestCardRankValue + highestCardSuitValue;
//
//        Card bestCard = null;
//        int bestCardValue = 0;
//
//        //create cardValue to Card Hashmap
//        HashMap<Integer, Card> cardValueToCard = new HashMap<Integer, Card>();
//        for (Card c : cardArrayList) {
//            String cardRankName = c.getRank().getName();
//            int rankValue = rankList.indexOf( cardRankName ) + 1;
//            int suitValue = suitArrayList.indexOf(c.getSuit().getName())*13;
//            int cardValue = rankValue + suitValue;
//            cardValueToCard.put(cardValue,c);
//        }
//
//        //if highestcard is trump suit
//        if(highestCardSuitName.equals(trumpSuitName)){
//            int smallestValue=0;
//            for (int cardValue : cardValueToCard.keySet()) {
//                if(cardValue>highestCardCardValue){
//
//                }
//            }
//        }
//        return bestCard;
//    }
}