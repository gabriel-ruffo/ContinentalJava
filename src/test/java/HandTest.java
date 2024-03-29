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

import static org.junit.Assert.*;

public class HandTest {

    @Test
    public void testHand_HandCreation() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(new Card(Suit.HEART, 2));
        cards.add(new Card(Suit.HEART, 3));
        cards.add(new Card(Suit.HEART, 4));

        Hand emptyHand = new Hand();
        Hand preMadeHand = new Hand(cards);

        assertEquals(0, emptyHand.getHand().size());
        assertEquals(3, preMadeHand.getHand().size());
    }

    @Test
    public void testHand_RemoveFromHand() throws InvalidCardException {
        Hand hand = new Hand();
        hand.addToHand(new Card(Suit.HEART, 2));
        assertEquals(1, hand.getHand().size());
        assertTrue(hand.removeFromHand(new Card(Suit.HEART, 2)));
        assertEquals(0, hand.getHand().size());
        assertFalse(hand.removeFromHand(new Card(Suit.JOKER, -1)));
    }

    @Test
    public void testHand_SortHandNumeralsAndJoker() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 10),
                new Card(Suit.HEART, 5),
                new Card(Suit.CLUB, 4),
                new Card(Suit.DIAMOND, 12),
                new Card(Suit.SPADE, 1),
                new Card(Suit.JOKER, -1));
        Hand hand = new Hand(cards);
        assertEquals(hand.getHand().get(0).getRank(), 10);
        assertEquals(hand.getHand().get(1).getRank(), 5);
        assertEquals(hand.getHand().get(2).getRank(), 4);
        assertEquals(hand.getHand().get(3).getRank(), 12);
        assertEquals(hand.getHand().get(4).getRank(), 1);
        assertEquals(hand.getHand().get(5).getRank(), -1);
        assertEquals(hand.getPoints(), 100);

        hand.sortHand();
        assertEquals(hand.getHand().get(0).getRank(), -1);
        assertEquals(hand.getHand().get(1).getRank(), 1);
        assertEquals(hand.getHand().get(2).getRank(), 4);
        assertEquals(hand.getHand().get(3).getRank(), 5);
        assertEquals(hand.getHand().get(4).getRank(), 10);
        assertEquals(hand.getHand().get(5).getRank(), 12);
        assertEquals(hand.getPoints(), 100);
    }

    @Test
    public void testHand_GetCardCountByRank() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 1),
                new Card(Suit.CLUB, 1),
                new Card(Suit.DIAMOND, 2),
                new Card(Suit.SPADE, 2),
                new Card(Suit.CLUB, 3));
        Hand hand = new Hand(cards);

        assertEquals(3, hand.getCardCountByRank(new Card(Suit.HEART, 1)));
        assertEquals(2, hand.getCardCountByRank(new Card(Suit.DIAMOND, 2)));
        assertEquals(1, hand.getCardCountByRank(new Card(Suit.CLUB, 3)));
        assertEquals(0, hand.getCardCountByRank(new Card(Suit.SPADE, 4)));
    }

    @Test
    public void testHand_GetCardCountBySuit() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 1),
                new Card(Suit.CLUB, 1),
                new Card(Suit.DIAMOND, 2),
                new Card(Suit.CLUB, 3));
        Hand hand = new Hand(cards);

        assertEquals(2, hand.getCardCountBySuit(new Card(Suit.HEART, 1)));
        assertEquals(1, hand.getCardCountBySuit(new Card(Suit.DIAMOND, 1)));
        assertEquals(2, hand.getCardCountBySuit(new Card(Suit.CLUB, 1)));
        assertEquals(0, hand.getCardCountBySuit(new Card(Suit.SPADE, 1)));
    }

    @Test
    public void testHand_DiscardWorstCardInRoundSix() throws InvalidCardException, InvalidHandException {
        HandController handController = new HandController();
        ArrayList<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 2),
                new Card(Suit.CLUB, 3),
                new Card(Suit.DIAMOND, 3),
                new Card(Suit.DIAMOND, 4),
                new Card(Suit.CLUB, 5));
        Hand hand = new Hand(cards);

        assertEquals(6, hand.getHand().size());
        assertEquals(45, hand.getPoints());

        handController.discardWorstCard(hand, 6);

        assertEquals(5, hand.getHand().size());
        assertEquals(25, hand.getPoints());
    }

    @Test
    public void testHandDoNotDiscardJoker() throws InvalidCardException, InvalidHandException {
        HandController handController = new HandController();
        ArrayList<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.CLUB, 5),
                new Card(Suit.JOKER, -1));
        Hand hand = new Hand(cards);

        assertEquals(3, hand.getHand().size());
        assertEquals(75, hand.getPoints());

        handController.discardWorstCard(hand, 6);

        assertEquals(2, hand.getHand().size());
        assertEquals(55, hand.getPoints());
    }

    @Test
    public void testGetCardCollectionBySuit() throws InvalidCardException {
        ArrayList<Card> cards = new ArrayList<>();
        Collections.addAll(cards,
                new Card(Suit.HEART, 1),
                new Card(Suit.HEART, 2),
                new Card(Suit.CLUB, 3),
                new Card(Suit.DIAMOND, 3),
                new Card(Suit.DIAMOND, 4),
                new Card(Suit.CLUB, 5));
        Hand hand = new Hand(cards);

        List<Card> hearts = hand.getCardCollectionBySuit(Suit.HEART);
        List<Card> clubs = hand.getCardCollectionBySuit(Suit.CLUB);
        List<Card> spades = hand.getCardCollectionBySuit(Suit.SPADE);

        assertEquals(hearts.size(), 2);
        assertTrue(hearts.contains(new Card(Suit.HEART, 1)));
        assertTrue(hearts.contains(new Card(Suit.HEART, 2)));

        assertEquals(clubs.size(), 2);
        assertTrue(clubs.contains(new Card(Suit.CLUB, 3)));
        assertTrue(clubs.contains(new Card(Suit.CLUB, 5)));

        assertEquals(spades.size(), 0);
    }

}
