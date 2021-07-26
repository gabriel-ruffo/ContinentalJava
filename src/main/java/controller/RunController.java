package controller;

import exceptions.card.InvalidCardException;
import model.Card;
import model.Hand;
import model.Suit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class RunController {
    private static final Logger ERROR_LOGGER = LogManager.getLogger(RunController.class);

    /*
    Test:
        Ace to four
        jack to Ace
        one long run
        jokers
        split 8 count or larger run into two in necessary
     */
    public static boolean checkForRuns(Hand hand, int runCount) throws InvalidCardException {
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

        ERROR_LOGGER.error("No valid run found");
        return false;
    }

    private static int findPerfectMatch(List<Card> cards, int runCount, int jokerCount) {
        int cardRankPointer = cards.get(0).getRank();
        int runBuildCount = 1;

        for (Card card : cards) {
            // skip Ace consideration for incomplete runs
            if (card.getRank() == 1) {
                continue;
            }

            int cardDiff = card.getRank() - cardRankPointer;

            if (cardDiff == 1) {
                runBuildCount++;
                cardRankPointer = card.getRank();
            } else if (straightNeedsJoker(cardDiff, jokerCount)) {
                // build count +2 for card and Joker
                runBuildCount += 2;
                jokerCount--;
                cardRankPointer = card.getRank();
            } else if (possibleRoyalStraight(cardDiff)) { // maybe replace with straightneedsace
                // ace to jack -- may be a royal straight
                runBuildCount += 2;
                cardRankPointer = card.getRank();
            }
        }

        // Joker can fit end of run
        if (runBuildCount == 3 && jokerCount > 0) {
            runBuildCount++;
        }

        return runBuildCount >= 4 ? --runCount : runCount;
    }

    private static boolean possibleRoyalStraight(int cardDiff) {
        return cardDiff == 10;
    }

    private static boolean straightNeedsJoker(int cardDiff, int jokerCount) {
        return (cardDiff == 2) && jokerCount > 0;
    }

    // TODO: implement
//    private static boolean straightNeedsAce(int aceCount) {
//        return false;
//    }

    private static List<Suit> getDistinctSuits(Hand hand) {
        return hand.getHand().stream().map(Card::getSuit).distinct().collect(Collectors.toList());
    }

    private static List<Card> getCardsBySuit(Hand hand, Suit suit) {
        return hand.getHand().stream().filter(card -> card.getSuit() == suit).collect(Collectors.toList());
    }
}
