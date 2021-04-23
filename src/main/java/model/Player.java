package model;

import exceptions.points.InvalidPointsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Player {

    private final Logger PLAYER_LOGGER = LogManager.getLogger(Player.class);

    private int points;
    private Hand hand;
    private final String name;

    public Player(String name) {
        points = 0;
        hand = new Hand();
        this.name = name;
    }

    public Player(int points, Hand hand, String name) {
        this.points = points;
        this.hand = hand;
        this.name = name;
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

    private boolean pointsAreValid(int points) {
        return points % 5 == 0;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

    public Hand getHand() {
        return this.hand;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                " points=" + points + '}';
    }

}
