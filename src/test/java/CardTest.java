import exceptions.card.InvalidCardException;
import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardTest {

    private final Card joker = new Card(Suit.JOKER, -1);
    private final Card ace = new Card(Suit.HEART, 1);
    private final Card two = new Card(Suit.HEART, 2);
    private final Card seven = new Card(Suit.HEART, 7);
    private final Card eight = new Card(Suit.HEART, 8);
    private final Card king = new Card(Suit.HEART, 13);

    public CardTest() throws InvalidCardException {
    }

    @Test(expected = InvalidCardException.class)
    public void testInvalidCardCreation() throws InvalidCardException {
        new Card(null, 0);
    }

    @Test
    public void testCard_CardCreation() throws InvalidCardException {
        Card twoOfHearts = new Card(Suit.HEART, 2);

        assertEquals(Suit.HEART, twoOfHearts.getSuit());
        assertEquals(2, twoOfHearts.getRank());

        assertEquals(Suit.JOKER, joker.getSuit());
        assertEquals(-1, joker.getRank());

        assertEquals(twoOfHearts, twoOfHearts);
        assertNotEquals(null, twoOfHearts);

        assertThrows(InvalidCardException.class, () -> new Card(Suit.HEART, 100));

        System.out.println(twoOfHearts);
        System.out.println(joker);
        System.out.println(twoOfHearts.hashCode());
        System.out.println(joker.hashCode());
    }

    @Test
    public void testCard_CardPoints() {
        assertEquals(50, joker.getPoints());
        assertEquals(20, ace.getPoints());
        assertEquals(5, two.getPoints());
        assertEquals(5, seven.getPoints());
        assertEquals(10, eight.getPoints());
        assertEquals(10, king.getPoints());
    }

    @Test
    public void testCardEquality() throws InvalidCardException {
        Card diamondTwo = new Card(Suit.DIAMOND, 2);
        Card dupeTwo = new Card(Suit.HEART, 2);

        assertEquals(two, dupeTwo);
        assertNotEquals(two, diamondTwo);
        assertNotEquals(two, null);
    }
}
