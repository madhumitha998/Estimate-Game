package estimate.player;

import cards.*;
import java.util.*;
import java.lang.Math;

/**
* The Computer class can place bids and play cards
* 
*
* @author  Madhumitha
* @version 1.0
*/
public class Computer extends Player{

    public Computer(int id,int position){
        super(id,position);
    }

    public double percentOfTrumpAndHigher(PlayerHand myHand, Suit trumpSuit){
        List<String> namesList = Arrays.asList( "a", "q", "k","j","10","9");
        ArrayList<String> HIGH_VALUES = new ArrayList<>();
        HIGH_VALUES.addAll(namesList); 

        String trumpSuitName = trumpSuit.getName();
        int trumpAndHigherCardsCnt = 0;

        int totalCardsInHand=myHand.getNumberOfCards();
        for(int i=0;i<totalCardsInHand;i++){
            Card c = myHand.getCard(i);
            
            //check if trump suit 
            if(c.suitToString().equals(trumpSuitName)){
                trumpAndHigherCardsCnt+=1;
            }

            //check if card is High value 
            else if (HIGH_VALUES.contains(c.getRank().getSymbol()) ) {
                trumpAndHigherCardsCnt+=1;
            }

        }
        /*check if it gives exact percentage without rounding */
        return (double)trumpAndHigherCardsCnt/totalCardsInHand;
    }

    public int indexOfBid(double percentOfTrumpAndHigher, int numPossibleBids, int medianIndex){
        if(percentOfTrumpAndHigher<=0.25){
            return Math.max(0,medianIndex-2);
        } else if (percentOfTrumpAndHigher<=0.50){
            return Math.max(0,medianIndex-1);
        } else if (percentOfTrumpAndHigher<=0.75){
            return Math.min(numPossibleBids-1,medianIndex+1);
        } else {
            return Math.min(numPossibleBids-1,medianIndex+2);
        }
    }

    public void bidWinningTricks(int totTricksInRound, int sumOfBidsInTrick, Suit trumpSuit){
        ArrayList possibleBids = new ArrayList<>();
        //set possibleBids array
        for (int i=0;i<=totTricksInRound;i++){
                possibleBids.add(i,i);
        } 
        if(super.getPosition() == 3){
            System.out.println("\n"+super.getPlayerId()+" is last");
            int excludedValueIndex = totTricksInRound - sumOfBidsInTrick;
            if ( excludedValueIndex >= 0 ) {
                possibleBids.remove(excludedValueIndex);
            }

        }
        System.out.println("\nComputer " + super.getPlayerId() + " Possible Bids: " +possibleBids + "\n");

        int numPossibleBids = possibleBids.size();
        //medianIndex
        int medianIndex ;
        if(numPossibleBids%2==0){
            medianIndex = (numPossibleBids/2)-1;
        } else {
            medianIndex = numPossibleBids/2;
        }

        //countTrumpAndHigherCards
        double percentOfTrumpAndHigher = percentOfTrumpAndHigher(getHand(), trumpSuit);

        //index of bid
        int bidIndex= indexOfBid(percentOfTrumpAndHigher, numPossibleBids,medianIndex);

        //set bid for Computer
        int bid = (int)possibleBids.get(bidIndex);
        super.setBid(bid);

    }

    public Card playCard(Suit trumpSuit,Suit leadSuit, Card highestPlayedCard){
        int bid = getBid();
        PlayerHand playerHand = getHand();
        SelectCard selectCard = new SelectCard();
        //if computer is first player, position should be 0
        if(getPosition()==0){
            if(bid>0){
                return selectCard.pickLargestCard(playerHand,trumpSuit);
            } else {
                return selectCard.pickSmallestCard(playerHand,trumpSuit);
            }
        } else {
            if(bid>0){
                return selectCard.pickBestCard1(playerHand,trumpSuit,leadSuit,highestPlayedCard);
            } else {
                return selectCard.pickBestCard2(playerHand,trumpSuit,leadSuit,highestPlayedCard);
            }
        }
    }

}