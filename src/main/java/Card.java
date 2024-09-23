

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

        public int GetFoeCardValue() {
            return cardValue;
        }

        public String GetCardType() {
            return type;
        }

    }
