import java.io.PrintWriter;
import java.util.Scanner;

public class Player {
    int playerNumber;
    Deck hand;
    final int MAX_CARDS = 12;
    int shields;
    boolean sponsor = false;
    Deck attack;
    int attackValue;

    public Player(int playerNumber) {
        this.playerNumber = playerNumber;
        hand = new Deck(MAX_CARDS);
        shields = 0;
        attack = new Deck(MAX_CARDS);
    }

    public boolean hasWon() {
        return shields >= 7;
    }

    public void setShields(int shields) {
        this.shields = shields;
    }

    public void plague() {
        shields = shields - 2;
        if (shields < 0) {
            shields = 0;
        }
    }

    // TODO: add card to deck
    public void addCard(Card card) {
        hand.add(card);
    }

    public Card drawCard() {
        return hand.drawCard();
    }

    public Card removeCard(int index) {
        return hand.removeCard(index);
    }

    public int numberToTrim() {
        if (hand.size() > MAX_CARDS) {
            return hand.size() - MAX_CARDS;
        }
        return 0;
    }

    public int countFoes() {
        int foeCounter = 0;
        for (int i = 0; i < hand.size(); i++) {
            if (hand.getCard(i).isFoe()) {
                foeCounter++;
            }

        }
        return foeCounter;
    }

    public String toString() {
        return playerNumber + "";
    }

    public int setupAttack(Stage stage, Scanner input, PrintWriter output) {
        output.print("Player " + playerNumber + " set up your attack.\n");

        while (true) {
            output.print(hand + "\n");
            output.print("Select cards for the stage attack: ");
            String userInput = input.nextLine();
            output.print(userInput + "\n");
            output.flush();
            if (userInput.equalsIgnoreCase("quit")) {
                break;
            }
            int cardIndex = Integer.parseInt(userInput);

            if (cardIndex >= 0 && cardIndex < hand.size()) {
                Card card = hand.removeCard(cardIndex);
                // Validate card type (foe or weapon) and uniqueness within the stage
                if (isValidAttackCard(card)) {
                    attack.add(card);
                    output.print("Selected: " + card + "\n");

                } else {
                    output.print("Invalid card selection.\n");
                }
            } else {
                output.println("Invalid card index.\n");
            }
        }

        // Calculate total attack value based on attackDeck
        attackValue = calculateAttackValue(attack);
        output.print("Your attack value is " + attackValue);
        output.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        return attackValue;

    }

    public boolean isValidAttackCard(Card card) {
        if (card == null || card.isFoe()) {
            return false;
        }
        for (Card a : attack.deck) {
            if (a.equals(card)) {
                return false;
            }
        }
        return true;
    }

    private int calculateAttackValue(Deck attackDeck) {
        int totalValue = 0;
        for (Card card : attackDeck.getDeck()) {
            totalValue += card.getValue();
        }
        return totalValue;
    }


}
