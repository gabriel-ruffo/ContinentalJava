package controller;

import exceptions.card.InvalidCardException;
import model.Hand;

public class GameController {
    /*
    TODO: Rules to implement
        1. Can't have a Tercia with more than one Joker
        2. Can't have a run with two Jokers next to each other
        3. Can't discard a Joker
     */
    public boolean checkHandForWinCondition(Hand hand, int round) throws InvalidCardException {
        switch (round) {
            case 6:
                return TerciaController.checkForTercias(hand, 2);
            case 7:
//                return checkForTerciaRun(hand);
            case 8:
                return RunController.checkForRuns(hand, 2);
            case 9:
                return TerciaController.checkForTercias(hand, 3);
            case 10:
//                return checkForTwoTerciasOneRun(hand);
            case 11:
//                return checkForOneTerciaTwoRuns(hand);
            case 12:
                return TerciaController.checkForTercias(hand, 4);
            case 13:
//                return checkForThreeRuns(hand);
            default:
//                throw new InvalidWinCondition(round + " is not a valid hand.");
        }
        return false;
    }
}
