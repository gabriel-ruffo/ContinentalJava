package model;

import java.util.*;
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


    /*
    1. If there are distinct cards, they are unneeded
     */
    public Card discardWorstCard(int round) {
//        if (round == 6) {
//            if (distinctCards.size() != 0) {
//                // there exist extra cards
//                removeFromHand(getHighestPointCard(distinctCards));
//            } else {
//                // check if there are extra cards in existing tercia (4+ cards)
//                // if not ^ toss highest card
//            }
//        }
        return null;
    }

    private Card getHighestPointCard(List<Card> cards) {
        return cards.stream().max(Comparator.comparingInt(Card::getPoints)).get();
    }

    @Override
    public String toString() {
        return "Hand=" + hand.toString() + '}';
    }
}
