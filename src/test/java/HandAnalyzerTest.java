import controller.HandAnalyzer;
import exceptions.card.InvalidCardException;
import exceptions.hand.InvalidHandException;
import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HandAnalyzerTest {

    @Test(expected = InvalidHandException.class)
    public void testNullHand() throws InvalidHandException {
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(null);

        assertEquals(0, handAnalyzer.getTerciaPossibles().size());
        assertEquals(0, handAnalyzer.getRunPossibles().size());
        assertEquals(0, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testRun() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 7),
                new Card(Suit.SPADE, 10),
                new Card(Suit.SPADE, 13));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(hand);

        assertEquals(4, handAnalyzer.getRunPossibles().size());
        assertEquals(0, handAnalyzer.getTerciaPossibles().size());
        assertEquals(0, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testTwoTercias() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.CLUB, 2),
                new Card(Suit.DIAMOND, 2),
                new Card(Suit.SPADE, 3),
                new Card(Suit.SPADE, 3),
                new Card(Suit.DIAMOND, 5));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(hand);

        assertEquals(5, handAnalyzer.getTerciaPossibles().size());
        assertEquals(0, handAnalyzer.getRunPossibles().size());
        assertEquals(0, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testTerciaAndRun() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 7),
                new Card(Suit.SPADE, 2),
                new Card(Suit.SPADE, 7));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(hand);

        assertEquals(4, handAnalyzer.getRunPossibles().size());
        assertEquals(4, handAnalyzer.getTerciaPossibles().size());
        assertEquals(2, handAnalyzer.getFlexCards().size());
    }
}
