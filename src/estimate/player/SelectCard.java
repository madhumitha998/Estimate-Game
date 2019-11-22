package estimate.player;

import cards.*;
import java.util.*;

/**
* The SelectCard class returns the best card to play when the computer is:
* <p>the first player with a positive bid</p>
* <p>the first player with a negative bid</p>
* <p>not the first player with a positive bid</p>
* <p>not the first player with a negative bid</p>
*
* @author  Madhumitha
* @version 1.0
*/
public class SelectCard {

    public SelectCard(){
        
    }

    public Card pickCardFirstPlayerPositive(PlayerHand playerhand, Suit trumpSuit){
        ArrayList<Card> cardArrayList = playerhand.getHand();
        
        cardArrayList.sort(new BidPosFirstCardComparator(trumpSuit));

        return cardArrayList.get(0);
    }

    public Card pickCardFirstPlayerNegative(PlayerHand playerhand, Suit trumpSuit){
        ArrayList<Card> cardArrayList = playerhand.getHand();
        
        cardArrayList.sort(new BidNegFirstCardComparator(trumpSuit));

        return cardArrayList.get(0);
    }

   public Card pickCardNextPlayerPositive(PlayerHand playerhand, Suit trumpSuit, Suit leadSuit, Card highestPlayedCard){
       ArrayList<Card> cardArrayList = playerhand.getHand();

       String trumpSuitName = trumpSuit.getName();
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

       //if highest played card is not trump suit->lead suit
       if(!highestPlayedCard.suitToString().equals(trumpSuitName)){
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

        //If highest played card is trumpsuit
       } else {
        for(Card myCard: cardArrayList){
            String mySuitName = myCard.suitToString();
            //leadSuit
            if(mySuitName.equals(trumpSuitName)){
                if(myCard.compareTo(highestPlayedCard)>0){
                    //arraylist of trumpsuit higher than that is played
                    trumpHigherCards.add(myCard);
                } else{
                    //arraylist of rest of trumpsuit cards
                    trumpRestCards.add(myCard);
                }
            } else if(mySuitName.equals(leadSuitName)){
                leadRestCards.add(myCard);
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
        if(!trumpHigherCards.isEmpty()){
            return trumpHigherCards.get(0);
        } else if(!leadRestCards.isEmpty()) {
            return leadRestCards.get(0);
        } else if(!trumpRestCards.isEmpty()) {
            return trumpRestCards.get(0);
        } else {
            return otherCards.get(0);
        }

       }
   }

   public Card pickCardNextPlayerNegative(PlayerHand playerhand, Suit trumpSuit, Suit leadSuit, Card highestPlayedCard){
    ArrayList<Card> cardArrayList = playerhand.getHand();

    String trumpSuitName = trumpSuit.getName();
    String leadSuitName = leadSuit.getName();

    //arraylist of leadsuit cards lower than that is played
    ArrayList<Card> leadLowerCards = new ArrayList<>();
    //arraylist of rest of leadsuit cards
    ArrayList<Card> leadRestCards = new ArrayList<>();
    //arraylist of trumpsuit cards whose rank is lower than played
    ArrayList<Card> trumpLowerCards = new ArrayList<>();
    //arraylist of rest of trumpsuit cards
    ArrayList<Card> trumpRestCards = new ArrayList<>();
    //other suit cards
    ArrayList<Card> otherCards = new ArrayList<>();

    //if highest played card is not trump suit->lead suit
    if(!highestPlayedCard.suitToString().equals(trumpSuitName)){
     for(Card myCard: cardArrayList){
         String mySuitName = myCard.suitToString();
         //leadSuit
         if(mySuitName.equals(leadSuitName)){
             if(myCard.compareTo(highestPlayedCard)<0){
                 //arraylist of leadsuit lower than that is played
                 leadLowerCards.add(myCard);
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
     Collections.sort(leadLowerCards);
     Collections.sort(leadRestCards);
     Collections.sort(trumpLowerCards);
     Collections.sort(trumpRestCards );
     otherCards.sort(new BidNegNextCardComparator(trumpSuit, leadSuit));

     //picking the best card
     if(!leadLowerCards.isEmpty()){
         return leadLowerCards.get(leadLowerCards.size()-1);
     } else if(!trumpRestCards.isEmpty()) {
         return trumpRestCards.get(trumpRestCards.size()-1);
     } else if(!leadRestCards.isEmpty()) {
         return leadRestCards.get(leadRestCards.size()-1);
     } else {
         return otherCards.get(0);
     }

     //If highest played card is trumpsuit
    } else {
     for(Card myCard: cardArrayList){
         String mySuitName = myCard.suitToString();
         //leadSuit
         if(mySuitName.equals(trumpSuitName)){
             if(myCard.compareTo(highestPlayedCard)<0){
                 //arraylist of trumpsuit lower than that is played
                 trumpLowerCards.add(myCard);
             } else{
                 //arraylist of rest of trumpsuit cards
                 trumpRestCards.add(myCard);
             }
         } else if(mySuitName.equals(leadSuitName)){
             leadRestCards.add(myCard);
         } else {
             otherCards.add(myCard);
         }  
     }
     //Sort all the arraylists
     Collections.sort(leadLowerCards);
     Collections.sort(leadRestCards);
     Collections.sort(trumpLowerCards);
     Collections.sort(trumpRestCards );
     otherCards.sort(new BidNegNextCardComparator(trumpSuit, leadSuit));

     //picking the best card
     if(!trumpLowerCards.isEmpty()){
        return trumpLowerCards.get(trumpLowerCards.size()-1);
    } else if(!trumpRestCards.isEmpty()) {
        return trumpRestCards.get(trumpRestCards.size()-1);
    } else if(!leadRestCards.isEmpty()) {
        return leadRestCards.get(leadRestCards.size()-1);
    } else {
        return otherCards.get(0);
    }

    }
}









}