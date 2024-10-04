

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    static Main game;

    @BeforeEach
    void initAll() {
        game = new Main();
        game.InitializeAdventureDeck();
        game.InitializeEventDeck();
    }


    @Test
    @DisplayName("Check adventure deck size is 100")
    void RESP_01_test_01() {
        int deckSize = game.GetAdventureDeckSize();
        assertEquals(100, deckSize);
    }

    @Test
    @DisplayName("Check event deck size is 17")
    void RESP_01_test_02() {
        int deckSize = game.GetEventDeckSize();
        assertEquals(17, deckSize);
    }

    @Test
    @DisplayName("Check there are 50 foe cards and 50 weapon cards")
    void RESP_01_test_03() {
        int numberFoes = game.GetNumberFoes();
        assertEquals(50, numberFoes);

        int numberWeapons = game.GetNumberWeapons();
        assertEquals(50, numberWeapons);
    }

    @Test
    @DisplayName("Check there is 12 quest cards and 5 event card")
    void RESP_01_test_04() {
        int numberQuestCards = game.GetNumberQuestCards();
        assertEquals(12, numberQuestCards);

        int numberEventCards = game.GetNumberEventCards();
        assertEquals(5, numberEventCards);
    }

    @Test
    @DisplayName("Check there are correct number of each value of foe cards")
    void RESP_01_test_05() {
        ArrayList<Card> deck = game.getAdventureDeck();
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
        ArrayList<Card> deck = game.getEventDeck();

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

    @Test
    @DisplayName("Distributes 12 adventure cards to each player and updates the deck")
    void RESP_02_test_01() {
        game.distributeCards();
        assertEquals(12, game.p1.hand.size());
        assertEquals(12, game.p2.hand.size());
        assertEquals(12, game.p3.hand.size());
        assertEquals(12, game.p4.hand.size());
        assertEquals(52, game.getAdventureDeck().size());
    }

    @Test
    @DisplayName("Checks that player hands are sorted")
    void RESP_02_test_02() {
        game.distributeCards();
        assertTrue(isSorted(game.p1.hand));
        assertTrue(isSorted(game.p2.hand));
        assertTrue(isSorted(game.p3.hand));
        assertTrue(isSorted(game.p4.hand));

    }


    private boolean isSorted(ArrayList<Card> deck) {
        for (int i = 0; i < deck.size() - 1; i++) {
            if (deck.get(i).compare(deck.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    @DisplayName("At the end of a turn, game determines if one or more players have 7 shields, when there is one winner")
    void RESP_03_test_01() {
        game.p1.shields = 7;
        game.p2.shields = 5;
        game.p3.shields = 2;
        game.p4.shields = 3;
        assertTrue(game.checkForWinner());
    }

    @Test
    @DisplayName("At the end of a turn, game determines if one or more players have 7 shields, when there are no winner yet")
    void RESP_03_test_02() {
        game.p1.shields = 6;
        game.p2.shields = 5;
        game.p3.shields = 2;
        game.p4.shields = 3;
        assertFalse(game.checkForWinner());
    }

    @Test
    @DisplayName("At the end of a turn, game determines if one or more players have 7 shields, when there are more then one winners")
    void RESP_03_test_03() {
        game.p1.shields = 7;
        game.p2.shields = 7;
        game.p3.shields = 2;
        game.p4.shields = 3;
        assertTrue(game.checkForWinner());
    }

    // QUESTION: What to do if you realize you need another test?

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, one winner")
    void RESP_04_test_01() {
        game.p1.shields = 7;
        assertEquals("End of game!\nWinners!\n1 ", game.getWinners());
    }

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, two winners")
    void RESP_04_test_02() {
        game.p3.shields = 7;
        game.p4.shields = 7;
        assertEquals("End of game!\nWinners!\n3 4 ", game.getWinners());
    }

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, three winners")
    void RESP_04_test_03() {
        game.p4.shields = 7;
        game.p2.shields = 7;
        game.p3.shields = 7;
        assertEquals("End of game!\nWinners!\n2 3 4 ", game.getWinners());
    }

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, four winners")
    void RESP_04_test_04() {
        game.p1.shields = 7;
        game.p2.shields = 7;
        game.p3.shields = 7;
        game.p4.shields = 7;
        assertEquals("End of game!\nWinners!\n1 2 3 4 ", game.getWinners());
    }

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, no winners")
    void RESP_04_test_05() {
        game.p1.shields = 0;
        game.p2.shields = 0;
        game.p3.shields = 0;
        game.p4.shields = 0;
        assertEquals("", game.getWinners());
    }

    @Test
    @DisplayName("Current player draws the next event card")
    void RESP_05_test_01() {
        game.playerTurn = 2;
        game.drawEventCard();
        assertEquals(16, game.eventDeck.size());
    }


}