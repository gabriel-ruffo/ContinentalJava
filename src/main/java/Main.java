import exceptions.GeneralGameException;
import exceptions.card.InvalidCardException;
import exceptions.deck.InvalidDeckException;
import exceptions.game.InvalidRoundException;
import exceptions.hand.InvalidHandException;
import exceptions.player.InvalidPlayerException;
import model.Player;
import runner.GameRunner;

public class Main {
    public static void main(String[] args) throws GeneralGameException {
        GameRunner gameRunner = new GameRunner();

        gameRunner.addPlayer(new Player());
        gameRunner.addPlayer(new Player());

        gameRunner.play();
    }
}
