package controller;

import model.Card;
import model.Hand;
import model.Suit;

import java.util.List;
import java.util.stream.Collectors;

public class RunController {

    /*
    Test:
        Ace to four
        jack to Ace
        one long run
        jokers
     */
    public static boolean checkForRuns(Hand hand, int runCount) {
        hand.sortHand();
        List<Suit> suits = getDistinctSuits(hand);
        for (Suit suit : suits) {
            List<Card> cards = getCardsBySuit(hand, suit);
            runCount = findPerfectMatch(cards, runCount);

            if (runCount == 0) {
                return true;
            }
        }

        return false;
    }

    private static List<Suit> getDistinctSuits(Hand hand) {
        return hand.getHand().stream().map(Card::getSuit).distinct().collect(Collectors.toList());
    }

    private static List<Card> getCardsBySuit(Hand hand, Suit suit) {
        return hand.getHand().stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
    }

    private static int findPerfectMatch(List<Card> cards, int runCount) {
        int cardRankPointer = cards.get(0).getRank();
        int runBuildCount = 1;

        for (Card card : cards) {
            if (card.getRank() == cardRankPointer + 1) {
                runBuildCount++;
                cardRankPointer = card.getRank();
            }
        }

        return runBuildCount >= 4 ? --runCount : runCount;
    }
}
