import controller.GameController;
import exceptions.card.InvalidCardException;
import exceptions.hand.InvalidHandException;
import model.Card;
import model.Hand;
import model.Player;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        assertTrue(player.getHasWon());
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
        assertTrue(player.getHasWon());
    }

    @Test
    public void testGoDownTwoIncompletesWithJokers() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));

        Hand twoIncompleteTercias = new Hand(cards);
        Player player = new Player(0, twoIncompleteTercias, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertTrue(player.getHasWon());
    }

    @Test
    public void testGoDownPerfectAndOverflowNoExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));

        Hand perfectAndOverflow = new Hand(cards);
        Player player = new Player(0, perfectAndOverflow, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertTrue(player.getHasWon());
    }

    @Test
    public void testGoDownPerfectAndIncompleteWithJokerNoExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));

        Hand perfectAndIncomplete = new Hand(cards);
        Player player = new Player(0, perfectAndIncomplete, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertTrue(player.getHasWon());
    }

    @Test
    public void testGoDownOverflowAndIncompleteWithJokerNoExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));

        Hand overflowAndIncomplete = new Hand(cards);
        Player player = new Player(0, overflowAndIncomplete, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertTrue(player.getHasWon());
    }

    @Test
    public void testGoDownTwoPerfectWithExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 12));

        Hand twoPerfectTercias = new Hand(cards);
        Player player = new Player(0, twoPerfectTercias, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertFalse(player.getHasWon());
        assertEquals(new Card(Suit.CLUB, 12), player.getHand().getHand().get(0));
    }

    @Test
    public void testGoDownTwoOverflowsWithExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 11));
        cards.add(new Card(Suit.CLUB, 12));

        Hand twoOverflowTercias = new Hand(cards);
        Player player = new Player(0, twoOverflowTercias, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertFalse(player.getHasWon());
        assertEquals(new Card(Suit.CLUB, 11), player.getHand().getHand().get(0));
        assertEquals(new Card(Suit.CLUB, 12), player.getHand().getHand().get(1));
    }

    @Test
    public void testGoDownTwoIncompletesWithJokersWithExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 13));

        Hand twoIncompleteTercias = new Hand(cards);
        Player player = new Player(0, twoIncompleteTercias, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertFalse(player.getHasWon());
        assertEquals(new Card(Suit.CLUB, 13), player.getHand().getHand().get(0));
    }

    @Test
    public void testGoDownPerfectAndOverflowWithExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 11));

        Hand perfectAndOverflow = new Hand(cards);
        Player player = new Player(0, perfectAndOverflow, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertFalse(player.getHasWon());
        assertEquals(new Card(Suit.CLUB, 11), player.getHand().getHand().get(0));
    }

    @Test
    public void testGoDownPerfectAndIncompleteWithJokerWithExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 11));

        Hand perfectAndIncomplete = new Hand(cards);
        Player player = new Player(0, perfectAndIncomplete, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertFalse(player.getHasWon());
        assertEquals(new Card(Suit.CLUB, 11), player.getHand().getHand().get(0));
    }

    @Test
    public void testGoDownOverflowAndIncompleteWithJokerWithExtras() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.JOKER, -1));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 2));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 10));
        cards.add(new Card(Suit.CLUB, 11));

        Hand overflowAndIncomplete = new Hand(cards);
        Player player = new Player(0, overflowAndIncomplete, "player");

        gameController.goDown(player, 6);

        assertTrue(player.getHasGoneDown());
        assertFalse(player.getHasWon());
        assertEquals(new Card(Suit.CLUB, 11), player.getHand().getHand().get(0));
    }

    @Test
    public void testDiscardWhenOtherPlayersHaveGoneDown() throws InvalidCardException, InvalidHandException {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");

        List<Hand> player1DownedHands = new ArrayList<>();
        List<Card> player1CardsList = new ArrayList<>();
        player1CardsList.add(new Card(Suit.CLUB, 2));
        player1CardsList.add(new Card(Suit.CLUB, 2));
        player1CardsList.add(new Card(Suit.CLUB, 2));
        player1DownedHands.add(new Hand(player1CardsList));

        player1.setDownedHand(player1DownedHands);
        player1.setHasGoneDown(true);

        List<Card> player2CardsList = new ArrayList<>();
        player2CardsList.add(new Card(Suit.CLUB, 2));
        player2CardsList.add(new Card(Suit.CLUB, 5));
        player2CardsList.add(new Card(Suit.CLUB, 11));
        player2.setHand(new Hand(player2CardsList));

        player2.setHasGoneDown(true);

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        GameController downedPlayersGameController = new GameController(playerList);

        // will discard 2 and 5
        downedPlayersGameController.discardCard(player2, 6);

        assertEquals(1, player2.getHand().getHand().size());
    }

    @Test
    public void testGoDownWithProblematicHand() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 4));
        cards.add(new Card(Suit.DIAMOND, 4));
        cards.add(new Card(Suit.SPADE, 5));
        cards.add(new Card(Suit.DIAMOND, 5));
        cards.add(new Card(Suit.CLUB, 5));
        cards.add(new Card(Suit.HEART, 11));
        cards.add(new Card(Suit.CLUB, 11));
        cards.add(new Card(Suit.SPADE, 11));
        cards.add(new Card(Suit.CLUB, 13));

        Hand problematicHand = new Hand(cards);

        Player player = new Player(0, problematicHand, "player");

        gameController.goDown(player, 6);
    }

//    @Test
//    public void testTwoPerfectRuns() throws InvalidCardException {
//        ArrayList<Card> cards = new ArrayList<>();
//        cards.add(new Card(Suit.HEART, 2));
//        cards.add(new Card(Suit.HEART, 3));
//        cards.add(new Card(Suit.HEART, 4));
//        cards.add(new Card(Suit.HEART, 5));
//        cards.add(new Card(Suit.SPADE, 9));
//        cards.add(new Card(Suit.SPADE, 10));
//        cards.add(new Card(Suit.SPADE, 11));
//        cards.add(new Card(Suit.SPADE, 12));
//
//        Hand twoRuns = new Hand(cards);
//
//        Player player = new Player(0, twoRuns, "player");
//
//        assertTrue(gameController.checkHandForWinCondition(twoRuns, 8));
//
//        gameController.goDown(player, 8);
//
//        assertTrue(player.getHasGoneDown());
//        assertTrue(player.getHasWon());
//    }
}
