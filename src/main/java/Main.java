import model.Player;
import runner.GameRunner;

public class Main {
    public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner();
        Player player1 = new Player();
        Player player2 = new Player();

        gameRunner.addPlayer(player1);
        gameRunner.addPlayer(player2);

        gameRunner.setupRound();
        System.out.println(player1.getHand().toString());
        System.out.println(player2.getHand().toString());
    }
}
