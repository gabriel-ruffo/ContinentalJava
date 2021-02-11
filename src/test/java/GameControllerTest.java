import controller.GameController;
import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameControllerTest {
    GameController gameController = new GameController();

    @Test
    public void test_FirstHandSuccessful() {
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
    public void test_FirstHandSuccessfulUnsorted() {
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
    public void test_FirstHandOneTercia() {
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
    public void test_roundSixThreePerfectTercias() {
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
    public void test_roundSixTwoPerfectTerciasOneJokerTercia() {
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
    public void test_roundSixOnePerfectTerciaTwoJokerTercias() {
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
    public void test_roundSixThreeJokerTercias() {
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
    public void test_roundSixThreeJokerOneTercia() {
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
    public void test_roundSixThreeJokerNoTercias() {
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
    public void test_roundSixNoTerciasNoJokers() {
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
    public void test_roundNineFourPerfectTercias() {
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
    public void test_roundNineFourJokerTercias() {
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
    public void test_roundNineThreePerfectTercias() {
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

}
