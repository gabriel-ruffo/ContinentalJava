package controller;

import model.Card;
import model.Hand;
import model.Suit;

public class TerciaController {

    public static boolean checkForTercias(Hand hand, int terciaCount) {
        hand.sortHand();
        int lastSeenRank = 0;

        for (Card card : hand.getHand()) {
            // skip already seen cards or Jokers
            if (canSkipCard(lastSeenRank, card)) {
                continue;
            } else {
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

    private static int checkForPerfectMatch(Hand hand, Card currentCard, int terciaCount) {
        if (hand.getCardCountByRank(currentCard) >= 3) {
            terciaCount--;
        }
        return terciaCount;
    }

    private static int checkForJokerMatches(Hand hand, int terciaCount) {
        int jokerCount = hand.getCardCountBySuit(new Card(Suit.JOKER, -1));
        int lastSeenRank = 0;

        for (Card card : hand.getHand()) {
            if (card.getRank() == lastSeenRank || card.getSuit() == Suit.JOKER) {
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

    private static boolean canSkipCard(int lastSeenRank, Card card) {
        return card.getRank() == lastSeenRank || card.getSuit() == Suit.JOKER;
    }
}
