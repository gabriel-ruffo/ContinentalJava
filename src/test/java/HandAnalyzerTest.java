import controller.HandAnalyzer;
import exceptions.card.InvalidCardException;
import exceptions.hand.InvalidHandException;
import model.Card;
import model.Hand;
import model.Player;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class HandAnalyzerTest {

    @Test(expected = InvalidHandException.class)
    public void testNullHand() throws InvalidHandException, InvalidCardException {
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(null, 7);

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
        handAnalyzer.generateHandComponents(hand, 8);

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
        handAnalyzer.generateHandComponents(hand, 6);

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
        handAnalyzer.generateHandComponents(hand, 7);

        assertEquals(4, handAnalyzer.getRunPossibles().size());
        assertEquals(4, handAnalyzer.getTerciaPossibles().size());
        assertEquals(2, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testRunUnsorted() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 7),
                new Card(Suit.HEART, 3),
                new Card(Suit.SPADE, 13),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 2),
                new Card(Suit.SPADE, 10)
                );
        Hand hand = new Hand(cards);
        hand.sortHand();
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(hand, 8);

        assertEquals(4, handAnalyzer.getRunPossibles().size());
        assertEquals(0, handAnalyzer.getTerciaPossibles().size());
        assertEquals(0, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testTerciaWithJoker() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.SPADE, 3),
                new Card(Suit.SPADE, 3),
                new Card(Suit.DIAMOND, 5),
                new Card(Suit.JOKER, -1));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(hand, 6);

        assertEquals(2, handAnalyzer.getTerciaPossibles().size());
        assertEquals(0, handAnalyzer.getRunPossibles().size());
        assertEquals(1, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testRunWithJoker() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.JOKER, -1),
                new Card(Suit.HEART, 7),
                new Card(Suit.SPADE, 10),
                new Card(Suit.SPADE, 13));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(hand, 8);

        assertEquals(2, handAnalyzer.getRunPossibles().size());
        assertEquals(0, handAnalyzer.getTerciaPossibles().size());
        assertEquals(1, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testTerciaAndRunWithJoker() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.JOKER, -1),
                new Card(Suit.HEART, 7),
                new Card(Suit.SPADE, 10),
                new Card(Suit.SPADE, 10),
                new Card(Suit.SPADE, 10),
                new Card(Suit.SPADE, 13));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        hand.sortHand();
        handAnalyzer.generateHandComponents(hand, 7);

        assertEquals(2, handAnalyzer.getRunPossibles().size());
        assertEquals(3, handAnalyzer.getTerciaPossibles().size());
        assertEquals(2, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testNoTerciasOrRuns() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.SPADE, 3),
                new Card(Suit.HEART, 7),
                new Card(Suit.SPADE, 10),
                new Card(Suit.SPADE, 13));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        hand.sortHand();
        handAnalyzer.generateHandComponents(hand, 7);

        assertEquals(0, handAnalyzer.getRunPossibles().size());
        assertEquals(0, handAnalyzer.getTerciaPossibles().size());
        assertEquals(0, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testTerciasAndRunsBeforeAndAfterAddingCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 5),
                new Card(Suit.SPADE, 2),
                new Card(Suit.SPADE, 7));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();
        handAnalyzer.generateHandComponents(hand, 7);

        assertEquals(3, handAnalyzer.getRunPossibles().size());
        assertEquals(2, handAnalyzer.getTerciaPossibles().size());
        assertEquals(1, handAnalyzer.getFlexCards().size());

        hand.addToHand(new Card(Suit.HEART, 7));
        handAnalyzer.generateHandComponents(hand, 7);

        assertEquals(4, handAnalyzer.getRunPossibles().size());
        assertEquals(4, handAnalyzer.getTerciaPossibles().size());
        assertEquals(2, handAnalyzer.getFlexCards().size());
    }

    @Test
    public void testCardHelpsPlayer() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 5),
                new Card(Suit.SPADE, 2),
                new Card(Suit.SPADE, 7));
        Hand hand = new Hand(cards);
        Player player = new Player(0, hand, "player");
        HandAnalyzer handAnalyzer = new HandAnalyzer();

        assertTrue(handAnalyzer.cardHelpsPlayer(player, new Card(Suit.HEART, 4), 8));
        assertTrue(handAnalyzer.cardHelpsPlayer(player, new Card(Suit.HEART, 4), 7));
        assertTrue(handAnalyzer.cardHelpsPlayer(player, new Card(Suit.HEART, 2), 6));
        assertFalse(handAnalyzer.cardHelpsPlayer(player, new Card(Suit.HEART, 2), 8));
    }

    @Test
    public void testGenerateRunTypesPerfectNoJoker() throws InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 4),
                new Card(Suit.HEART, 5),
                new Card(Suit.DIAMOND, 10),
                new Card(Suit.DIAMOND, 11),
                new Card(Suit.DIAMOND, 12),
                new Card(Suit.DIAMOND, 13));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();

        assertEquals(handAnalyzer.getPerfectRuns().size(), 0);

        handAnalyzer.generateRunTypes(hand);

        assertEquals(handAnalyzer.getPerfectRuns().size(), 2);
    }

    @Test
    public void testGenerateRunTypesPerfectRoyalNoJoker() throws InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 4),
                new Card(Suit.DIAMOND, 1),
                new Card(Suit.DIAMOND, 11),
                new Card(Suit.DIAMOND, 12),
                new Card(Suit.DIAMOND, 13));
        Hand hand = new Hand(cards);
        HandAnalyzer handAnalyzer = new HandAnalyzer();

        assertEquals(handAnalyzer.getPerfectRuns().size(), 0);

        handAnalyzer.generateRunTypes(hand);

        assertEquals(handAnalyzer.getPerfectRuns().size(), 2);
    }
}
