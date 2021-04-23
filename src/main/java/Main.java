import exceptions.GeneralGameException;
import model.Player;
import runner.GameRunner;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws GeneralGameException {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
        players.add(new Player("Player3"));

        GameRunner gameRunner = new GameRunner(players);

        gameRunner.play();
    }
}
