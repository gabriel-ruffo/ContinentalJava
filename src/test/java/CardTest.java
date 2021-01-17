import model.Card;
import model.Suit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardTest {

    @Test
    public void testCard_CardCreation() {
        Card twoOfHearts = new Card(Suit.HEART, 2);
        Card joker = new Card(Suit.JOKER, -1);

        assertEquals(Suit.HEART, twoOfHearts.getSuit());
        assertEquals(2, twoOfHearts.getRank());

        assertEquals(Suit.JOKER, joker.getSuit());
        assertEquals(-1, joker.getRank());

        System.out.println(twoOfHearts.toString());
        System.out.println(joker.toString());
    }
}
