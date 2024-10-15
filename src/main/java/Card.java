import java.util.Objects;

public class Card {
    int cardValue;
    String type;
    boolean weapon;

    public Card(int value, String t, boolean w) {
        cardValue = value;
        type = t;
        weapon = w;
    }

    public int getValue() {
        return cardValue;
    }

    public String GetType() {
        return type;
    }

    @Override
    public String toString() {
        if (isFoe() || !weapon) {
            if (type.equals("E")) {
                if (cardValue == 1) {
                    return "Plague";
                } else if (cardValue == 2) {
                    return "Queen’s favor";
                } else {
                    return "Prosperity";
                }
            }
            return type + cardValue;
        }
        switch (type) {
            case "D" -> {
                return "Dagger";
            }
            case "H" -> {
                return "Horse";
            }
            case "S" -> {
                return "Sword";
            }
            case "B" -> {
                return "Axe";
            }
            case "L" -> {
                return "Lance";
            }
            case "E" -> {
                return "Excalibur";
            }
        }

        return "";
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

    public boolean equals(Card card) {
        return cardValue == card.cardValue && type.equals(card.type);
    }

    public boolean isWeapon() {
        return !type.equals("F") && !type.equals("E") && !type.equals("Q");
    }

    public boolean isFoe() {
        return type.equals("F");
    }


}
