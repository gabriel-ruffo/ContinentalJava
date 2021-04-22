package controller;

import exceptions.card.InvalidCardException;
import exceptions.hand.InvalidHandException;
import model.Card;
import model.Hand;
import model.Suit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class HandController {
    private final HandAnalyzer handAnalyzer = new HandAnalyzer();
    private final Logger HAND_CONTROLLER_LOGGER = LogManager.getLogger(HandController.class);

    public Card discardWorstCard(Hand hand, int round) throws InvalidHandException, InvalidCardException {
        // TODO: handle null card exception -- use Optionals
        Card discardCard = null;
        List<Card> handCopy = new ArrayList<>(hand.getHand());

        handAnalyzer.generateHandComponents(hand, round);

        handCopy.addAll(handAnalyzer.getFlexCards());
        handCopy.removeAll(handAnalyzer.getTerciaPossibles());
        handCopy.removeAll(handAnalyzer.getRunPossibles());
        handCopy.removeIf(card -> card.getSuit() == Suit.JOKER); // don't discard jokers

        // there exist unnecessary cards in hand
        if (handCopy.size() > 0) {
            discardCard = new Hand(handCopy).getHighestPointCard();
            HAND_CONTROLLER_LOGGER.info("Discarded card: " + discardCard.toString());
        } else {
            // all cards are necessary, get rid of next least useful card
            if (roundNeedsOnlyTercias(round)) {
                handAnalyzer.generateTerciaTypes(hand);
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
