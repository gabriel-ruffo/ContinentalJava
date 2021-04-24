import controller.GameController;
import exceptions.card.InvalidCardException;
import model.Card;
import model.Hand;
import model.Player;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameControllerTest {
    private final GameController gameController = new GameController(null);

    @Test
    public void test_FirstHandSuccessful() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.SPADE, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.SPADE, 3));

        Hand twoTercias = new Hand(cards);

        assertTrue(gameController.checkHandForWinCondition(twoTercias, 6));
    }

    @Test
    public void test_FirstHandSuccessfulUnsorted() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.SPADE, 3));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.SPADE, 2));
        cards.add(new Card(Suit.HEART, 2));

        Hand twoTercias = new Hand(cards);

        assertTrue(gameController.checkHandForWinCondition(twoTercias, 6));
    }

    @Test
    public void test_FirstHandOneTercia() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.SPADE, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.SPADE, 4));

        Hand twoTercias = new Hand(cards);

        assertFalse(gameController.checkHandForWinCondition(twoTercias, 6));
    }

    @Test
    public void test_roundSixThreePerfectTercias() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.SPADE, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.SPADE, 3));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));

        Hand threeTercias = new Hand(cards);

        assertTrue(gameController.checkHandForWinCondition(threeTercias, 9));
    }

    @Test
    public void test_roundSixTwoPerfectTerciasOneJokerTercia() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.SPADE, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.SPADE, 3));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.JOKER, -1));

        Hand threeTercias = new Hand(cards);

        assertTrue(gameController.checkHandForWinCondition(threeTercias, 9));
    }

    @Test
    public void test_roundSixOnePerfectTerciaTwoJokerTercias() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.SPADE, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.JOKER, -1));

        Hand threeTercias = new Hand(cards);

        assertTrue(gameController.checkHandForWinCondition(threeTercias, 9));
    }

    @Test
    public void test_roundSixThreeJokerTercias() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.JOKER, -1));

        Hand threeTercias = new Hand(cards);

        assertTrue(gameController.checkHandForWinCondition(threeTercias, 9));
    }

    @Test
    public void test_roundSixThreeJokerOneTercia() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 1));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.JOKER, -1));

        Hand threeTercias = new Hand(cards);

        assertFalse(gameController.checkHandForWinCondition(threeTercias, 9));
    }

    @Test
    public void test_roundSixThreeJokerNoTercias() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 1));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 8));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.JOKER, -1));

        Hand threeTercias = new Hand(cards);

        assertFalse(gameController.checkHandForWinCondition(threeTercias, 9));
    }

    @Test
    public void test_roundSixNoTerciasNoJokers() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 1));
        cards.add(new Card(Suit.DIAMOND, 9));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 8));
        cards.add(new Card(Suit.CLUB, 11));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.CLUB, 12));

        Hand threeTercias = new Hand(cards);

        assertFalse(gameController.checkHandForWinCondition(threeTercias, 9));
    }

    @Test
    public void test_roundNineFourPerfectTercias() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.SPADE, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.SPADE, 3));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.SPADE, 5));

        Hand fourTercias = new Hand(cards);

        assertTrue(gameController.checkHandForWinCondition(fourTercias, 12));
    }

    @Test
    public void test_roundNineFourJokerTercias() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.JOKER, -1));

        Hand fourTercias = new Hand(cards);

        assertTrue(gameController.checkHandForWinCondition(fourTercias, 12));
    }

    @Test
    public void test_roundNineThreePerfectTercias() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.SPADE, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.SPADE, 3));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 4));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.SPADE, 6));

        Hand fourTercias = new Hand(cards);

        assertFalse(gameController.checkHandForWinCondition(fourTercias, 12));
    }

    @Test
    public void testGoDownPerfectHand() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));

        Hand twoPerfectTercias = new Hand(cards);
        Player player = new Player(0, twoPerfectTercias, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
    }

    @Test
    public void testGoDownTwoOverflows() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));

        Hand twoOverflowTercias = new Hand(cards);
        Player player = new Player(0, twoOverflowTercias, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
    }

    @Test
    public void testGoDownTwoIncompletesWithJokers() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));;

        Hand twoIncompleteTercias = new Hand(cards);
        Player player = new Player(0, twoIncompleteTercias, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
    }

}
