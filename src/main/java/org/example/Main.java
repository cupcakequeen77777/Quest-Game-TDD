package org.example;


import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.print("Adventure Game!");
    }

    ArrayList<Card> adventureDeck = new ArrayList<>();
    ArrayList<Card> eventDeck = new ArrayList<>();

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
        return 0;
    }

    public int GetNumberWeapons() {
        return 0;
    }

    public int GetEventDeckSize() {
        return eventDeck.size();
    }

    public int GetNumberQuestCards() {
        return 0;
    }

    public int GetNumberEventCards() {
        return 0;
    }


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

        public Card DrawCard() {
            return new Card();
        }

        public int GetFoeCardValue() {
            return cardValue;
        }

        public String GetCardType() {
            return type;
        }

    }


    public void InitializeAdventureDeck() {


    }

    public void InitializeEventDeck() {

    }


}