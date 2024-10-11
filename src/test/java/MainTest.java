

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    static Main game;

    @BeforeEach
    void initAll() {
        game = new Main();
        game.InitializeAdventureDeck();
        game.InitializeEventDeck();
        game.players.add(new Player(1));
        game.players.add(new Player(2));
        game.players.add(new Player(3));
        game.players.add(new Player(4));
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
        Deck deck = game.getAdventureDeck();
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
            assertEquals(value[i], deck.getCard(i).GetFoeCardValue());
            assertEquals("F", deck.getCard(i).GetCardType());
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
            assertEquals(wValue[i - 50], deck.getCard(i).GetFoeCardValue());
            assertEquals(wType[i - 50], deck.getCard(i).GetCardType());
        }


    }

    @Test
    @DisplayName("Check there are correct number of each value of event cards")
    void RESP_01_test_06() {
        Deck deck = game.getEventDeck();

        int[] qValue = {2, 2, 2,
                3, 3, 3, 3,
                4, 4, 4,
                5, 5};
        int numberQuests = qValue.length;
        int[] eValue = {1, 2, 2, 3, 3};

        for (int i = 0; i < numberQuests; i++) {
            assertEquals(qValue[i], deck.getCard(i).GetFoeCardValue());
            assertEquals("Q", deck.getCard(i).GetCardType());
        }

        for (int i = numberQuests; i < deck.size(); i++) {
            System.out.println(deck.getCard(i).GetCardType() + deck.getCard(i).GetFoeCardValue());
            assertEquals(eValue[i - numberQuests], deck.getCard(i).GetFoeCardValue());
            assertEquals("E", deck.getCard(i).GetCardType());
        }

    }

    @Test
    @DisplayName("Distributes 12 adventure cards to each player and updates the deck")
    void RESP_02_test_01() {
        game.distributeCards();
        for (Player player : game.players) {
            assertEquals(12, player.hand.size());
        }
        assertEquals(52, game.getAdventureDeck().size());
    }

    @Test
    @DisplayName("Checks that player hands are sorted")
    void RESP_02_test_02() {
        game.distributeCards();
        for (Player player : game.players) {
            assertTrue(isSorted(player.hand.getDeck()));
        }
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
        game.players.get(0).shields = 7;
        game.players.get(1).shields = 5;
        game.players.get(2).shields = 2;
        game.players.get(3).shields = 3;
        assertTrue(game.checkForWinner());
    }

    @Test
    @DisplayName("At the end of a turn, game determines if one or more players have 7 shields, when there are no winner yet")
    void RESP_03_test_02() {
        game.players.get(0).shields = 6;
        game.players.get(1).shields = 5;
        game.players.get(2).shields = 2;
        game.players.get(3).shields = 3;
        assertFalse(game.checkForWinner());
    }

    @Test
    @DisplayName("At the end of a turn, game determines if one or more players have 7 shields, when there are more then one winners")
    void RESP_03_test_03() {
        game.players.get(0).shields = 7;
        game.players.get(1).shields = 7;
        game.players.get(2).shields = 2;
        game.players.get(3).shields = 3;
        assertTrue(game.checkForWinner());
    }

    // QUESTION: What to do if you realize you need another test?

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, one winner")
    void RESP_04_test_01() {
        game.players.get(0).shields = 7;
        assertEquals("End of game!\nWinners!\n1 ", game.getWinners());
    }

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, two winners")
    void RESP_04_test_02() {
        game.players.get(2).shields = 7;
        game.players.get(3).shields = 7;
        assertEquals("End of game!\nWinners!\n3 4 ", game.getWinners());
    }

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, three winners")
    void RESP_04_test_03() {
        game.players.get(3).shields = 7;
        game.players.get(2).shields = 7;
        game.players.get(1).shields = 7;
        assertEquals("End of game!\nWinners!\n2 3 4 ", game.getWinners());
    }

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, four winners")
    void RESP_04_test_04() {
        game.players.get(0).shields = 7;
        game.players.get(1).shields = 7;
        game.players.get(2).shields = 7;
        game.players.get(3).shields = 7;
        assertEquals("End of game!\nWinners!\n1 2 3 4 ", game.getWinners());
    }

    @Test
    @DisplayName("Game displays the id of each winner and then terminates, no winners")
    void RESP_04_test_05() {
        game.players.get(0).shields = 0;
        game.players.get(1).shields = 0;
        game.players.get(2).shields = 0;
        game.players.get(3).shields = 0;
        assertEquals("", game.getWinners());
    }

    @Test
    @DisplayName("Current player draws the next event card")
    void RESP_05_test_01() {
        game.playerTurn = 1;
        game.drawEventCard();
        assertEquals(16, game.eventDeck.size());
    }

    @Test
    @DisplayName("Game carries out the action(s) triggered by an E card, plague")
    void RESP_06_test_01() {
        game.playerTurn = 1;
        game.players.get(1).setShields(4);
        Card plagueCard = new Card(1, "E");
        game.resolveEvent(plagueCard);
        assertEquals(2, game.players.get(1).shields);
    }

    @Test
    @DisplayName("Game carries out the action(s) triggered by an E card, Queen’s favor card")
    void RESP_06_test_02() {
        game.playerTurn = 1;
        game.players.get(1).setShields(4);
        Card queenCard = new Card(2, "E");
        game.resolveEvent(queenCard);
        assertEquals(2, game.players.get(1).hand.size());
    }

    @Test
    @DisplayName("Game carries out the action(s) triggered by an E card, Prosperity card")
    void RESP_06_test_03() {
        game.playerTurn = 1;
        game.players.get(1).setShields(4);
        Card prosperity = new Card(3, "E");
        game.resolveEvent(prosperity);
        assertEquals(2, game.players.get(0).hand.size());
        assertEquals(2, game.players.get(1).hand.size());
        assertEquals(2, game.players.get(2).hand.size());
        assertEquals(2, game.players.get(3).hand.size());
    }


    @Test
    @DisplayName("Game handles the drawing of a Q card")
    void RESP_07_test_01() {
        game.playerTurn = 1;
        Card quest = new Card(1, "Q");
        game.startTurn(quest);
        assertEquals(quest, game.quest);
    }

    @Test
    @DisplayName("Game handles quest sponsorship, first player accepts")
    void RESP_07_test_02() {
        game.playerTurn = 0;
        String input = "y\n";
        StringWriter output = new StringWriter();
        game.requestSponsorship(new Scanner(input), new PrintWriter(output), game.playerTurn);
        game.playerTurn = game.nextPlayer();
        assertEquals("Player 1 do you want to sponsor (y/N): ", output.toString());
        assertTrue(game.players.get(0).sponsor);
    }

    @Test
    @DisplayName("Game handles quest sponsorship, second player accepts")
    void RESP_07_test_03() {
        game.playerTurn = 3;

        String input = "n\n";
        StringWriter output = new StringWriter();
        game.requestSponsorship(new Scanner(input), new PrintWriter(output), game.playerTurn);
        game.playerTurn = game.nextPlayer();
        assertEquals("Player 4 do you want to sponsor (y/N): ", output.toString());
        assertFalse(game.players.get(3).sponsor);

        input = "n\n";
        output = new StringWriter();
        game.requestSponsorship(new Scanner(input), new PrintWriter(output), game.playerTurn);
        game.playerTurn = game.nextPlayer();
        assertEquals("Player 1 do you want to sponsor (y/N): ", output.toString());
        assertTrue(game.players.get(0).sponsor);

    }

    @Test

        simulatedInput = "y\n"; // Replace with the expected input for requestSponsorship
        inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inputStream);
        assertTrue(game.requestSponsorship(game.nextPlayer()));
        System.setIn(System.in); // Restore System.in to its original state


    }
}