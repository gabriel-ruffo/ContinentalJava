import exceptions.card.InvalidCardException;
import exceptions.game.InvalidRoundException;
import exceptions.player.InvalidPlayerException;
import model.Player;
import runner.GameRunner;

public class Main {
    public static void main(String[] args) throws InvalidCardException, InvalidPlayerException, InvalidRoundException {
        GameRunner gameRunner = new GameRunner();

        gameRunner.addPlayer(new Player());
        gameRunner.addPlayer(new Player());

        gameRunner.play();
    }
}
