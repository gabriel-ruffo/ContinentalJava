import model.Player;
import runner.GameRunner;

public class Main {
    public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner();

        gameRunner.addPlayer(new Player());
        gameRunner.addPlayer(new Player());

        gameRunner.play();
    }
}
