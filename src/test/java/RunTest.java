import controller.RunController;
import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RunTest {

    @Test
    public void testRunNoJokers() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.CLUB, 5));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testInvalidRun() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.CLUB, 6));

        Hand run = new Hand(cards);

        assertFalse(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testTwoRunsNoJokers() {
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
    public void testRunWithJokerInMiddle() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 5));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testRunWithJokerAtEnd() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.JOKER, -1));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }

    @Test
    public void testRunWithDuplicateCards() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.CLUB, 5));

        Hand run = new Hand(cards);

        assertTrue(RunController.checkForRuns(run, 1));
    }
}
