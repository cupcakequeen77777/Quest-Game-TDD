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

    public boolean isEmpty() {
        return deck.isEmpty();
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

    public Deck removeAll() {
        Deck d = new Deck(cardLimit);
        d.deck = deck;
        deck = new ArrayList<>();
        return d;
    }

    public void add(Card card) {
        deck.add(card);
    }

    public void addAll(Deck d) {
        deck = d.getDeck();
    }

    public Card drawCard() {
        return deck.removeFirst();
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    public void sort() {
        if(deck.size()>=2) {
            deck.sort(new CardComparator());
        }
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
