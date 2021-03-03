package model;

public class Player {

    private int points;
    private Hand hand;

    public Player () {
        points = 0;
        hand = new Hand();
    }

    public Player(int points, Hand hand) {
        this.points = points;
        this.hand = hand;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int pointsToAdd) {
        if(pointsAreValid(pointsToAdd)) {
            points += pointsToAdd;
        }
        // TODO: how to enforce correct points? Custom exception, boolean return type, ...
    }

    private boolean pointsAreValid(int points) {
        return points % 5 == 0;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }

}
