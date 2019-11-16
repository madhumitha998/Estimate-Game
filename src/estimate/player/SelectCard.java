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

   public Card pickBestCard1(PlayerHand playerhand, Suit trumpSuit, Suit leadSuit, Card highestPlayedCard){
       ArrayList<Card> cardArrayList = playerhand.getHand();

       //List for suit
       List<String> suitList = Arrays.asList( "Clubs", "Diamonds", "Hearts","Spades");
       ArrayList<String> suitArrayList = new ArrayList<>();
       suitArrayList.addAll(suitList);

       //List for ranking
       List<String> rankList = Arrays.asList( "Two", "Three", "Four","Five","Six","Seven",
               "Eight","Nine","Ten","Jack","Queen","King","Ace");

       //get Trump suit for round
       //dummy value for now
       String trumpSuitName = trumpSuit.getName();

       //get Lead suit for round
       //dummy value for now
       String leadSuitName = leadSuit.getName();

       //arraylist of leadsuit cards higher than that is played
       ArrayList<Card> leadHigherCards = new ArrayList<>();
       //arraylist of rest of leadsuit cards
       ArrayList<Card> leadRestCards = new ArrayList<>();
       //arraylist of trumpsuit cards whose rank is higher than played
       ArrayList<Card> trumpHigherCards = new ArrayList<>();
       //arraylist of rest of trumpsuit cards
       ArrayList<Card> trumpRestCards = new ArrayList<>();
       //other suit cards
       ArrayList<Card> otherCards = new ArrayList<>();

       for(Card myCard: cardArrayList){
            String mySuitName = myCard.suitToString();
            //leadSuit
            if(mySuitName.equals(leadSuitName)){
                if(myCard.compareTo(highestPlayedCard)>0){
                    //arraylist of leadsuit higher than that is played
                    leadHigherCards.add(myCard);
                } else{
                    //arraylist of rest of leadsuit cards
                    leadRestCards.add(myCard);
                }
            } else if(mySuitName.equals(trumpSuitName)){
                trumpRestCards.add(myCard);
            } else {
                otherCards.add(myCard);
            }  
       }
       //Sort all the arraylists
       Collections.sort(leadHigherCards);
       Collections.sort(leadRestCards);
       Collections.sort(trumpHigherCards);
       Collections.sort(trumpRestCards );
       Collections.sort(otherCards);

       //picking the best card
       if(!leadHigherCards.isEmpty()){
            return leadHigherCards.get(0);
       } else if(!trumpRestCards.isEmpty()) {
            return trumpRestCards.get(0);
       } else if(!leadRestCards.isEmpty()) {
            return leadRestCards.get(0);
       } else {
            return otherCards.get(0);
       }
       
       

   }
}