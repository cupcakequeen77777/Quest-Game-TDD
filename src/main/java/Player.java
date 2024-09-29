import java.util.ArrayList;

public class Player {
    int playerNumber;
    ArrayList<Card> hand;
    final int MAX_CARDS = 12;
    int shields;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new ArrayList<>(MAX_CARDS);
        shields = 0;
    }


}
