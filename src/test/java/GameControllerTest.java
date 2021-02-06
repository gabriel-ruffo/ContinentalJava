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
}
