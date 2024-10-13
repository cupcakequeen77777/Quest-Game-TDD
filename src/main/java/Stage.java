import java.util.ArrayList;
import java.util.List;

public class Stage {
    Card foeCard;
    List<Card> weaponCards;
    int value;

    public Stage() {
        foeCard = null;
        weaponCards = new ArrayList<>();
        value = 0;
    }

    public void addCard(Card card) {
        if (isValidCard(card)) {
            if (foeCard == null && card.isFoe()) {
                foeCard = card;
            } else if (card.isWeapon()) {
                weaponCards.add(card);
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
        for (Card weapon : weaponCards) {
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
        for (Card weapon : weaponCards) {
            value += weapon.getValue();
        }
        return value;
    }

    public boolean isComplete(){
        return foeCard != null && !weaponCards.isEmpty();
    }


    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(value);
        str.append("\n").append(foeCard).append(" ");
        for (Card weaponCard : weaponCards) {
            str.append(weaponCard.toString()).append(" ");
        }
        return str.toString();
    }
}
