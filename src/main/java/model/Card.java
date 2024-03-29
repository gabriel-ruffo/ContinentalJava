package model;

import exceptions.card.InvalidCardException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class Card {

    private final Suit suit;
    private final int rank;

    public Card(Suit suit, int rank) throws InvalidCardException {
        if (suit == null) {
            Logger CARD_LOGGER = LogManager.getLogger(Card.class);
            CARD_LOGGER.error("Card was attempted to be created with a null suit value.");
            throw new InvalidCardException("Suit can't be null.");
        } else {
            this.suit = suit;
        }

        if (!validate(rank)) {
            Logger CARD_LOGGER = LogManager.getLogger(Card.class);
            CARD_LOGGER.error("Card was attempted to be created with an invalid rank value.");
            throw new InvalidCardException("Rank must be between 1, 13, and -1 for Jokers.");
        } else {
            this.rank = rank;
        }
    }

    private boolean validate(int rank) {
        return rank >= -1 && rank <= 13 && rank != 0;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }

    public int getPoints() {
        if (rank == -1) {
            return 50;
        } else if (isBetween(rank, 2, 7)) {
            return 5;
        } else if (isBetween(rank, 8, 13)) {
            return 10;
        } else {
            return 20;
        }
    }

    private boolean isBetween(int num, int low, int high) {
        return low <= num && num <= high;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (rank == 1) {
            stringBuilder.append("ACE");
        } else if (rank == 11) {
            stringBuilder.append("JACK");
        } else if (rank == 12) {
            stringBuilder.append("QUEEN");
        } else if (rank == 13) {
            stringBuilder.append("KING");
        } else if (rank == -1) {
            stringBuilder.append("JOKER");
            return stringBuilder.toString();
        } else {
            stringBuilder.append(rank);
        }

        stringBuilder.append(" of ").append(suit).append("S");

        return stringBuilder.toString();
    }
}
