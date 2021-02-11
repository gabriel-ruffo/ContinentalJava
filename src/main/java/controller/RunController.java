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
        split 8 count or larger run into two in necessary
     */
    public static boolean checkForRuns(Hand hand, int runCount) {
        int jokerCount = hand.getCardCountBySuit(new Card(Suit.JOKER, -1));
        hand.sortHand();
        List<Suit> suits = getDistinctSuits(hand);

        for (Suit suit : suits) {
            if (suit == Suit.JOKER) {
                continue;
            }
            List<Card> cards = getCardsBySuit(hand, suit);
            runCount = findPerfectMatch(cards, runCount, jokerCount);

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

    private static int findPerfectMatch(List<Card> cards, int runCount, int jokerCount) {
        int cardRankPointer = cards.get(0).getRank();
        int runBuildCount = 1;

        for (Card card : cards) {
            int cardDiff = card.getRank() - cardRankPointer;

            if (cardDiff == 1) {
                runBuildCount++;
                cardRankPointer = card.getRank();
            } else if ((cardDiff == 2) && jokerCount > 0) {
                // build count +2 for card and Joker
                runBuildCount += 2;
                jokerCount--;
                cardRankPointer = card.getRank();
            }
        }

        // Joker can fit end of run
        if (runBuildCount == 3 && jokerCount > 0) {
            runBuildCount++;
        }

        return runBuildCount >= 4 ? --runCount : runCount;
    }
}
