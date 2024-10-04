import java.util.ArrayList;

public class Player {
    int playerNumber;
    Deck hand;
    final int MAX_CARDS = 12;
    int shields;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new Deck(MAX_CARDS);
        shields = 0;
    }

    public boolean hasWon() {
        return shields >= 7;
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


}
