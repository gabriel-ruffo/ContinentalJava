import controller.RunController;
import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;

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
}
