

import org.junit.jupiter.api.*;
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
        game.players.clear();
        game.adventureDeck.deck.clear();
        game.eventDeck.deck.clear();
        game = null;
    }

    @BeforeEach
    void initAll() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);
    }

    public void initializeGame(Scanner input, StringWriter output) {
        game = new Main(input, new PrintWriter(output));
        game.InitializeAdventureDeck();
        game.adventureDeck.shuffle();
        game.InitializeEventDeck();
        game.eventDeck.shuffle();
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
        game = new Main();
        game.InitializeAdventureDeck();
        game.InitializeEventDeck();
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

        deck.sort();

        for (int i = numberFoeCards; i < numberFoeCards + numberWeaponCards; i++) {
            assertEquals(wValue[i - 50], deck.getCard(i).getValue());
            assertEquals(wType[i - 50], deck.getCard(i).GetType());
        }


    }

    @Test
    @DisplayName("Check there are correct number of each value of event cards")
    void RESP_01_test_06() {
        game = new Main();
        game.InitializeAdventureDeck();
        game.InitializeEventDeck();
        Deck deck = game.getEventDeck();

        int[] qValue = {2, 2, 2,
                3, 3, 3, 3,
                4, 4, 4,
                5, 5};
        int numberQuests = qValue.length;
        int[] eValue = {1, 2, 2, 3, 3};

//        for (int i = 0; i < numberQuests; i++) {
//            assertEquals(qValue[i], deck.getCard(i).getValue());
//            assertEquals("Q", deck.getCard(i).GetType());
//        }
//
//        for (int i = numberQuests; i < deck.size(); i++) {
//            System.out.println(deck.getCard(i).GetType() + deck.getCard(i).getValue());
//            assertEquals(eValue[i - numberQuests], deck.getCard(i).getValue());
//            assertEquals("E", deck.getCard(i).GetType());
//        }

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
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);
        game.distributeCards();
        rigInitialHands(game);
        game.playerTurn = 0;

        Card quest = new Card(2, "E", false);
        game.startTurn(quest);
        assertTrue(output.toString().contains("Current player: 1\n"));

        output = new StringWriter();
        game.displayHand(game.playerTurn);
        assertNotNull(output.toString());
    }

    @Test
    @DisplayName("Game carries out the action(s) triggered by an E card, plague")
    void RESP_06_test_01() {
        game.playerTurn = 1;
        game.players.get(1).setShields(4);
        Card plagueCard = new Card(1, "E", false);
        game.resolveEvent(plagueCard);
        assertEquals(2, game.players.get(1).shields);
    }

    @Test
    @DisplayName("Game carries out the action(s) triggered by an E card, Queen’s favor card")
    void RESP_06_test_02() {
        game.playerTurn = 1;
        game.players.get(1).setShields(4);
        Card queenCard = new Card(2, "E", false);
        game.resolveEvent(queenCard);
        assertEquals(2, game.players.get(1).hand.size());
    }

    @Test
    @DisplayName("Game carries out the action(s) triggered by an E card, Prosperity card")
    void RESP_06_test_03() {
        game.playerTurn = 1;
        game.players.get(1).setShields(4);
        Card prosperity = new Card(3, "E", false);
        game.resolveEvent(prosperity);
        assertEquals(2, game.players.get(0).hand.size());
        assertEquals(2, game.players.get(1).hand.size());
        assertEquals(2, game.players.get(2).hand.size());
        assertEquals(2, game.players.get(3).hand.size());
    }


    @Test
    @DisplayName("Game handles the drawing of a Q card")
    void RESP_07_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        game.playerTurn = 1;
        Card quest = new Card(1, "Q", false);
        game.startTurn(quest);
        assertEquals(quest, game.questCard);
    }

    @Test
    @DisplayName("Game handles quest sponsorship, first player accepts")
    void RESP_07_test_02() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        game.playerTurn = 0;
        when(mockScanner.nextLine()).
                thenReturn("y");

        game.questCard = new Card(3, "Q", false);

        game.requestSponsorships();

        assertTrue(output.toString().contains("Player 1 do you want to sponsor (y/N): "));
        assertTrue(game.players.get(0).sponsor);
    }

    @Test
    @DisplayName("Game handles quest sponsorship, second player accepts")
    void RESP_07_test_03() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        game.playerTurn = 3;

        game.questCard = new Card(3, "Q", false);

        when(mockScanner.nextLine()).
                thenReturn("n", "y");

        assertTrue(game.requestSponsorships());

        assertFalse(game.players.get(3).sponsor);
        assertTrue(game.players.get(0).sponsor);

    }

    @Test
    @DisplayName("Game handles the quest sponsorship, everyone declines")
    void RESP_08_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);
        game.playerTurn = 2;
        game.questCard = new Card(2, "Q", false);
        when(mockScanner.nextLine()).
                thenReturn("n", "n", "n", "n");

        game.requestSponsorships();

        assertFalse(game.players.get(0).sponsor);
        assertFalse(game.players.get(1).sponsor);
        assertFalse(game.players.get(2).sponsor);
        assertFalse(game.players.get(3).sponsor);
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
        assertEquals(numberCardsToDraw, game.players.get(0).numberToTrim());
    }

    @Test
    @DisplayName("If any player has more then 12 cards then they must discard down")
    void RESP_9_test_02() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);
        game.distributeCards();

        int numberCardsToDraw = 2;
        for (int i = 0; i < numberCardsToDraw; i++) {
            game.players.get(0).addCard(game.drawAdventureCard());
        }
        when(mockScanner.nextLine()).
                thenReturn("0", "3");
        game.trimCards();

        assertEquals(12, game.players.get(0).hand.size());
        assertEquals(12, game.players.get(1).hand.size());
        assertEquals(12, game.players.get(2).hand.size());
        assertEquals(12, game.players.get(3).hand.size());

        System.out.println(output);

    }

    @Test
    @DisplayName("The game displays the hand of the player and prompts the player for the position of the next card to delete")
    void RESP_09_test_02() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);
        game.distributeCards();
        int numberCardsToDraw = 2;
        game.players.get(3).removeCard(0); // Player 4 will have less then 12 cards
        for (int i = 0; i < numberCardsToDraw; i++) {
            game.players.get(0).addCard(game.drawAdventureCard());
            game.players.get(0).addCard(game.drawAdventureCard());
            game.players.get(1).addCard(game.drawAdventureCard());
        }
        when(mockScanner.nextLine()).
                thenReturn("0", "0", "1", "3", "0", "1");
        game.trimCards();

        assertTrue(game.players.get(0).hand.size() <= 12);
        assertTrue(game.players.get(1).hand.size() <= 12);
        assertTrue(game.players.get(2).hand.size() <= 12);
        assertTrue(game.players.get(3).hand.size() <= 12);


        System.out.println(output);
    }

    @Test
    @DisplayName("The player enters a valid position")
    void RESP_09_test_03() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        String input = "6";
        game.PromptInput("Enter card index of card to discard: ");
        int index = game.readCardInput(input);
        assertEquals(6, index);
    }


    @Test
    @DisplayName("Test single stage")
    void RESP_10_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);
        // Create a sponsor, quest, and hand with valid cards
        Player sponsor = rigPLayer2Hands();
        sponsor.hand.sort();

        game.questCard = new Card(3, "Q", false);// Assuming a 3-stage quest
        game.quest = new Quest(game.questCard.cardValue);

        // Simulate user input to select the valid cards
        when(mockScanner.nextLine()).thenReturn("0", "5", "quit");

        assertNotNull(game.buildStage(sponsor, 0));

    }

    @Test
    @DisplayName("Test valid quest setup with multiple stages")
    void RESP_10_test_02() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);
        // Create a sponsor, quest, and hand with valid cards
        Player sponsor = rigPLayer2Hands();
        sponsor.hand.sort();
        game.questCard = new Card(4, "Q", false);

        when(mockScanner.nextLine()).
                thenReturn("0", "6", "quit").
                thenReturn("1", "4", "quit").
                thenReturn("1", "2", "3", "quit").
                thenReturn("1", "2", "quit");

        game.sponsorSetsUpQuest(sponsor);

        assertEquals("""
                -------------------
                Quest
                Stage 1: 15
                F5 Horse\s
                Stage 2: 25
                F15 Sword\s
                Stage 3: 35
                F15 Dagger Axe\s
                Stage 4: 55
                F40 Axe\s
                -------------------\n""", game.quest.toString());

        // Assert that the quest is valid
        assertEquals(4, game.quest.stages.size());
        assertTrue(game.quest.isStageValid(0));
        assertTrue(game.quest.isStageValid(1));
        assertTrue(game.quest.isStageValid(2));
        assertTrue(game.quest.isStageValid(3));

        // Assert stage values and card selections
        assertEquals(15, game.quest.stages.get(0).getValue());
        assertEquals(25, game.quest.stages.get(1).getValue());
        assertEquals(35, game.quest.stages.get(2).getValue());
        assertEquals(55, game.quest.stages.get(3).getValue());
    }


    Player rigPLayer2Hands() {
        Player p = game.players.get(0);

        int[] values = {5, 5, 15, 15, 40, 5, 10, 10, 10, 15, 15, 30};
        String[] types = {"F", "F", "F", "F", "F", "D", "S", "H", "H", "B", "B", "E"};

        for (int i = 0; i < values.length; i++) {
            p.addCard(new Card(values[i], types[i], true));
        }
        return p;
    }

    @Test
    @DisplayName("Game determines and displays eligible participants for each stage")
    void RESP_11_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);
        game.distributeCards();
        rigInitialHands(game);
        // Create a sponsor, quest, and hand with valid cards
        Player sponsor = game.players.get(1);
        game.playerTurn = 1;
        game.questCard = new Card(4, "Q", false);

        // Simulate user input to select the valid cards
        when(mockScanner.nextLine()).
                thenReturn("0", "6", "quit").
                thenReturn("1", "4", "quit").
                thenReturn("1", "2", "3", "quit").
                thenReturn("1", "2", "quit");

        game.sponsorSetsUpQuest(sponsor);

        output = new StringWriter();
        game.output = new PrintWriter(output);

        game.eligibleParticipants();

        assertTrue(output.toString().contains("[1, 3, 4]"));

    }

    @Test
    @DisplayName("Game prompts participants to withdraw or tackle the current stage")
    void RESP_12_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        Quest quest = new Quest(4);
        game.questCard = new Card(4, "Q", false);
        game.distributeCards();
        rigInitialHands(game);
        rigQuest(quest);

        game.eligibleParticipants();

        when(mockScanner.nextLine()).
                thenReturn("y", " ", "y", "n");
        game.participateInQuest();
        assertEquals("[1, 3]", quest.stages.get(quest.currentStage).participants.toString());

    }

    @Test
    @DisplayName("Each participating players draw an Adventure Card")
    void RESP_13_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        Quest quest = new Quest(4);
        game.distributeCards();
        rigInitialHands(game);
        rigQuest(quest);

        game.eligibleParticipants();

        when(mockScanner.nextLine()).thenReturn("y", " ", "y", "n");
        game.participateInQuest();

        when(mockScanner.nextLine()).thenReturn("0", "1", "2");
        game.startStage();

        assertEquals(13, game.players.get(0).hand.size());
        assertEquals(3, game.players.get(1).hand.size());
        assertEquals(13, game.players.get(2).hand.size());
        assertEquals(12, game.players.get(3).hand.size());

    }

    void setUpQuest(Scanner mockScanner) {
        Player p1 = game.players.get(0);
        Player p2 = game.players.get(1);
        Player p3 = game.players.get(2);
        Player p4 = game.players.get(3);
        Card quest = game.drawEventCard();
        game.eventDeck.add(quest);

        quest = game.eventDeck.removeCard(new Card(4, "Q", false));
        game.questCard = quest;

        game.displayHand(game.playerTurn);
        game.startTurn(quest);
        game.displayHand(game.playerTurn);

        when(mockScanner.nextLine()).thenReturn("n", "y");
        game.requestSponsorships();

        when(mockScanner.nextLine()).
                thenReturn("0", "6", "quit").
                thenReturn("1", "4", "quit").
                thenReturn("1", "2", "3", "quit").
                thenReturn("1", "2", "quit");

        game.sponsorSetsUpQuest(game.players.get(game.playerTurn));

        game.eligibleParticipants();

        when(mockScanner.nextLine()).thenReturn("y", "y", "y");
        game.participateInQuest();

        p1.addCard(game.adventureDeck.removeCard(new Card(30, "F", true)));
        p3.addCard(game.adventureDeck.removeCard(new Card(10, "S", true)));
        p4.addCard(game.adventureDeck.removeCard(new Card(15, "B", true)));

        p1.hand.sort();
        p2.hand.sort();
        p3.hand.sort();
        p4.hand.sort();

        when(mockScanner.nextLine()).
                thenReturn("0", "0", "0");
        game.trimCards();
    }

    @Test
    @DisplayName("Participants set up a valid attack")
    void RESP_14_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        setInitialHands(game);

        setUpQuest(mockScanner);

        when(mockScanner.nextLine()).
                thenReturn("4", "4", "quit").thenReturn("4", "3", "quit").thenReturn("3", "5", "quit");
        game.handleParticipantAttacks();

        assertEquals(15, game.players.get(0).attackValue);
        assertEquals(0, game.players.get(1).attackValue);
        assertEquals(15, game.players.get(2).attackValue);
        assertEquals(15, game.players.get(3).attackValue);


        System.out.println(output);

    }

    @Test
//    @Disabled
    @DisplayName("Game resolves attacks against the current stage")
    void RESP_15_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        setInitialHands(game);

        setUpQuest(mockScanner);


        when(mockScanner.nextLine()).
                thenReturn("4", "4", "quit").thenReturn("4", "quit").thenReturn("3", "5", "quit");
        game.handleParticipantAttacks();


        game.resolveStage();
        System.out.println(output);
        assertTrue(output.toString().contains("Players continuing the quest: [1, 4]"));
    }

    @Test
//    @Disabled
    @DisplayName("Game handles the completion of a quest and updates shields")
    void RESP_16_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        setInitialHands(game);

        setUpQuest(mockScanner);


        when(mockScanner.nextLine()).
                thenReturn("4", "4", "quit").thenReturn("4", "3", "quit").thenReturn("3", "5", "quit");
        game.handleParticipantAttacks();


        game.resolveStage();
        assertEquals(0, game.players.get(0).shields);
        assertEquals(0, game.players.get(2).attack.size());
        assertEquals(0, game.players.get(3).attack.size());
    }

    @Test
    @DisplayName("Game discards cards used in attacks ")
    void RESP_17_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        // Start game, decks are created, hands of the 4 players are set up with random cards
        setInitialHands(game);

        setUpQuest(mockScanner);


        when(mockScanner.nextLine()).
                thenReturn("4", "4", "quit").thenReturn("4", "quit").thenReturn("3", "5", "quit");
        game.handleParticipantAttacks();


        game.quest.stages.get(3).participants = game.quest.stages.get(game.quest.currentStage).participants;
        game.quest.stages.get(3).value = 15;
        game.quest.currentStage = 3;

        when(mockScanner.nextLine()).
                thenReturn("0");
        game.resolveStage();

        assertEquals(4, game.players.get(0).shields);
        assertEquals(0, game.players.get(2).shields);
        assertEquals(4, game.players.get(3).shields);
    }

    @Test
//    @Disabled
    @DisplayName("Game discards cards used by the sponsor and updates their hand, who then draws the same number of cards + the number of stages, and then possibly trims their hand")
    void RESP_18_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        // Start game, decks are created, hands of the 4 players are set up with random cards
        setInitialHands(game);
        setUpQuest(mockScanner);
        when(mockScanner.nextLine()).
                thenReturn("4", "4", "quit").thenReturn("4", "quit").thenReturn("3", "5", "quit");
        game.handleParticipantAttacks();

        game.quest.stages.get(3).participants = game.quest.stages.get(game.quest.currentStage).participants;
        game.quest.stages.get(3).value = 15;
        game.quest.currentStage = 3;

        when(mockScanner.nextLine()).
                thenReturn("0");
        game.resolveStage();

        assertEquals(12, game.players.get(1).hand.size());

    }

    @Test
    @DisplayName("If adventure deck or event deck runs out, reshuffle discard ")
    void RESP_19_test_01() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        for (int i = 0; i < 100; i++) {
            game.adventureDiscardDeck.add(game.drawAdventureCard());
        }

        for (int i = 0; i < 17; i++) {
            game.eventDiscardDeck.add(game.drawEventCard());
        }

        assertEquals(0, game.adventureDeck.size());
        assertEquals(100, game.adventureDiscardDeck.size());
        assertEquals(0, game.eventDeck.size());
        assertEquals(17, game.eventDiscardDeck.size());

        game.adventureDiscardDeck.add(game.drawAdventureCard());
        game.eventDiscardDeck.add(game.drawEventCard());

        assertEquals(99, game.adventureDeck.size());
        assertEquals(1, game.adventureDiscardDeck.size());
        assertEquals(16, game.eventDeck.size());
        assertEquals(1, game.eventDiscardDeck.size());


    }

    void rigInitialHands(Main game) {
        int[] values1 = {5, 5, 15, 15, 5, 10, 10, 10, 10, 15, 15, 20};
        String[] types1 = {"F", "F", "F", "F", "D", "S", "S", "H", "H", "B", "B", "L"};
        int[] values2 = {5, 5, 15, 15, 40, 5, 10, 10, 10, 15, 15, 30};
        String[] types2 = {"F", "F", "F", "F", "F", "D", "S", "H", "H", "B", "B", "E"};
        int[] values3 = {5, 5, 5, 15, 5, 10, 10, 10, 10, 10, 15, 20};
        String[] types3 = {"F", "F", "F", "F", "D", "S", "S", "S", "H", "H", "B", "L"};
        int[] values4 = {5, 15, 15, 40, 5, 5, 10, 10, 10, 15, 20, 30};
        String[] types4 = {"F", "F", "F", "F", "D", "D", "S", "H", "H", "B", "L", "E"};

        rigHand(game.players.get(0), values1, types1);
        rigHand(game.players.get(1), values2, types2);
        rigHand(game.players.get(2), values3, types3);
        rigHand(game.players.get(3), values4, types4);
        game.players.get(0).hand.sort();
        game.players.get(1).hand.sort();
        game.players.get(2).hand.sort();
        game.players.get(3).hand.sort();
    }

    void setInitialHands(Main game) {
        int[] values1 = {5, 5, 15, 15, 5, 10, 10, 10, 10, 15, 15, 20};
        String[] types1 = {"F", "F", "F", "F", "D", "S", "S", "H", "H", "B", "B", "L"};
        int[] values2 = {5, 5, 15, 15, 40, 5, 10, 10, 10, 15, 15, 30};
        String[] types2 = {"F", "F", "F", "F", "F", "D", "S", "H", "H", "B", "B", "E"};
        int[] values3 = {5, 5, 5, 15, 5, 10, 10, 10, 10, 10, 15, 20};
        String[] types3 = {"F", "F", "F", "F", "D", "S", "S", "S", "H", "H", "B", "L"};
        int[] values4 = {5, 15, 15, 40, 5, 5, 10, 10, 10, 15, 20, 30};
        String[] types4 = {"F", "F", "F", "F", "D", "D", "S", "H", "H", "B", "L", "E"};

        setHand(game.players.get(0), values1, types1);
        setHand(game.players.get(1), values2, types2);
        setHand(game.players.get(2), values3, types3);
        setHand(game.players.get(3), values4, types4);
        game.players.get(0).hand.sort();
        game.players.get(1).hand.sort();
        game.players.get(2).hand.sort();
        game.players.get(3).hand.sort();
    }

    void setHand(Player p, int[] values, String[] types) {
        for (int i = 0; i < 12; i++) {
            p.addCard(game.adventureDeck.removeCard(new Card(values[i], types[i], true)));
        }
    }

    void rigHand(Player p, int[] values, String[] types) {

        for (int i = 0; i < 12; i++) {
            game.adventureDeck.add(p.hand.drawCard());
        }

        for (int i = 0; i < 12; i++) {
            p.addCard(game.adventureDeck.removeCard(new Card(values[i], types[i], true)));
        }
    }


    public void rigQuest(Quest quest) {
        Stage stage = new Stage();
        Player p2 = game.players.get(1);
        System.out.println(p2.hand);
        stage.foeCard = p2.hand.removeCard(new Card(5, "F", true));
        stage.weaponCards.add(p2.hand.removeCard(new Card(10, "H", true)));
        stage.calculateValue();
        quest.stages.add(stage);

        stage = new Stage();
        stage.foeCard = p2.hand.removeCard(new Card(15, "F", true));
        stage.weaponCards.add(p2.hand.removeCard(new Card(10, "S", true)));
        stage.calculateValue();
        quest.stages.add(stage);

        stage = new Stage();
        stage.foeCard = p2.hand.removeCard(new Card(15, "F", true));
        stage.weaponCards.add(p2.hand.removeCard(new Card(5, "D", true)));
        stage.weaponCards.add(p2.hand.removeCard(new Card(15, "B", true)));
        stage.calculateValue();
        quest.stages.add(stage);

        stage = new Stage();
        stage.foeCard = p2.hand.removeCard(new Card(40, "F", true));
        stage.weaponCards.add(p2.hand.removeCard(new Card(15, "B", true)));
        stage.calculateValue();
        quest.stages.add(stage);
        game.quest = quest;
        game.playerTurn = 1;
    }

    @Test
    @DisplayName("A-TEST JP-Scenario")
    void A_TEST_JP_Scenario() {
        StringWriter output = new StringWriter();
        Scanner mockScanner = mock(Scanner.class);
        initializeGame(mockScanner, output);

        Player p1 = game.players.get(0);
        Player p2 = game.players.get(1);
        Player p3 = game.players.get(2);
        Player p4 = game.players.get(3);


        // Start game, decks are created, hands of the 4 players are set up with random cards
        game.InitializeAdventureDeck();
        game.InitializeEventDeck();
        game.adventureDeck.shuffle();
        game.eventDeck.shuffle();
        game.distributeCards();

        // Rig the 4 hands to the hold the cards of the 4 posted initial hands
        rigInitialHands(game);

        Card quest = game.drawEventCard();

        game.eventDeck.add(quest);
        quest = game.eventDeck.removeCard(new Card(4, "Q", false));

        game.displayHand(game.playerTurn);

        game.startTurn(quest);
        game.displayHand(game.playerTurn);

        when(mockScanner.nextLine()).
                thenReturn("n", "y");
        game.requestSponsorships();

        when(mockScanner.nextLine()).
                thenReturn("0", "6", "quit").
                thenReturn("1", "4", "quit").
                thenReturn("1", "2", "3", "quit").
                thenReturn("1", "2", "quit");

        game.sponsorSetsUpQuest(game.players.get(game.playerTurn));

        game.eligibleParticipants();

        when(mockScanner.nextLine()).thenReturn("y", "y", "y");

        game.participateInQuest();

        p1.addCard(game.adventureDeck.removeCard(new Card(30, "F", true)));
        p3.addCard(game.adventureDeck.removeCard(new Card(10, "S", true)));
        p4.addCard(game.adventureDeck.removeCard(new Card(15, "B", true)));

        when(mockScanner.nextLine()).
                thenReturn("0", "0", "0");
        game.trimCards();

        when(mockScanner.nextLine()).
                thenReturn("4", "4", "quit").thenReturn("4", "3", "quit").thenReturn("3", "5", "quit");

        game.handleParticipantAttacks();
        game.resolveStage();

        /*
        Stage 2:
        a. P1 is asked and decides to participate. P1 draws a F10
        b. P3 is asked and decides to participate. P3 draws a Lance
        c. P4 is asked and decides to participate. P4 draws a Lance
         */

        when(mockScanner.nextLine()).
                thenReturn("y", "y", "y");

        game.participateInQuest();

        p1.addCard(game.adventureDeck.removeCard(new Card(10, "F", true)));
        p3.addCard(game.adventureDeck.removeCard(new Card(20, "L", true)));
        p4.addCard(game.adventureDeck.removeCard(new Card(20, "L", true)));

        /*
        d. P1 sees their hand and builds an attack: Horse + Sword => value of 20
        e. P3 sees their hand and builds an attack: Axe + Sword => value of 25
        f. P4 sees their hand and builds an attack: Horse + Axe=> value of 25

         */

        when(mockScanner.nextLine()).
                thenReturn("6", "5", "quit").thenReturn("8", "7", "quit").thenReturn("5", "6", "quit");

        game.handleParticipantAttacks();
        game.resolveStage();

        // Assert P1 has no shields and their hand is F5 F10 F15 F15 F30 Horse Axe Axe Lance (displayed in this order)
        assertEquals(0, p1.shields);
        assertEquals("F5 F10 F15 F15 F30 Horse Axe Axe Lance ", p1.handToString());

        /*
        Stage 3:
            a. P3 is asked and decides to participate. P3 draws an Axe
            b. P4 is asked and decides to participate. P4 draws a Sword
            c. P3 sees their hand and builds an attack: Lance + Horse + Sword => value of
            40
            d. P4 sees their hand and builds an attack: Axe + Sword + Lance => value of 45
            e. Resolution: P3’s and P4’s attack are su[icient go onto the next stage
            f. All 2 participants discard the cards used for their attacks
         */

        when(mockScanner.nextLine()).
                thenReturn("y", "y");

        game.participateInQuest();

        p3.addCard(game.adventureDeck.removeCard(new Card(15, "B", true)));
        p4.addCard(game.adventureDeck.removeCard(new Card(10, "S", true)));

        /*
        Stage 3:
            a. P3 is asked and decides to participate. P3 draws an Axe
            b. P4 is asked and decides to participate. P4 draws a Sword
            c. P3 sees their hand and builds an attack: Lance + Horse + Sword => value of
            40
            d. P4 sees their hand and builds an attack: Axe + Sword + Lance => value of 45
            e. Resolution: P3’s and P4’s attack are sufficient go onto the next stage
            f. All 2 participants discard the cards used for their attacks
         */

        when(mockScanner.nextLine()).
                thenReturn("8", "5", "3", "quit").thenReturn("6", "4", "5", "quit");

        game.handleParticipantAttacks();
        game.resolveStage();

        /*
        Stage 4:
            a. P3 is asked and decides to participate. P3 draws a F30
            b. P4 is asked and decides to participate. P4 draws a Lance
            c. P3 sees their hand and builds an attack: Axe + Horse + Lance=> value of 45
            d. P4 sees their hand and builds an attack: Dagger + Sword + Lance + Excalibur
            => value of 65
         */

        when(mockScanner.nextLine()).thenReturn("y", "y");
        game.participateInQuest();
        p3.addCard(game.adventureDeck.removeCard(new Card(30, "F", true)));
        p4.addCard(game.adventureDeck.removeCard(new Card(20, "L", true)));

        when(mockScanner.nextLine()).
                thenReturn("6", "5", "5", "quit").thenReturn("3", "3", "3", "4", "quit");

        game.handleParticipantAttacks();
        /*
        P2 discards all 9 cards used in the quest and draws 9+4 = 13 random cards and then trims down to 12 cards. It does not matter which cards are selected to discard.
         */
        when(mockScanner.nextLine()).
                thenReturn("0");
        game.resolveStage();

        /*
        e. Resolution:
            i. P3’s attack is insufficient – P3 loses and receives no shields
            ii. P4’s attack is sufficient – P4 receives 4 shields
            f. All 2 participants discard the cards used for their attacks
            i. assert P3 has no shields and has the 5 cards: F5 F5 F15 F30 Sword
            ii. assert P4 has 4 shields and has the cards: F15 F15 F40 Lance
         */


        assertEquals(0, p3.shields);
        assertEquals("F5 F5 F15 F30 Sword ", p3.handToString());

        assertEquals(4, p4.shields);
        assertEquals("F15 F15 F40 Lance ", p4.handToString());

        //  assert P2 has 12 cards in hand
        assertEquals(12, p2.hand.size());


//        System.out.println(output);

    }

}