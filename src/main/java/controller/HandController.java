package controller;

import exceptions.card.InvalidCardException;
import exceptions.hand.InvalidHandException;
import model.Card;
import model.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HandController {
    private final HandAnalyzer handAnalyzer = new HandAnalyzer();

    public Card discardWorstCard(Hand hand, int round) throws InvalidHandException, InvalidCardException {
        Card discardCard = null;
        List<Card> handCopy = new ArrayList<>();
        handCopy.addAll(hand.getHand());

        handAnalyzer.generateHandComponents(hand, round);
        List<Card> terciaPossibles = handAnalyzer.getTerciaPossibles();
        List<Card> runPossibles = handAnalyzer.getRunPossibles();
        List<Card> flexCards = handAnalyzer.getFlexCards();

        handCopy.addAll(flexCards);
        handCopy.removeAll(terciaPossibles);
        handCopy.removeAll(runPossibles);

        if (handCopy.size() > 0) {
            discardCard = new Hand(handCopy).getHighestPointCard();
        } else {
            if (roundNeedsOnlyTercias(round)) {
                handAnalyzer.generateTerciaTypes();
            } else if (roundNeedsOnlyRuns(round)) {

            } else {

            }
        }
        hand.removeFromHand(discardCard);
        return discardCard;
    }

    private boolean roundNeedsOnlyTercias(int round) {
        return round == 6 || round == 9 || round == 12;
    }

    private boolean roundNeedsOnlyRuns(int round) {
        return round == 8 || round == 13;
    }
}
