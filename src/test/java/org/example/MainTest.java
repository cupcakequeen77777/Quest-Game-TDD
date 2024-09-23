package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {
    @Test
    @DisplayName("Check adventure deck size is 100")
    void RESP_01_test_01() {
        Main game = new Main();
        game.InitializeAdventureDeck();

        int deckSize = game.GetAdventureDeckSize();
        assertEquals(100, deckSize);
    }

    @Test
    @DisplayName("Check event deck size is 17")
    void RESP_01_test_02() {
        Main game = new Main();
        game.InitializeEventDeck();

        int deckSize = game.GetEventDeckSize();
        assertEquals(17, deckSize);
    }

    @Test
    @DisplayName("Check there are 50 foe cards and 50 weapon cards")
    void RESP_01_test_03() {
        Main game = new Main();
        game.InitializeAdventureDeck();

        int numberFoes = game.GetNumberFoes();
        assertEquals(50, numberFoes);

        int numberWeapons = game.GetNumberWeapons();
        assertEquals(50, numberWeapons);
    }

    @Test
    @DisplayName("Check there is 12 quest cards and 5 event card")
    void RESP_01_test_04() {
        Main game = new Main();
        game.InitializeEventDeck();

        int numberQuestCards = game.GetNumberQuestCards();
        assertEquals(50, numberQuestCards);

        int numberEventCards = game.GetNumberEventCards();
        assertEquals(50, numberEventCards);
    }

    @Test
    @DisplayName("Check there are correct number of each value of foe cards")
    void RESP_01_test_05() {
        Main game = new Main();
        game.InitializeAdventureDeck();
        ArrayList<Main.Card> deck = game.getAdventureDeck();
        int numberFoeCards = 50;
        int numberWeaponCards = 50;
        int[] value = {
                5, 5, 5, 5, 5, 5, 5, 5,
                10, 10, 10, 10, 10, 10, 10,
                15, 15, 15, 15, 15, 15, 15, 15,
                20, 20, 20, 20, 20, 20, 20,
                25, 25, 25, 25, 25, 25, 25,
                30, 30, 30, 30,
                35, 35, 35, 35,
                40, 40,
                50, 50,
                70};

        for (int i = 0; i < numberFoeCards; i++) {
            assertEquals(value[i], deck.get(i).GetFoeCardValue());
            assertEquals("F", deck.get(i).GetCardType());
        }

        int[] wValue = {
                5, 5, 5, 5, 5, 5,
                10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10,
                15, 15, 15, 15, 15, 15, 15, 15,
                20, 20, 20, 20, 20, 20,
                30, 30};

        String[] wType = {
                "D", "D", "D", "D", "D", "D",
                "H", "H", "H", "H", "H", "H", "H", "H", "H", "H", "H", "H",
                "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S", "S",
                "B", "B", "B", "B", "B", "B", "B", "B",
                "L", "L", "L", "L", "L", "L",
                "E", "E"};

        for (int i = numberFoeCards; i < numberFoeCards + numberWeaponCards; i++) {
            assertEquals(wValue[i - 50], deck.get(i).GetFoeCardValue());
            assertEquals(wType[i - 50], deck.get(i).GetCardType());
        }


    }

    @Test
    @DisplayName("Check there are correct number of each value of event cards")
    void RESP_01_test_06() {
        Main game = new Main();
        game.InitializeEventDeck();
        ArrayList<Main.Card> deck = game.getEventDeck();

        int[] qValue = {2, 2, 2,
                3, 3, 3, 3,
                4, 4, 4,
                5, 5};
        int numberQuests = qValue.length;
        int[] eValue = {1, 2, 2, 3, 3};

        for (int i = 0; i < numberQuests; i++) {
            assertEquals(qValue[i], deck.get(i).GetFoeCardValue());
            assertEquals("Q", deck.get(i).GetCardType());
        }

        for (int i = numberQuests; i < deck.size(); i++) {
            System.out.println(deck.get(i).GetCardType() + deck.get(i).GetFoeCardValue());
            assertEquals(eValue[i - numberQuests], deck.get(i).GetFoeCardValue());
            assertEquals("E", deck.get(i).GetCardType());
        }

    }


}