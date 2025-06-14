import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        StringWriter output = new StringWriter();
        PrintWriter out = new PrintWriter(output);
        Main game = new Main(input, out);

        game.players.add(new Player(1));
        game.players.add(new Player(2));
        game.players.add(new Player(3));
        game.players.add(new Player(4));

        // Start game, decks are created, hands of the 4 players are set up with random cards
        game.InitializeAdventureDeck();
        game.InitializeEventDeck();
        game.adventureDeck.shuffle();
        game.eventDeck.shuffle();
        game.distributeCards();


        while (!game.checkForWinner()) {

            Card newCard = game.drawEventCard();

            game.startTurn(newCard);

            if (newCard.type.equals("Q")) {
                game.displayHand(game.playerTurn);

                Card quest = newCard;

                game.eventDeck.add(quest);

                game.questCard = quest;

                game.displayHand(game.playerTurn);

                game.requestSponsorships();

                game.sponsorSetsUpQuest(game.players.get(game.playerTurn));

                while (true) {

                    game.eligibleParticipants();

                    game.participateInQuest();

                    game.startStage();

                    game.trimCards();

                    game.handleParticipantAttacks();
                    game.resolveStage();
                    if (game.quest.currentStage >= game.quest.numStages || game.quest.stages.get(game.quest.currentStage).participants.isEmpty()) {
                        break;
                    }
                }
            } else {

            }
            System.out.print("Winners: " + game.getWinners());
            System.out.println(output);
        }
    }

    final int numberTypesOfFoes = 10;
    final int numberPlayers = 4;
    int playerTurn = 0;
    Card questCard = null;
    Quest quest = null;
    public PrintWriter output;
    public Scanner input;

    Deck adventureDeck = new Deck(50);
    Deck eventDeck = new Deck(50);
    ArrayList<Player> players = new ArrayList<>(numberPlayers);
    Deck adventureDiscardDeck = new Deck(50);
    Deck eventDiscardDeck = new Deck(50);

    public Main() {
        input = new Scanner(System.in);
        output = new PrintWriter(System.out, true);
    }

    public Main(Scanner in, PrintWriter out) {
        input = in;
        output = out;
    }

    public Deck getAdventureDeck() {
        return adventureDeck;
    }

    public Deck getEventDeck() {
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
        for (Card card : adventureDeck.getDeck()) {
            if (!Objects.equals(card.GetType(), "F")) {
                count++;
            }
        }
        return count;
    }

    private int GetNumberOfType(String type, Deck deck) {
        int count = 0;
        for (Card card : deck.getDeck()) {
            if (Objects.equals(card.GetType(), type)) {
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

        for (int i = 0; i < numberTypesOfFoes; i++) {
            for (int j = 0; j < numberFoes[i]; j++) {
                newCard = new Card(value[i], "F", true);
                adventureDeck.add(newCard);

            }
        }

        for (int i = 0; i < numberWeaponTypes; i++) {
            for (int j = 0; j < numberWeapons[i]; j++) {
                newCard = new Card(wValue[i], wType[i], true);
                adventureDeck.add(newCard);
            }
        }

    }

    public void InitializeEventDeck() {
        Card newCard;
        int[] value = {2, 3, 4, 5};
        int[] numberQuests = {3, 4, 3, 2}; // of certain value
        int numberTypesOfQuests = 4;

        for (int i = 0; i < numberTypesOfQuests; i++) {
            for (int j = 0; j < numberQuests[i]; j++) {
                newCard = new Card(value[i], "Q", false);
                eventDeck.add(newCard);
            }
        }

        int[] eValue = {1, 2, 3}; // of certain value
        int[] numberEvents = {1, 2, 2}; // of certain value
        int numberTypesOfEvents = 3;

        for (int i = 0; i < numberTypesOfEvents; i++) {
            for (int j = 0; j < numberEvents[i]; j++) {
                newCard = new Card(eValue[i], "E", false);
                eventDeck.add(newCard);
            }
        }

        eventDeck.shuffle();
    }

    public void distributeCards() {
        int defaultNumCard = 12;
        for (int i = 0; i < defaultNumCard; i++) {
            players.get(0).hand.add(drawAdventureCard());
            players.get(1).hand.add(drawAdventureCard());
            players.get(2).hand.add(drawAdventureCard());
            players.get(3).hand.add(drawAdventureCard());
        }

        for (Player player : players) {
            player.hand.sort();
        }
    }

    public Card drawAdventureCard() {
        if (adventureDeck.isEmpty()) {
            adventureDeck.addAll(adventureDiscardDeck.removeAll());
            adventureDeck.shuffle();
        }
        return adventureDeck.drawCard();
    }

    public boolean checkForWinner() {
        for (Player player : players) {
            if (player.hasWon()) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> getListOfWinners() {
        ArrayList<String> winners = new ArrayList<>();
        for (Player player : players) {
            if (player.hasWon()) {
                winners.add(player.playerNumber + ""); // QUESTION: what is the winner IDs?
            }
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
        if (eventDeck.isEmpty()) {
            eventDeck.addAll(eventDiscardDeck.removeAll());
            eventDeck.shuffle();
        }
        return eventDeck.drawCard();
    }

    public void resolveEvent(Card newCard) {
        Player currentPlayer = players.get(playerTurn);
        switch (newCard.cardValue) {
            case 1: // Plague: current player loses 2 shields
                System.out.print("Lose 2 shields\n");
                output.print("Lose 2 shields\n");
                currentPlayer.plague();
                break;
            case 2: // Queen’s favor: current player draws 2 adventure cards and possibly trims their hand (UC-03)
                System.out.print("Draw 2 adventure cards\n");
                output.print("Draw 2 adventure cards\n");
                currentPlayer.addCard(drawAdventureCard());
                currentPlayer.addCard(drawAdventureCard());
                currentPlayer.hand.sort();
                trimCards();
                break;
            case 3: // Prosperity: All players draw 2 adventure cards and each of them possibly trims their hand (UC-03)
                System.out.print("Everyone draws 2 adventure cards\n");
                output.print("Everyone draws 2 adventure cards\n");
                for (Player player : players) {
                    player.addCard(drawAdventureCard());
                    player.addCard(drawAdventureCard());
                    currentPlayer.hand.sort();
                    trimCards();
                }

                break;

        }
        playerTurn = nextPlayer();
    }


    public boolean requestSponsorships() {
        System.out.println("Quest of " + questCard.cardValue + " stages");
        output.println("Quest of " + questCard.cardValue + " stages");
        for (int i = 0; i < numberPlayers; i++) {
            String prompt = "Player " + ((playerTurn + 1) + " do you want to sponsor (y/N): ");
            String response = PromptInput(prompt);
            System.out.print(response + "\n");
            output.print(response + "\n");
            if (response.equalsIgnoreCase("y")) {  // Prompt the current player to sponsor the quest.
                players.get(playerTurn).sponsor = true;
                return true;
            }
            playerTurn = nextPlayer();

        }
        eventDiscardDeck.add(questCard);
        quest = null;

        return false;// Continue this process until a player agrees to sponsor or all players decline.
    }


    public int nextPlayer() {
        System.out.println("Press <Enter> to end your turn");
        output.println("Press <Enter> to end your turn");
        input.nextLine();
        output.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        return (playerTurn + 1) % numberPlayers;
    }

    public void startTurn(Card newCard) {
        System.out.println("Current player: " + players.get(playerTurn).playerNumber + "\n");
        output.print("Current player: " + players.get(playerTurn).playerNumber + "\n");
        System.out.println("Drew: " + newCard);
        output.println(newCard);
        switch (newCard.type) {
            case "E":
                resolveEvent(newCard);
                break;
            case "Q":
                questCard = newCard;
                break;
        }
    }

    public String PromptInput(String prompt) {
        System.out.print(prompt);
        output.print(prompt);
        return input.nextLine();
    }

    public int readCardInput(String input) {
        int cardIndex = -1;
        try {
            cardIndex = Integer.parseInt(input);
        } catch (NumberFormatException _) {
        }
        return cardIndex;
    }

    public void trimCards() {
        output.flush();
        for (Player player : players) {
            int count = player.numberToTrim();
            if (player.numberToTrim() > 0) {
                System.out.println("Player " + player.playerNumber + " need to discard " + player.numberToTrim() + " cards\n");
                output.print("Player " + player.playerNumber + " need to discard " + player.numberToTrim() + " cards\n");
                for (int i = 0; i < count; i++) {
                    System.out.print(player.handToString());
                    output.print(player.handToString());
                    System.out.print("\nChoose a card to discard: ");
                    output.print("\nChoose a card to discard: ");

                    int cardIndex = input.nextInt();
                    output.print(cardIndex + "\n");

                    Card card = player.removeCard(cardIndex);
                    System.out.println("Discard " + card + "\n\n");
                    output.print("Discard " + card + "\n\n");
                    adventureDeck.add(card);
                }

                displayHand(player.playerNumber - 1);
                nextPlayer();
            }
        }

    }

    public void displayHand(int playerIndex) {
        System.out.println("Hand: " + players.get(playerIndex).handToString() + "\n");
        output.print("Hand: " + players.get(playerIndex).handToString() + "\n");
    }

    public Quest sponsorSetsUpQuest(Player sponsor) {
        quest = new Quest(questCard.cardValue);
        quest.sponsor = sponsor;
        for (int i = 0; i < quest.numStages; i++) {
            buildStage(sponsor, i);
        }

        // Check if all stages are valid
        if (quest.stages.size() == quest.numStages) {
            // Quest is ready to be resolved
            output.println("Quest setup successful!");
        } else {
            output.println("Quest setup failed: Insufficient stages.");
        }

        output.flush();

        output.println(quest);

        return quest;
    }

    public Stage buildStage(Player sponsor, int stageIndex) {
        Stage stage = new Stage();
        boolean stageIsValid = false;
        quest.addStage(stage);

        while (true) {
            displayHand(playerTurn);
            String userInput = PromptInput("Sponsor, choose a card for stage " + (stageIndex + 1) + "/" + quest.numStages + ": ");
            output.flush();
            System.out.print(userInput);
            output.print(userInput);
            if (userInput.equalsIgnoreCase("quit") && stageIsValid) {
                System.out.println("\n\n");
                output.print("\n\n");
                break;
            } else if (userInput.equalsIgnoreCase("quit")) {
                System.out.println("\nInsufficient value for this stage.\n");
                output.print("\nInsufficient value for this stage.\n");
                continue;
            }

            int cardIndex = readCardInput(userInput);

            if (cardIndex >= 0 && cardIndex < sponsor.hand.size()) {
                Card card = sponsor.hand.removeCard(cardIndex);
                // Validate card type (foe or weapon) and uniqueness within the stage
                if (card != null && stage.isValidCard(card)) {
                    stage.addCard(card);
                    stage.value = stage.calculateValue();
                    System.out.print("\nSelected: " + card + "\n\n");
                    output.print("\nSelected: " + card + "\n\n");

                    if (quest.isStageValid(stageIndex)) {
                        stageIsValid = true;
                    }
                } else {
                    sponsor.hand.add(card);
                    System.out.println("Invalid card selection.\n");
                    output.print("Invalid card selection.\n");
                }
            } else {
                System.out.println("Invalid card index.");
                output.println("Invalid card index.");
            }
        }
        return stage;
    }

    public ArrayList<Player> eligibleParticipants() {
        ArrayList<Player> eligibleParticipants;
        if (quest.currentStage == 0) {
            eligibleParticipants = new ArrayList<>();
            for (Player player : players) {
                if (player.playerNumber - 1 != playerTurn) {
                    eligibleParticipants.add(player);
                }
            }
        } else {
            eligibleParticipants = quest.stages.get(quest.currentStage).participants;
        }
        output.print("Eligible Participants: " + eligibleParticipants + "\n");
        return eligibleParticipants;
    }

    public void participateInQuest() {
        ArrayList<Player> eligibleParticipants = eligibleParticipants();
        ArrayList<Player> participants = eligibleParticipants();
        for (Player player : eligibleParticipants) {
            if (player.playerNumber != playerTurn - 1) {
                System.out.print(player.handToString());
                output.print(player.handToString());
                String prompt = "\nPlayer " + player.playerNumber + " do you want to participate in the quest (y/N): ";
                String response = PromptInput(prompt);
                System.out.println(response + "\n");
                output.print(response + "\n");
                if (response.equalsIgnoreCase("n")) {
                    participants.remove(player);
                }

            }
            nextPlayer();
        }
        quest.stages.get(quest.currentStage).participants = participants;
    }

    public void startStage() {
        Stage stage = quest.stages.get(quest.currentStage);
        for (Player participant : stage.participants) {
            participant.addCard(adventureDeck.drawCard());
            participant.hand.sort();
        }
        System.out.println();
    }


    public void handleParticipantAttacks() {
        ArrayList<Player> participants = quest.stages.get(quest.currentStage).participants;

        for (Player participant : participants) {
            participant.attackValue = participant.setupAttack(input, output);
        }

    }

    public void resolveStage() {
        if (quest.numStages - 1 == quest.currentStage) {
            quest.stages.add(new Stage());
        }

        for (int i = 0; i < quest.stages.get(0).participants.size(); i++) {
            Player participant = quest.stages.get(0).participants.get(i);

            // participants with an attack equal or greater to the value of the current stage are eligible for the next stage
            if (participant.attackValue >= quest.stages.get(quest.currentStage).value) {

                // If this is the last stage, they are winners of this quest and earn as many shields as there are stages to this quest.
                if (quest.numStages - 1 == quest.currentStage) {
                    participant.shields += quest.numStages;
                }
                quest.stages.get(quest.currentStage + 1).participants.add(participant);
            }
            adventureDiscardDeck.addAll(participant.attack);
            participant.attack.removeAll();
        }

        if (quest.numStages - 1 == quest.currentStage) {
            System.out.println("Quest completed by players: " + quest.stages.get(quest.currentStage + 1).participants + "\n");
            output.print("Quest completed by players: " + quest.stages.get(quest.currentStage + 1).participants + "\n");
            // draws the same number of cards + the number of stages, and then possibly trims their hand
            for (int i = 0; i < quest.countCardsUsed() + quest.numStages; i++) {
                quest.sponsor.addCard(adventureDeck.drawCard());
            }
            quest.sponsor.hand.sort();
            trimCards();
            for (Stage stage : quest.stages) {
                adventureDiscardDeck.add(stage.foeCard);
                stage.foeCard = null;
                adventureDiscardDeck.addAll(stage.weaponCards);
                stage.weaponCards.removeAll();
            }
        } else {
            quest.currentStage++;
            System.out.println("Players continuing the quest: " + quest.stages.get(quest.currentStage).participants);
            output.print("Players continuing the quest: " + quest.stages.get(quest.currentStage).participants);
        }


    }


}
