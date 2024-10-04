import java.util.ArrayList;
import java.util.Objects;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.print("Adventure Game!");
    }

    ArrayList<Card> adventureDeck = new ArrayList<>();
    ArrayList<Card> eventDeck = new ArrayList<>();

    // TODO: change in refactor to array list
    Player p1 = new Player(1);
    Player p2 = new Player(2);
    Player p3 = new Player(3);
    Player p4 = new Player(4);

    final int numberTypesOfFoes = 10;
    final int numberPlayers = 4;
    int playerTurn = 0;

    public ArrayList<Card> getAdventureDeck() {
        return adventureDeck;
    }

    public ArrayList<Card> getEventDeck() {
        return eventDeck;
    }

    public int GetAdventureDeckSize() {
        return adventureDeck.size();
    }

    public int GetNumberFoes() {
        return GetNumberOfType("F", adventureDeck);
    }

    public int GetNumberWeapons() {
        int count = 0;
        for (Card card : adventureDeck) {
            if (!Objects.equals(card.GetCardType(), "F")) {
                count++;
            }
        }
        return count;
    }

    private int GetNumberOfType(String type, ArrayList<Card> deck) {
        int count = 0;
        for (Card card : deck) {
            if (Objects.equals(card.GetCardType(), type)) {
                count++;
            }
        }
        return count;
    }

    public int GetEventDeckSize() {
        return eventDeck.size();
    }

    public int GetNumberQuestCards() {
        return GetNumberOfType("Q", eventDeck);
    }

    public int GetNumberEventCards() {
        return GetNumberOfType("E", eventDeck);
    }

    public void InitializeAdventureDeck() {
        Card newCard;
        int[] value = {5, 10, 15, 20, 25, 30, 35, 40, 50, 70};
        int[] numberFoes = {8, 7, 8, 7, 7, 4, 4, 2, 2, 1}; // of certain value
        int[] wValue = {5, 10, 10, 15, 20, 30};
        String[] wType = {"D", "H", "S", "B", "L", "E"};
        int[] numberWeapons = {6, 12, 16, 8, 6, 2}; // of certain value
        int numberWeaponTypes = 6;
        int count = 0;
        for (int i = 0; i < numberTypesOfFoes; i++) {
            for (int j = 0; j < numberFoes[i]; j++) {
                newCard = new Card(value[i], "F");
                adventureDeck.add(count, newCard);
                count++;
            }
        }

        for (int i = 0; i < numberWeaponTypes; i++) {
            for (int j = 0; j < numberWeapons[i]; j++) {
                newCard = new Card(wValue[i], wType[i]);
                adventureDeck.add(count, newCard);
                count++;
            }
        }

    }

    public void InitializeEventDeck() {
        Card newCard;
        int[] value = {2, 3, 4, 5};
        int[] numberQuests = {3, 4, 3, 2}; // of certain value
        int numberTypesOfQuests = 4;
        int count = 0;

        for (int i = 0; i < numberTypesOfQuests; i++) {
            for (int j = 0; j < numberQuests[i]; j++) {
                newCard = new Card(value[i], "Q");
                eventDeck.add(count, newCard);
                count++;
            }
        }

        int[] eValue = {1, 2, 3}; // of certain value
        int[] numberEvents = {1, 2, 2}; // of certain value
        int numberTypesOfEvents = 3;

        for (int i = 0; i < numberTypesOfEvents; i++) {
            for (int j = 0; j < numberEvents[i]; j++) {
                newCard = new Card(eValue[i], "E");
                eventDeck.add(count, newCard);
                count++;
            }
        }
    }

    public void distributeCards() {
        Collections.shuffle(adventureDeck);
        int defaultNumCard = 12;
        for (int i = 0; i < defaultNumCard; i++) {
            p1.hand.add(drawAdventureCard());
            p2.hand.add(drawAdventureCard());
            p3.hand.add(drawAdventureCard());
            p4.hand.add(drawAdventureCard());
        }
        p1.hand.sort(new CardComparator());
        p2.hand.sort(new CardComparator());
        p3.hand.sort(new CardComparator());
        p4.hand.sort(new CardComparator());
    }

    public Card drawAdventureCard() {
        return adventureDeck.removeFirst();
    }

    public boolean checkForWinner() {
        return p1.hasWon() || p2.hasWon() || p3.hasWon() || p4.hasWon();
    }

    public ArrayList<String> getListOfWinners() {
        ArrayList<String> winners = new ArrayList<>();
        if (p1.hasWon()) {
            winners.add(p1.playerNumber + ""); // QUESTION: what is the winner IDs?
        }
        if (p2.hasWon()) {
            winners.add(p2.playerNumber + "");
        }
        if (p3.hasWon()) {
            winners.add(p3.playerNumber + "");
        }
        if (p4.hasWon()) {
            winners.add(p4.playerNumber + "");
        }
        return winners;
    }

    public String getWinners() {
        ArrayList<String> winners = getListOfWinners();
        StringBuilder winner = new StringBuilder();
        if (!winners.isEmpty()) {
            winner.append("End of game!\nWinners!\n");
            for (String s : winners) {
                winner.append(s).append(" ");
            }
        }
        return winner.toString();
    }

    public Card drawEventCard() {
        return null;
    }



        class CardComparator implements Comparator<Card> {
        // Overriding compare()method of Comparator
        @Override
        public int compare(Card c1, Card c2) {
            return c1.compare(c2);
        }
    }

}