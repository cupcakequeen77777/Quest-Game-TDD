import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Main {
    public static void main(String[] args) {
        System.out.print("Adventure Game!");
    }

    final int numberTypesOfFoes = 10;
    final int numberPlayers = 4;
    int playerTurn = 0;
    int sponsorCount = 0;
    Card quest = null;

    Deck adventureDeck = new Deck(50);
    Deck eventDeck = new Deck(50);
    ArrayList<Player> players = new ArrayList<>(numberPlayers);
    Deck discardDeck = new Deck(100);

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
            if (!Objects.equals(card.GetCardType(), "F")) {
                count++;
            }
        }
        return count;
    }

    private int GetNumberOfType(String type, Deck deck) {
        int count = 0;
        for (Card card : deck.getDeck()) {
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
//        int count = 0;
        for (int i = 0; i < numberTypesOfFoes; i++) {
            for (int j = 0; j < numberFoes[i]; j++) {
                newCard = new Card(value[i], "F");
                adventureDeck.add(newCard);
//                count++;
            }
        }

        for (int i = 0; i < numberWeaponTypes; i++) {
            for (int j = 0; j < numberWeapons[i]; j++) {
                newCard = new Card(wValue[i], wType[i]);
                adventureDeck.add(newCard);
//                adventureDeck.add(count, newCard);
//                count++;
            }
        }

    }

    public void InitializeEventDeck() {
        Card newCard;
        int[] value = {2, 3, 4, 5};
        int[] numberQuests = {3, 4, 3, 2}; // of certain value
        int numberTypesOfQuests = 4;
//        int count = 0;

        for (int i = 0; i < numberTypesOfQuests; i++) {
            for (int j = 0; j < numberQuests[i]; j++) {
                newCard = new Card(value[i], "Q");
                eventDeck.add(newCard);
//                count++;
            }
        }

        int[] eValue = {1, 2, 3}; // of certain value
        int[] numberEvents = {1, 2, 2}; // of certain value
        int numberTypesOfEvents = 3;

        for (int i = 0; i < numberTypesOfEvents; i++) {
            for (int j = 0; j < numberEvents[i]; j++) {
                newCard = new Card(eValue[i], "E");
                eventDeck.add(newCard);
//                count++;
            }
        }
    }

    public void distributeCards() {
        adventureDeck.shuffle();
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
        return eventDeck.drawCard();
    }

    public void resolveEvent(Card newCard) {
        Player currentPlayer = players.get(playerTurn);
        switch (newCard.cardValue) {
            case 1: // Plague: current player loses 2 shields
                currentPlayer.plague();
                break;
            case 2: // Queen’s favor: current player draws 2 adventure cards and possibly trims their hand (UC-03)
                currentPlayer.addCard(drawAdventureCard());
                currentPlayer.addCard(drawAdventureCard());
                break;
            case 3: // Prosperity: All players draw 2 adventure cards and each of them possibly trims their hand (UC-03)
                for (Player player : players) {
                    player.addCard(drawAdventureCard());
                    player.addCard(drawAdventureCard());
                }

                break;

        }
    }

    /*
    Output: player number if found sponsor, if all players decline -2, if current player declines -1
     */
    public int requestSponsorship(Scanner input, PrintWriter output, int player) {
        String prompt = "Player " + (player + 1) + " do you want to sponsor (y/N): ";
        String response = PromptInput(input, output, prompt);
        if (response.equalsIgnoreCase("y")) {  // Prompt the current player to sponsor the quest.
            players.get(player).sponsor = true;
            System.out.println("Quest Setup");
            return player;
        }
        sponsorCount++;
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        if (sponsorCount >= numberPlayers) {
            discardDeck.add(quest);
            quest = null;
            playerTurn = nextPlayer();
            sponsorCount = 0;
            return -2;
        }
        return -1;// Continue this process until a player agrees to sponsor or all players decline.
    }

    public int nextPlayer() {
        return (playerTurn + 1) % numberPlayers;
    }

    public int startTurn(PrintWriter output, Card newCard) {
        output.print("Current player: " + players.get(playerTurn).playerNumber);
        switch (newCard.type) {
            case "E":
                resolveEvent(newCard);
                break;
            case "Q":
                quest = newCard;
                break;
        }

        return playerTurn;
    }

    public String PromptInput(Scanner input, PrintWriter output, String prompt) {
        output.print(prompt);
        output.flush();
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

    public ArrayList<Integer> numberCardsToTrim() {
        ArrayList<Integer> numberCardsToTrim = new ArrayList<>();
        for (Player player : players) {
            numberCardsToTrim.add(player.numberToTrim());
        }
        return numberCardsToTrim;
    }


    public void trimPlayerCard(int cardIndex, int playerIndex) {
        Card discard = players.get(playerIndex).removeCard(cardIndex);
        if (discard != null) {
            discardDeck.add(discard);
        }
    }

    public void displayHand(PrintWriter output, int playerIndex) {
        output.print(players.get(playerIndex).hand + "\n");
    }

}
