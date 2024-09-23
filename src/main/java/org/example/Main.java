package org.example;


import java.util.ArrayList;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        System.out.print("Adventure Game!");
    }

    ArrayList<Main.Card> adventureDeck = new ArrayList<Main.Card>();
    ArrayList<Main.Card> eventDeck = new ArrayList<Main.Card>();

    final int numberTypesOfFoes = 10;

    public ArrayList<Main.Card> getAdventureDeck() {
        return adventureDeck;
    }

    public ArrayList<Main.Card> getEventDeck() {
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

    private int GetNumberOfType(String type, ArrayList<Main.Card> deck) {
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


    public class Card {
        int cardValue;
        String type;

        public Card() {
            new Main.Card(0, "");
        }

        public Card(int value, String t) {
            cardValue = value;
            type = t;
        }

        public Main.Card DrawCard() {
            return new Main.Card();
        }

        public int GetFoeCardValue() {
            return cardValue;
        }

        public String GetCardType() {
            return type;
        }

    }


    public void InitializeAdventureDeck() {
        Main.Card newCard;
        int[] value = {5, 10, 15, 20, 25, 30, 35, 40, 50, 70};
        int[] numberFoes = {8, 7, 8, 7, 7, 4, 4, 2, 2, 1}; // of certain value
        int[] wValue = {5, 10, 10, 15, 20, 30};
        String[] wType = {"D", "H", "S", "B", "L", "E"};
        int[] numberWeapons = {6, 12, 16, 8, 6, 2}; // of certain value
        int numberWeaponTypes = 6;
        int count = 0;
        for (int i = 0; i < numberTypesOfFoes; i++) {
            for (int j = 0; j < numberFoes[i]; j++) {
                newCard = new Main.Card(value[i], "F");
                adventureDeck.add(count, newCard);
                count++;
            }
        }

        for (int i = 0; i < numberWeaponTypes; i++) {
            for (int j = 0; j < numberWeapons[i]; j++) {
                newCard = new Main.Card(wValue[i], wType[i]);
                System.out.println(wType[i] + wValue[i]);
                adventureDeck.add(count, newCard);
                count++;
            }
        }

    }

    public void InitializeEventDeck() {
        Main.Card newCard;
        int[] value = {2, 3, 4, 5};
        int[] numberQuests = {3, 4, 3, 2}; // of certain value
        int numberTypesOfQuests = 4;
        int count = 0;

        for (int i = 0; i < numberTypesOfQuests; i++) {
            for (int j = 0; j < numberQuests[i]; j++) {
                newCard = new Main.Card(value[i], "Q");
                eventDeck.add(count, newCard);
                count++;
            }
        }

        int[] eValue = {1, 2, 3}; // of certain value
        int[] numberEvents = {1, 2, 2}; // of certain value
        int numberTypesOfEvents = 3;

        for (int i = 0; i < numberTypesOfEvents; i++) {
            for (int j = 0; j < numberEvents[i]; j++) {
                newCard = new Main.Card(eValue[i], "E");
                eventDeck.add(count, newCard);
                count++;
            }
        }
    }


}