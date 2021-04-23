import controller.HandController;
import exceptions.card.InvalidCardException;
import exceptions.hand.InvalidHandException;
import model.Card;
import model.Hand;
import model.Suit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class HandControllerTest {
    private final HandController handController = new HandController();

    @Test
    public void testDiscardTenPointFromUselessHand() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 7),
                new Card(Suit.SPADE, 10),
                new Card(Suit.SPADE, 13));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(10, discardedCard.getPoints());
    }

    @Test
    public void testDiscardBug() throws InvalidCardException, InvalidHandException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.SPADE, 2),
                new Card(Suit.DIAMOND, 2),
                new Card(Suit.CLUB, 5),
                new Card(Suit.DIAMOND, 7),
                new Card(Suit.DIAMOND, 7),
                new Card(Suit.HEART, 11),
                new Card(Suit.SPADE, 11));
        Hand hand = new Hand(cards);

        System.out.println(hand);
        cards.add(new Card(Suit.DIAMOND, 5));
        System.out.println(hand);
        Card discardedCard = handController.discardWorstCard(hand, 6);
        System.out.println(discardedCard);
        System.out.println(hand);
        assertEquals(10, discardedCard.getPoints());
    }

    @Test
    public void testDiscardFivePointFromUselessHand() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 7));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(5, discardedCard.getPoints());
    }

    @Test
    public void testDiscardAceFromUselessFivePointers() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 3),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 7));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(20, discardedCard.getPoints());
    }

    @Test
    public void testDiscardAceFromUselessTenPointers() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 9),
                new Card(Suit.HEART, 10));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(20, discardedCard.getPoints());
    }

    @Test
    public void testDiscardFromHandWithTerciasAndUselessCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(20, discardedCard.getPoints());
    }

    @Test
    public void testDiscardFromOverflowAndNoUselessCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(10, discardedCard.getRank());
        assertEquals(10, discardedCard.getPoints());
    }

    @Test
    public void testDiscardFromTwoOverflowsAndNoUselessCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 8),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(8, discardedCard.getRank());
    }

    @Test
    public void testDiscardFromThreeOverflowsAndNoUselessCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 9),
                new Card(Suit.HEART, 9),
                new Card(Suit.HEART, 9),
                new Card(Suit.HEART, 9),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);
        assertEquals(9, discardedCard.getRank());
    }

    @Test
    public void testDiscardFromOneIncompleteAndNoUselessCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(2, discardedCard.getRank());
    }

    @Test
    public void testDiscardFromTwoIncompletesAndNoUselessCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 9),
                new Card(Suit.HEART, 9));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(9, discardedCard.getRank());
    }

    @Test
    public void testDiscardFromPerfectAndNoUselessCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(2, discardedCard.getRank());
    }

    @Test
    public void testDiscardFromTwoPerfectsAndNoUselessCards() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 2),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(10, discardedCard.getRank());
    }

    @Test
    public void testDiscardFromOneOfEach() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(10, discardedCard.getRank());
    }

    @Test
    public void testDiscardFromOverflowAndPerfect() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 10));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(10, discardedCard.getRank());
    }

    @Test
    public void testDiscardFromIncompleteAndPerfect() throws InvalidHandException, InvalidCardException {
        List<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 5),
                new Card(Suit.HEART, 5));
        Hand hand = new Hand(cards);

        Card discardedCard = handController.discardWorstCard(hand, 6);

        assertEquals(1, discardedCard.getRank());
    }
}
