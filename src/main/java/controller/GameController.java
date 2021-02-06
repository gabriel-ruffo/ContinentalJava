package controller;

import model.Card;
import model.Hand;

import java.util.ArrayList;
import java.util.Comparator;

public class GameController {
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

    public boolean checkForTercias(Hand hand, int count) {
        hand.sortHand();
        ArrayList<Card> cards = hand.getHand();
        //TODO: implement

        return false;
    }
}
