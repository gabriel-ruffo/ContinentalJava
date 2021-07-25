package model;

import exceptions.card.InvalidCardException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {

    private final List<Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public Hand(List<Card> hand) {
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
            if (handCard.equals(card)) {
                hand.remove(handCard);
                return true;
            }
        }
        return false;
    }

    public void removeCardsFromHand(List<Card> cards) {
        hand.removeAll(cards);
    }

    public void sortHand() {
        hand.sort(Comparator.comparing(Card::getRank));
    }

    public int getPoints() {
        return hand.stream().map(Card::getPoints).reduce(0, Integer::sum);
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getCardCountByRank(Card card) {
        return (int) hand.stream().filter(predicateCard -> predicateCard.getRank() == card.getRank()).count();
    }

    public int getCardCountBySuit(Card card) {
        return (int) hand.stream().filter(predicateCard -> predicateCard.getSuit() == card.getSuit()).count();
    }

    public int getJokerCount() throws InvalidCardException {
        return getCardCountBySuit(new Card(Suit.JOKER, -1));
    }

    public List<Card> getCardCollectionByRank(int rank) {
        return hand.stream().filter(card -> card.getRank() == rank).collect(Collectors.toList());
    }

    public List<Card> getCardCollectionBySuit(Suit suit) {
        return hand.stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
    }

    public Card getHighestPointCard() {
        return hand.stream().max(Comparator.comparingInt(Card::getPoints)).get();
    }

    @Override
    public String toString() {
        return "Hand=" + hand.toString() + '}';
    }
}
