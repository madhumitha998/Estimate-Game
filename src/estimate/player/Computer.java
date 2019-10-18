import java.util.ArrayList;

public class Computer extends Player{

    public Computer(int id,int position){
        super(id,position);
    }

    private double percentOfTrumpAndHigher(Hand myHand){
        List<String> namesList = Arrays.asList( "a", "q", "k","j","10","9");
        ArrayList<String> HIGH_VALUES = new ArrayList<>();
        HIGH_VALUES.addAll(namesList); 
        
        //Gets trump suit for current round
        //String trumpSuitName = gameLogic.trumpCard.suitToString().
        //but for now i will create a dummy value for testing
        String trumpSuitName = "Hearts";
        int trumpAndHigherCardsCnt = 0;

        int totalCardsInHand=myHand.getNumberOfCards();
        for(int i=0;i<totalCardsInHand;i++){
            Card c = myHand.getCard(i);
            
            //check if trump suit 
            if(c.suitToString()==trumpSuitName){
                trumpAndHigherCardsCnt+=1;
            }

            //check if card is High value 
            if (HIGH_VALUES.contains(c.getRank().getSymbol()) ) {
                trumpAndHigherCardsCnt+=1;
            }

        }
        //check how to give exact percentage without rounding
        return trumpAndHigherCardsCnt/totalCardsInHand;
    }

    public void bidWinningTricks(){
        int totTricksInRound;
        if(isDealer()){
            //possible bids
            //medianIndex
            //countTrumpAndHigherCards
        } else {
            totTricksInRound = //from somewhere
            if(totTricksInRound%2==0){
                medianIndex = totTricksInRound/2;
            }
            //possible bid : 0-
        }
    }

    public Card playCard(){

    }


}