package controller;

import exceptions.hand.InvalidHandException;
import model.Card;
import model.Hand;
import model.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HandAnalyzer {
    private final List<Card> terciaPossibles = new ArrayList<>();
    private final List<Card> runPossibles = new ArrayList<>();
    private final List<Card> flexCards = new ArrayList<>();

    public void generateHandComponents(Hand hand) throws InvalidHandException {
        if (hand == null) {
            throw new InvalidHandException("Hand can't be null");
        }
        generateTerciaComponent(hand);
        generateRunComponent(hand);
        generateFlexCardsComponent(hand);
    }

    public List<Card> getTerciaPossibles() {
        return terciaPossibles;
    }

    public List<Card> getRunPossibles() {
        return runPossibles;
    }

    public List<Card> getFlexCards() {
        return flexCards;
    }

    private void generateTerciaComponent(Hand hand) {
        for (Card card : hand.getHand()) {
            if (card.getSuit() == Suit.JOKER) {
                continue;
            }
            if (hand.getCardCountByRank(card) >= 2) {
                terciaPossibles.add(card);
            }
        }
    }

    private void generateRunComponent(Hand hand) {
        for (Suit suit : getDistinctSuits(hand)) {
            if (suit == Suit.JOKER) {
                continue;
            }
            List<Card> cards = getCardsBySuit(hand, suit);
            if (cards.size() > 1) {
                filterCardsByWeight(cards);
            }
        }
    }

    private void filterCardsByWeight(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            Card cardOne = cards.get(i);
            Card cardTwo = cards.get(i + 1);
            if (withinWeight(cardOne, cardTwo)) {
                addRunCardsIfNotPresent(cardOne);
                addRunCardsIfNotPresent(cardTwo);
            }
        }
    }

    private boolean withinWeight(Card cardOne, Card cardTwo) {
        int weight = getWeight(cardOne.getRank(), cardTwo.getRank());
        return weight > 0 && weight <= 2;
    }

    private int getWeight(int num, int num2) {
        return Math.abs(num - num2);
    }

    private void addRunCardsIfNotPresent(Card card) {
        if (!runPossibles.contains(card)) {
            runPossibles.add(card);
        }
    }

    private void generateFlexCardsComponent(Hand hand) {
        List<Card> jokers = hand.getHand().stream().
                filter(predicateCard -> predicateCard.getSuit() == Suit.JOKER)
                .collect(Collectors.toList());
        flexCards.addAll(jokers);

        for (Card terciaCard : terciaPossibles) {
            for (Card runCard : runPossibles) {
                if (terciaCard == runCard) {
                    flexCards.add(terciaCard);
                }
            }
        }
    }

    private List<Suit> getDistinctSuits(Hand hand) {
        return hand.getHand().stream().map(Card::getSuit).distinct().collect(Collectors.toList());
    }

    private List<Card> getCardsBySuit(Hand hand, Suit suit) {
        return hand.getHand().stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
    }


}
