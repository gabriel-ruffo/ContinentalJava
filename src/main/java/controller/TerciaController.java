package controller;

import exceptions.InvalidCardException;
import model.Card;
import model.Hand;
import model.Suit;

public class TerciaController {

    /*
    Rules:
        if have 6 or more of same card, be allowed to split it
     */
    public static boolean checkForTercias(Hand hand, int terciaCount) throws InvalidCardException {
        if (terciaCount == 0) {
            return true;
        }

        hand.sortHand();
        int lastSeenRank = 0;

        for (Card card : hand.getHand()) {
            // skip already seen cards or Jokers
            if (canSkipCard(lastSeenRank, card)) {
                continue;
            } else {
                // check for perfect matches first
                terciaCount = checkForPerfectMatch(hand, card, terciaCount);
                if (terciaCount == 0) {
                    return true;
                }
            }
            lastSeenRank = card.getRank();
        }

        // if still need matches, check for joker matches
        if (terciaCount > 0) {
            terciaCount = checkForJokerMatches(hand, terciaCount);
        }

        return terciaCount == 0;
    }

    // a perfect match is 3 or more of the same card
    private static int checkForPerfectMatch(Hand hand, Card currentCard, int terciaCount) {
        if (hand.getCardCountByRank(currentCard) >= 3) {
            terciaCount--;
        }
        return terciaCount;
    }

    // a joker match is a pair with a joker
    private static int checkForJokerMatches(Hand hand, int terciaCount) throws InvalidCardException {
        int jokerCount = hand.getCardCountBySuit(new Card(Suit.JOKER, -1));
        int lastSeenRank = 0;

        for (Card card : hand.getHand()) {
            if (canSkipCard(lastSeenRank, card)) {
                continue;
            } else {
                int cardsOfRankCount = hand.getCardCountByRank(card);
                if (cardsOfRankCount == 2 && jokerCount > 0) {
                    jokerCount--;
                    terciaCount--;
                }
            }
            lastSeenRank = card.getRank();
        }
        return terciaCount;
    }

    // if have already seen given card or looking at joker return true
    private static boolean canSkipCard(int lastSeenRank, Card card) {
        return card.getRank() == lastSeenRank || card.getSuit() == Suit.JOKER;
    }
}
