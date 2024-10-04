import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Deck {
    ArrayList<Card> deck;
    int cardLimit;

    public Deck(int cardLimit) {
        this.cardLimit = cardLimit;
        deck = new ArrayList<>(cardLimit);
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public int getCardLimit() {
        return cardLimit;
    }

    public Card getCard(int i) {
        return deck.get(i);
    }

    public void add(Card card) {
        deck.add(card);
    }

    public Card drawCard() {
        return deck.removeFirst();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void sort() {
        deck.sort(new CardComparator());
    }

    public int size() {
        return deck.size();
    }

    static class CardComparator implements Comparator<Card> {
        // Overriding compare()method of Comparator
        @Override
        public int compare(Card c1, Card c2) {
            return c1.compare(c2);
        }
    }

}
