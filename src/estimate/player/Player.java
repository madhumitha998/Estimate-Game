public class Player {
    private int id;
    private int position;
    private Hand hand = new Hand();
    private int bid;
    private boolean isTrickWinner;
    private boolean isDealer;
    
    public Player(int id, int position){
        this.id = id;
        this.position = position;
    }

    //Position 
    public void setPosition(int position){
        this.position=position;
    }

    public int getPlayerId() {
        return this.id;
    }

    public int getPosition(){
        return position;
    }

    //Hand
    //use hand.addCard() from Hand class to set the hand for a trick

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

    public boolean IsDealer(){
        return isDealer;
    }


}