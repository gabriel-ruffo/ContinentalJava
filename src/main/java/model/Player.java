package model;

import exceptions.points.InvalidPointsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final Logger PLAYER_LOGGER = LogManager.getLogger(Player.class);

    private int points;
    private Hand hand;
    private final String name;
    private List<Hand> downedHand;
    private boolean hasGoneDown;
    private boolean hasWon;

    public Player(String name) {
        points = 0;
        hand = new Hand();
        this.name = name;
        downedHand = new ArrayList<>();
        hasGoneDown = false;
        hasWon = false;
    }

    public Player(int points, Hand hand, String name) {
        this.points = points;
        this.hand = hand;
        this.name = name;
        downedHand = new ArrayList<>();
        hasGoneDown = false;
        hasWon = false;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int pointsToAdd) throws InvalidPointsException {
        if (pointsAreValid(pointsToAdd)) {
            points += pointsToAdd;
        } else {
            PLAYER_LOGGER.error("Given points (" + pointsToAdd + ") are invalid.");
            throw new InvalidPointsException("Given points (" + pointsToAdd + ") are invalid.");
        }
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return this.hand;
    }

    public List<Hand> getDownedHand() {
        return downedHand;
    }

    public boolean getHasGoneDown() {
        return hasGoneDown;
    }

    public void setHasGoneDown(boolean hasGoneDown) {
        this.hasGoneDown = hasGoneDown;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public boolean getHasWon() {
        return hasWon;
    }

    private boolean pointsAreValid(int points) {
        return points % 5 == 0;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                " points=" + points + '}';
    }

}
