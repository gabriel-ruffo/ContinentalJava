import controller.RunController;
import exceptions.card.InvalidCardException;
import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RunTest {

    @Test
    public void testRunNoJokers() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.CLUB, 5));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testInvalidRun() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.CLUB, 6));

        Hand run = new Hand(cards);

        assertFalse(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testTwoRunsNoJokers() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.CLUB, 5));
        cards.add(new Card(Suit.DIAMOND, 2));
        cards.add(new Card(Suit.DIAMOND, 3));
        cards.add(new Card(Suit.DIAMOND, 4));
        cards.add(new Card(Suit.DIAMOND, 5));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 2));
    }

    @Test
    public void testRunWithJokerInMiddle() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 5));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testRunWithJokerAtEnd() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.JOKER, -1));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testRunWithDuplicateCards() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.CLUB, 5));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testRunAceToFour() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testRunJackToAce() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 11));
        cards.add(new Card(Suit.CLUB, 12));
        cards.add(new Card(Suit.CLUB, 13));
        cards.add(new Card(Suit.CLUB, 1));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testBothAceCornerCasesDifferentSuits() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));

        cards.add(new Card(Suit.DIAMOND, 11));
        cards.add(new Card(Suit.DIAMOND, 12));
        cards.add(new Card(Suit.DIAMOND, 13));
        cards.add(new Card(Suit.DIAMOND, 1));
        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 2));
    }

//    @Test
//    public void testBothAceCornerCasesSameSuits() {
//        ArrayList<Card> cards = new ArrayList<>();
//        cards.add(new Card(Suit.CLUB, 1));
//        cards.add(new Card(Suit.CLUB, 2));
//        cards.add(new Card(Suit.CLUB, 3));
//        cards.add(new Card(Suit.CLUB, 4));
//
//        cards.add(new Card(Suit.CLUB, 11));
//        cards.add(new Card(Suit.CLUB, 12));
//        cards.add(new Card(Suit.CLUB, 13));
//        cards.add(new Card(Suit.CLUB, 1));
//        Hand run = new Hand(cards);
//
//        assertTrue(RunController.checkForRuns(run, 2));
//    }
//
//    @Test
//    public void testLongRunCanBeOneOrTwoRuns() {
//        ArrayList<Card> cards = new ArrayList<>();
//        cards.add(new Card(Suit.CLUB, 1));
//        cards.add(new Card(Suit.CLUB, 2));
//        cards.add(new Card(Suit.CLUB, 3));
//        cards.add(new Card(Suit.CLUB, 4));
//        cards.add(new Card(Suit.CLUB, 5));
//        cards.add(new Card(Suit.CLUB, 6));
//        cards.add(new Card(Suit.CLUB, 7));
//        cards.add(new Card(Suit.CLUB, 8));
//
//        Hand run = new Hand(cards);
//
//        assertTrue(RunController.checkForRuns(run, 1));
//        assertTrue(RunController.checkForRuns(run, 2));
//    }
}
