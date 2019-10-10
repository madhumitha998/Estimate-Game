public class Player {
    private int id;
    private int position;
    private ArrayList<Card> hand;
    private int bid;
    private boolean isTrickWinner;
    private boolean isDealer;
    
    public Player(id,position){
        this.id=id;
        this.position=position;
    }

    //Position 
    public setPosition(int position){
        this.position=position;
    }

    public int getPosition(){
        return position;
    }

    //Hand
    public void addToHand(Card c){
        hand.add(c);
    }

    public ArrayList<Card> getHand(){
        return hand;
    }

    //Bid
    public void setBid(int bid){
        this.bid = bid;
    }

    public int getBid(){
        return bid;
    }

    //TrickWinner
    public void setTrickWinner(boolean value){
        isTrickWinner = value;
    }

    public boolean getTrickWinner(){
        return isTrickWinner;
    }

    //IsDealer
    public void setIsDealer(boolean value){
        isDealer = value;
    }

    public boolean getIsDealer(){
        return isDealer;
    }


}