package model;

import java.util.ArrayList;

public class Hand {

    private final ArrayList<Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public Hand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public boolean addToHand(Card card) {
        if (card.validate()) {
            hand.add(card);
            return true;
        }
        return false;
    }

    public boolean removeFromHand(Card card) {
        for (Card handCard : hand) {
            if (handCard.getRank() == card.getRank() && handCard.getSuit() == card.getSuit()) {
                hand.remove(handCard);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
}
