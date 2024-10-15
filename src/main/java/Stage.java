import java.util.ArrayList;
import java.util.List;

public class Stage {
    Card foeCard;
    Deck weaponCards;
    int value;
    ArrayList<Player> participants;

    public Stage() {
        foeCard = null;
        weaponCards = new Deck(20);
        value = 0;
        participants = new ArrayList<>();
    }

    public void addCard(Card card) {
        if (isValidCard(card)) {
            if (foeCard == null && card.isFoe()) {
                foeCard = card;
            } else if (card.isWeapon()) {
                weaponCards.add(card);
                weaponCards.sort();
            } else {
                // Handle invalid card type
                throw new IllegalArgumentException("Invalid card type: " + card.type);
            }
            calculateValue();
        } else {
            // Handle invalid card (e.g., duplicate)
            throw new IllegalArgumentException("Invalid card: " + card);
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isValidCard(Card card) {
        // Check if the card is a foe or weapon
        if (!card.type.equals("F") && !card.isWeapon()) { // FIXME: weapons check in card class if weapon
            return false;
        }

        // Check if the card is a duplicate
        if (foeCard != null && card.equals(foeCard)) {
            return false;
        }
        for (Card weapon : weaponCards.getDeck()) {
            if (card.equals(weapon)) {
                return false;
            }
        }

        return true;
    }

    public int calculateValue() {
        if (foeCard != null) {
            value = foeCard.getValue();
        }
        for (Card weapon : weaponCards.getDeck()) {
            value += weapon.getValue();
        }
        return value;
    }

    public boolean isComplete() {
        return foeCard != null && !weaponCards.getDeck().isEmpty();
    }


    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(value);
        str.append("\n").append(foeCard).append(" ");
        for (Card weaponCard : weaponCards.getDeck()) {
            str.append(weaponCard.toString()).append(" ");
        }
        return str.toString();
    }
}
