import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.player.InvalidPlayerException;
import model.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DeckTest {

    @Test
    public void testDeck_DeckCreation() throws InvalidCardException, InvalidDeckException {
        Deck deck = new Deck();
        assertThrows(InvalidDeckException.class, deck::getCard);
        assertThrows(InvalidDeckException.class, deck::peekCard);
        deck.makeDeck();

        assertEquals(54, deck.getDeck().size());
        assertEquals(new Card(Suit.DIAMOND, 1), deck.peekCard());
        assertEquals(new Card(Suit.DIAMOND, 1), deck.getCard());
        System.out.println(deck.getDeck());
        deck.reinitialize();
    }

    @Test
    public void testDeck_DeckShuffle() throws InvalidCardException {
        Deck deck = new Deck();
        deck.makeDeck();
        deck.shuffle();
        System.out.println(deck.getDeck());
    }

    @Test
    public void testDealCardToPlayer() throws InvalidCardException, InvalidPlayerException, InvalidDeckException {
        Player player = new Player(0, new Hand(), "player");
        Deck deck = new Deck();

        assertThrows(InvalidDeckException.class, () -> {
            deck.dealToPlayer(player, 1);
        });

        deck.makeDeck();

        assertThrows(InvalidPlayerException.class, () -> {
            deck.dealToPlayer(null, 6);
        });

        deck.dealToPlayer(player, 6);
    }

}
