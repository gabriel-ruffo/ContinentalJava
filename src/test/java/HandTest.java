import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;

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
}
