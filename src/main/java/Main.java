import exceptions.GeneralGameException;
import model.Player;
import runner.GameRunner;

public class Main {
    public static void main(String[] args) throws GeneralGameException {
        GameRunner gameRunner = new GameRunner();

        gameRunner.addPlayer(new Player("Player1"));
        gameRunner.addPlayer(new Player("Player2"));
        gameRunner.addPlayer(new Player("Player3"));

        gameRunner.play();
    }
}
