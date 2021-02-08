package controller;

import model.Card;
import model.Hand;
import model.Suit;

import java.util.ArrayList;

public class GameController {
    /*
    TODO: Rules to implement
        1. Can't have a Tercia with more than one Joker
        2. Can't have a run with two Jokers next to each other
        3. Can't discard a Joker
    TODO: Maybe make a Tercia checker class to take it out of GameController?
     */
    public boolean checkHandForWinCondition(Hand hand, int round) {
        switch (round) {
            case 6:
                return checkForTercias(hand, 2);
            case 7:
//                return checkForTerciaRun(hand);
            case 8:
//                return checkForTwoRuns(hand);
            case 9:
                return checkForTercias(hand, 3);
            case 10:
//                return checkForTwoTerciasOneRun(hand);
            case 11:
//                return checkForOneTerciaTwoRuns(hand);
            case 12:
                return checkForTercias(hand, 4);
            case 13:
//                return checkForThreeRuns(hand);
            default:
//                throw new InvalidWinCondition(round + " is not a valid hand.");
        }
        return false;
    }

    private boolean checkForTercias(Hand hand, int terciaCount) {
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

    private int checkForPerfectMatch(Hand hand, Card currentCard, int terciaCount) {
        if (hand.getCardCountByRank(currentCard) >= 3) {
            terciaCount--;
        }
        return terciaCount;
    }

    private int checkForJokerMatches(Hand hand, int terciaCount) {
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

    private boolean canSkipCard(int lastSeenRank, Card card) {
        return card.getRank() == lastSeenRank || card.getSuit() == Suit.JOKER;
    }

    private int getCardCount(ArrayList<Card> cards, Card currentCard) {
        return (int) cards.stream().filter(predicateCard -> predicateCard.getRank() == currentCard.getRank()).count();
    }
}
