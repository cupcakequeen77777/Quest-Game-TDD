import java.util.*;

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

    public boolean isEmpty() {
        return deck.isEmpty();
    }

    public Card getCard(int i) {
        return deck.get(i);
    }

    public Card removeCard(Card x) {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).equals(x)) {
                return deck.remove(i);
            }
        }
        return new Card(1000, "Q", true);
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
        deck.sort(new CardComparator());
    }

    public int size() {
        deck.removeAll(Arrays.asList("", null));
        return deck.size();
    }

    @Override
    public String toString() {
        return deck.toString();
    }

    static class CardComparator implements Comparator<Card> {
        @Override
        public int compare(Card c1, Card c2) {
            if (c1 == null) {
                return c2 == null ? 0 : 1; // Null is lower
            } else if (c2 == null) {
                return -1; // Non-null is higher
            } else {
                return c1.compare(c2);
            }
        }


    }

}
