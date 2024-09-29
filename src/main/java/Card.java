

public class Card {
    int cardValue;
    String type;

    public Card() {
        new Card(0, "");
    }

    public Card(int value, String t) {
        cardValue = value;
        type = t;
    }

    public int GetFoeCardValue() {
        return cardValue;
    }

    public String GetCardType() {
        return type;
    }

    @Override
    public String toString() {
        return type + cardValue;
    }

    // TODO: Displaying the hand of a player means listing foes first in increasing order, then weapons, also in increasing order, with swords before horses.
    public int compare(Card card) {
        if (type.equals("F") && !card.type.equals("F")) {
            return -1;
        } else if (card.type.equals("F") && !type.equals("F")) {
            return 1;
        }
        return Integer.compare(cardValue, card.cardValue);
    }


}
