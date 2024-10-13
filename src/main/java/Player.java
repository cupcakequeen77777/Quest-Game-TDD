public class Player {
    int playerNumber;
    Deck hand;
    final int MAX_CARDS = 12;
    int shields;
    boolean sponsor = false;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new Deck(MAX_CARDS);
        shields = 0;
    }

    public boolean hasWon() {
        return shields >= 7;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public void plague(){
        shields = shields - 2;
        if(shields<0){
            shields = 0;
        }
    }

    // TODO: add card to deck
    public void addCard(Card card){
        hand.add(card);
    }

    public Card drawCard(){
        return hand.drawCard();
    }

    public Card removeCard(int index){
        return hand.removeCard(index);
    }

    public int numberToTrim(){
        if(hand.size() > MAX_CARDS){
            return hand.size() - MAX_CARDS;
        }
        return 0;
    }

    public int countFoes(){
        int foeCounter = 0;
        return foeCounter;
    }

    public String toString(){
        return playerNumber + "";
    }


}
