import exceptions.InvalidCardException;
import model.Player;
import runner.GameRunner;

public class Main {
    public static void main(String[] args) throws InvalidCardException {
        GameRunner gameRunner = new GameRunner();

        gameRunner.addPlayer(new Player());
        gameRunner.addPlayer(new Player());

        gameRunner.play();
    }
}
