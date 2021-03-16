import controller.TerciaController;
import exceptions.card.InvalidCardException;
import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TerciaTest {

    @Test
    public void testSuccessfulPlainTercia() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));

        Hand tercia = new Hand(cards);
        assertTrue(TerciaController.checkForTercias(tercia, 1));
    }

    @Test
    public void testSuccessfulUnsortedTercia() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 4));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 5));
        cards.add(new Card(Suit.CLUB, 2));

        Hand unsortedTercia = new Hand(cards);
        assertTrue(TerciaController.checkForTercias(unsortedTercia, 1));
    }

    @Test
    public void testSuccessfulOneTerciaFromTwo() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 3));

        Hand unsortedTercia = new Hand(cards);
        assertTrue(TerciaController.checkForTercias(unsortedTercia, 1));
        assertTrue(TerciaController.checkForTercias(unsortedTercia, 2));
    }

    @Test
    public void testSuccessfulOneTerciaWithJoker() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.JOKER, -1));

        Hand terciaWithJoker = new Hand(cards);

        assertTrue(TerciaController.checkForTercias(terciaWithJoker, 1));
    }

    @Test
    public void testTwoPossibleTerciasWithOneJoker() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.DIAMOND, 3));
        cards.add(new Card(Suit.DIAMOND, 3));
        cards.add(new Card(Suit.JOKER, -1));

        Hand terciaWithJoker = new Hand(cards);

        assertTrue(TerciaController.checkForTercias(terciaWithJoker, 1));
    }

    @Test
    public void testNoTerciaWithOneJoker() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.JOKER, -1));

        Hand terciaWithJoker = new Hand(cards);

        assertFalse(TerciaController.checkForTercias(terciaWithJoker, 1));
    }

    @Test
    public void testHigherCountThanTercias() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));

        Hand tercia = new Hand(cards);
        assertFalse(TerciaController.checkForTercias(tercia, 3));
    }

    @Test
    public void testInvalidTerciaCount() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));

        Hand tercia = new Hand(cards);
        assertFalse(TerciaController.checkForTercias(tercia, -1));
    }

    @Test
    public void testZeroTerciaCount() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));

        Hand tercia = new Hand(cards);
        assertTrue(TerciaController.checkForTercias(tercia, 0));
    }

    @Test
    public void testTerciaWithMoreThanThreeCards() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));

        Hand tercia = new Hand(cards);
        assertTrue(TerciaController.checkForTercias(tercia, 1));
    }

    @Test
    public void testInvalidTerciaWithTwoJokers() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.JOKER, -1));

        Hand terciaWithJokers = new Hand(cards);

        assertFalse(TerciaController.checkForTercias(terciaWithJokers, 1));
    }

    @Test
    public void testInvalidTerciaWithThreeJokers() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.JOKER, -1));

        Hand jokersTercia = new Hand(cards);

        assertFalse(TerciaController.checkForTercias(jokersTercia, 1));
    }
}
