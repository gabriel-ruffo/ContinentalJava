import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class HandTest {

    @Test
    public void testHand_HandCreation() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 4));

        Hand emptyHand = new Hand();
        Hand preMadeHand = new Hand(cards);

        assertEquals(0, emptyHand.getHand().size());
        assertEquals(3, preMadeHand.getHand().size());
    }

    @Test
    public void testHand_AddToHand() {
        Hand hand = new Hand();
        assertEquals(0, hand.getHand().size());
        assertTrue(hand.addToHand(new Card(Suit.HEART, 2)));
        assertEquals(1, hand.getHand().size());

        assertFalse(hand.addToHand(new Card(Suit.HEART, -2)));
        assertEquals(1, hand.getHand().size());
        assertFalse(hand.addToHand(new Card(Suit.HEART, 14)));
        assertEquals(1, hand.getHand().size());
    }

    @Test
    public void testHand_RemoveFromHand() {
        Hand hand = new Hand();
        assertTrue(hand.addToHand(new Card(Suit.HEART, 2)));
        assertEquals(1, hand.getHand().size());
        assertTrue(hand.removeFromHand(new Card(Suit.HEART, 2)));
        assertEquals(0, hand.getHand().size());
        assertFalse(hand.removeFromHand(new Card(Suit.JOKER, -1)));
    }

    @Test
    public void testHand_SortHandNumeralsAndJoker() {
        ArrayList<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 5),
                new Card(Suit.CLUB, 4),
                new Card(Suit.DIAMOND, 12),
                new Card(Suit.SPADE, 1),
                new Card(Suit.JOKER, -1));
        Hand hand = new Hand(cards);
        assertEquals(hand.getHand().get(0).getRank(), 10);
        assertEquals(hand.getHand().get(1).getRank(), 5);
        assertEquals(hand.getHand().get(2).getRank(), 4);
        assertEquals(hand.getHand().get(3).getRank(), 12);
        assertEquals(hand.getHand().get(4).getRank(), 1);
        assertEquals(hand.getHand().get(5).getRank(), -1);
        assertEquals(hand.getPoints(), 100);

        hand.sortHand();
        assertEquals(hand.getHand().get(0).getRank(), -1);
        assertEquals(hand.getHand().get(1).getRank(), 1);
        assertEquals(hand.getHand().get(2).getRank(), 4);
        assertEquals(hand.getHand().get(3).getRank(), 5);
        assertEquals(hand.getHand().get(4).getRank(), 10);
        assertEquals(hand.getHand().get(5).getRank(), 12);
        assertEquals(hand.getPoints(), 100);
    }
}
