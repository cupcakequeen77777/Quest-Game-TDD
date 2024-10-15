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

    public Card removeCard(Card x) {
        for (int i = 0; i < deck.size(); i++) {
            if(deck.get(i).equals(x)) {
                return deck.remove(i);
            }
        }
        return null;
    }

    public Card removeCard(int index) {
        return deck.remove(index);
    }

    public void removeAll() {
        deck = new ArrayList<>();
    }

    public void add(Card card) {
        deck.add(card);
    }

    public void add(Deck d) {
        deck.addAll(d.getDeck());
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

    @Override
    public String toString() {
        return deck.toString();
    }

    static class CardComparator implements Comparator<Card> {
        // Overriding compare()method of Comparator
        @Override
        public int compare(Card c1, Card c2) {
            return c1.compare(c2);
        }
    }

}
