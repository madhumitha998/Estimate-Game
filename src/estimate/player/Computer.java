/**
* The Computer class can place bids and play cards
* 
*
* @author  Madhumitha
* @version 1.2
* @since   2019-10-19 
*/
package estimate.player;
import cards.*;
import java.util.*;
import java.lang.Math;
import estimate.player.SelectCard;

public class Computer extends Player{

    public Computer(int id,int position){
        super(id,position);
    }

    public double percentOfTrumpAndHigher(PlayerHand myHand){
        List<String> namesList = Arrays.asList( "a", "q", "k","j","10","9");
        ArrayList<String> HIGH_VALUES = new ArrayList<>();
        HIGH_VALUES.addAll(namesList); 
        
        //Gets trump suit for current round
        /* String trumpSuitName = gameLogic.trumpCard.suitToString(). */
        //but for now i will create a dummy value for testing
        String trumpSuitName = "Hearts";
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

    public void bidWinningTricks(){
        int totTricksInRound;
        /* get totTricksInRound from Round.java method or somewhere */
        //dummy value for now
        totTricksInRound = 5;

        ArrayList possibleBids = new ArrayList<>();
        //set possibleBids array
        for (int i=0;i<=totTricksInRound;i++){
                possibleBids.add(i,i);
        } 
        if(isDealer()){
            /*get sumOfBidsInTrick*/
            //dummy value for now
            int sumOfBidsInTrick = 4;
            int excludedValueIndex = totTricksInRound - sumOfBidsInTrick;
            possibleBids.remove(excludedValueIndex);
        } 

        int numPossibleBids = possibleBids.size();
        //medianIndex
        int medianIndex ;
        if(numPossibleBids%2==0){
            medianIndex = (numPossibleBids/2)-1;
        } else {
            medianIndex = numPossibleBids/2;
        }

        //countTrumpAndHigherCards
        double percentOfTrumpAndHigher = percentOfTrumpAndHigher(getHand());

        //index of bid
        int bidIndex= indexOfBid(percentOfTrumpAndHigher, numPossibleBids,medianIndex);

        //set bid for Computer
        int bid = (int)possibleBids.get(bidIndex);
        super.setBid(bid);

    }

    public Card playCard(){
        int bid = getBid();
        PlayerHand hand = getHand();
        //if computer is first player, position should be 1
        if(getPosition()==1){
            if(bid>0){

            } else {
                
            }
        } 
        
        return null;
    }

    //tester function
    public Card selectCard(){
        SelectCard selectcard = new SelectCard();
        selectcard.pickSmallestCard(getHand(), Suit.SPADES);
    }
}