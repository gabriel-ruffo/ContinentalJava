import exceptions.InvalidCardException;
import model.Deck;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class DeckTest {

    @Test
    public void testDeck_DeckCreation() throws InvalidCardException {
        Deck deck = new Deck();
        deck.makeDeck();

        assertEquals(54, deck.getDeck().size());
        System.out.println(deck.getDeck());
    }

    @Test
    public void testDeck_DeckShuffle() throws InvalidCardException {
        Deck deck = new Deck();
        deck.makeDeck();
        deck.shuffle();
        System.out.println(deck.getDeck());
    }
}
