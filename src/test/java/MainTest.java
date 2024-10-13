

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MainTest {
    static Main game;

    @AfterEach
    void tearDown() {
        game = null;
    }

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
            assertEquals(value[i], deck.getCard(i).getValue());
            assertEquals("F", deck.getCard(i).GetType());
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
            assertEquals(wValue[i - 50], deck.getCard(i).getValue());
            assertEquals(wType[i - 50], deck.getCard(i).GetType());
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
            assertEquals(qValue[i], deck.getCard(i).getValue());
            assertEquals("Q", deck.getCard(i).GetType());
        }

        for (int i = numberQuests; i < deck.size(); i++) {
            System.out.println(deck.getCard(i).GetType() + deck.getCard(i).getValue());
            assertEquals(eValue[i - numberQuests], deck.getCard(i).getValue());
            assertEquals("E", deck.getCard(i).GetType());
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
    @DisplayName("The game indicates whose turn it is and displays this player’s hand")
    void RESP_05_test_02() {
        game.distributeCards();
        rigInitialHands();
        game.playerTurn = 0;

        Card quest = new Card(2, "E");
        StringWriter output = new StringWriter();
        game.startTurn(new PrintWriter(output), quest);
        assertEquals("Current player: 1", output.toString());

        output = new StringWriter();
        game.displayHand(new PrintWriter(output), game.playerTurn);
        assertNotNull(output.toString());
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
        StringWriter output = new StringWriter();
        game.startTurn(new PrintWriter(output), quest);
        assertEquals(quest, game.questCard);
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

        input = "y\n";
        output = new StringWriter();
        game.requestSponsorship(new Scanner(input), new PrintWriter(output), game.playerTurn);
        game.playerTurn = game.nextPlayer();
        assertEquals("Player 1 do you want to sponsor (y/N): ", output.toString());
        assertTrue(game.players.get(0).sponsor);

    }

    @Test
    @DisplayName("Game handles the quest sponsorship, everyone declines")
    void RESP_08_test_01() {
        game.playerTurn = 0;

        String input = "n\n";
        StringWriter output = new StringWriter();
        game.requestSponsorship(new Scanner(input), new PrintWriter(output), game.playerTurn);
        game.playerTurn = game.nextPlayer();
        assertEquals("Player 1 do you want to sponsor (y/N): ", output.toString());
        assertFalse(game.players.get(0).sponsor);

        input = "n\n";
        output = new StringWriter();
        game.requestSponsorship(new Scanner(input), new PrintWriter(output), game.playerTurn);
        game.playerTurn = game.nextPlayer();
        assertEquals("Player 2 do you want to sponsor (y/N): ", output.toString());
        assertFalse(game.players.get(1).sponsor);

        input = "n\n";
        output = new StringWriter();
        game.requestSponsorship(new Scanner(input), new PrintWriter(output), game.playerTurn);
        game.playerTurn = game.nextPlayer();
        assertEquals("Player 3 do you want to sponsor (y/N): ", output.toString());
        assertFalse(game.players.get(2).sponsor);

        input = "y\n";
        output = new StringWriter();
        game.requestSponsorship(new Scanner(input), new PrintWriter(output), game.playerTurn);
        game.playerTurn = game.nextPlayer();
        assertEquals("Player 4 do you want to sponsor (y/N): ", output.toString());
        assertTrue(game.players.get(3).sponsor);

        assertNull(game.quest);

    }


    @ParameterizedTest
    @ValueSource(ints = {0, 2, 1, 3}) // six numbers
    @DisplayName("The game computes n, the number of cards to discard by that player")
    void RESP_09_test_01(int numberCardsToDraw) {
        game.distributeCards();
        for (int i = 0; i < numberCardsToDraw; i++) {
            game.players.get(0).addCard(game.drawAdventureCard());
        }
        System.out.println(game.numberCardsToTrim());
        assertEquals(numberCardsToDraw, game.numberCardsToTrim().get(0));
    }

    @Test
    @DisplayName("The game displays the hand of the player and prompts the player for the position of the next card to delete")
    void RESP_09_test_02() {
        game.distributeCards();
        int numberCardsToDraw = 2;
        for (int i = 0; i < numberCardsToDraw; i++) {
            game.players.get(0).addCard(game.drawAdventureCard());
        }
    }

    @Test
    @DisplayName("The player enters a valid position")
    void RESP_09_test_03() {
        String input = "6";
        StringWriter output = new StringWriter();
        game.PromptInput(new Scanner(input), new PrintWriter(output), "Enter card index of card to discard: ");
        int index = game.readCardInput(input);
        assertEquals(6, index);
    }

    @Test
    @DisplayName("The game deletes the card from the player’s hand and displays the trimmed hand")
    void RESP_09_test_04() {
        int cardIndex = 3;
        int playerIndex = 0;
        game.distributeCards();
        rigInitialHands();
        Card card = new Card(5, "F");
        game.players.get(playerIndex).addCard(card);

        game.trimPlayerCard(cardIndex, playerIndex);
        assertEquals(12, game.players.get(playerIndex).hand.size());

        StringWriter output = new StringWriter();
        game.displayHand(new PrintWriter(output), playerIndex);
        assertEquals("[F5, F5, F15, D5, S10, S10, H10, H10, B15, B15, L20, F5]\n", output.toString());

    }


    @Test
    @DisplayName("Test single stage")
    void RESP_10_test_01() {
        // Create a sponsor, quest, and hand with valid cards
        Player sponsor = rigPLayer2Hands();
        sponsor.hand.sort();
        System.out.println(sponsor.hand);
        Quest quest = new Quest(3); // Assuming a 3-stage quest

        // Simulate user input to select the valid cards
        StringWriter output = new StringWriter();

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).thenReturn("0", "5", "quit");

        assertNotNull(game.buildStage(quest, sponsor, 0, mockScanner, new PrintWriter(output)));

    }

    @Test
    @DisplayName("Test valid quest setup with multiple stages")
    void RESP_10_test_02() {
        // Create a sponsor, quest, and hand with valid cards
        Player sponsor = rigPLayer2Hands();
        Quest quest = new Quest(4); // Assuming a 4-stage quest
        sponsor.hand.sort();

        // Simulate user input to select the valid cards
        StringWriter output = new StringWriter();

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).
                thenReturn("0", "6", "quit").
                thenReturn("1", "4", "quit").
                thenReturn("1", "2", "3", "quit").
                thenReturn("1", "2", "quit");

        game.sponsorSetsUpQuest(sponsor, quest, mockScanner, new PrintWriter(output));

        assertEquals("""
                -------------------
                Quest
                Stage 1: 15
                F5 H10\s
                Stage 2: 25
                F15 S10\s
                Stage 3: 35
                F15 D5 B15\s
                Stage 4: 55
                F40 B15\s
                -------------------\n""", quest.toString());

        // Assert that the quest is valid
        assertEquals(4, quest.stages.size());
        assertTrue(quest.isStageValid(0));
        assertTrue(quest.isStageValid(1));
        assertTrue(quest.isStageValid(2));
        assertTrue(quest.isStageValid(3));

        // Assert stage values and card selections
        assertEquals(15, quest.stages.get(0).getValue());
        assertEquals(25, quest.stages.get(1).getValue());
        assertEquals(35, quest.stages.get(2).getValue());
        assertEquals(55, quest.stages.get(3).getValue());
    }


    void rigInitialHands() {
        Player p1 = game.players.get(0);
        Player p2 = game.players.get(1);
        Player p3 = game.players.get(2);
        Player p4 = game.players.get(3);

        for (int i = 0; i < 12; i++) {
            game.adventureDeck.add(p1.drawCard());
            game.adventureDeck.add(game.players.get(1).drawCard());
            game.adventureDeck.add(game.players.get(2).drawCard());
            game.adventureDeck.add(game.players.get(3).drawCard());
        }

        int[] values1 = {5, 5, 15, 15, 5, 10, 10, 10, 10, 15, 15, 20};
        String[] types1 = {"F", "F", "F", "F", "D", "S", "S", "H", "H", "B", "B", "L"};
        int[] values2 = {5, 5, 15, 15, 40, 5, 10, 10, 10, 15, 15, 30};
        String[] types2 = {"F", "F", "F", "F", "F", "D", "S", "H", "H", "B", "B", "E"};
        int[] values3 = {5, 5, 5, 15, 5, 10, 10, 10, 10, 10, 15, 20};
        String[] types3 = {"F", "F", "F", "F", "D", "S", "S", "S", "H", "H", "B", "L"};
        int[] values4 = {5, 15, 15, 40, 5, 5, 10, 10, 10, 15, 20, 30};
        String[] types4 = {"F", "F", "F", "F", "D", "D", "S", "H", "H", "B", "L", "E"};
        for (int i = 0; i < values1.length; i++) {
            p1.addCard(game.adventureDeck.removeCard(new Card(values1[i], types1[i])));
            p2.addCard(game.adventureDeck.removeCard(new Card(values2[i], types2[i])));
            p3.addCard(game.adventureDeck.removeCard(new Card(values3[i], types3[i])));
            p4.addCard(game.adventureDeck.removeCard(new Card(values4[i], types4[i])));
        }
    }

    Player rigPLayer2Hands() {
        Player p = game.players.get(0);

        int[] values = {5, 5, 15, 15, 40, 5, 10, 10, 10, 15, 15, 30};
        String[] types = {"F", "F", "F", "F", "F", "D", "S", "H", "H", "B", "B", "E"};

        for (int i = 0; i < values.length; i++) {
            p.addCard(new Card(values[i], types[i]));
        }
        return p;
    }

    @Test
    @DisplayName("Game determines and displays eligible participants for each stage")
    void RESP_11_test_01() {
        game.distributeCards();
        rigInitialHands();
        // Create a sponsor, quest, and hand with valid cards
        Player sponsor = game.players.get(1);
        Quest quest = new Quest(4); // Assuming a 4-stage quest
        sponsor.hand.sort();

        // Simulate user input to select the valid cards
        StringWriter output = new StringWriter();

        Scanner mockScanner = mock(Scanner.class);
        when(mockScanner.nextLine()).
                thenReturn("0", "6", "quit").
                thenReturn("1", "4", "quit").
                thenReturn("1", "2", "3", "quit").
                thenReturn("1", "2", "quit");

        game.sponsorSetsUpQuest(sponsor, quest, mockScanner, new PrintWriter(output));

        output = new StringWriter();

        game.eligibleParticipants(new PrintWriter(output));

        assertEquals("[1, 3, 4]", output.toString());
        
    }


}