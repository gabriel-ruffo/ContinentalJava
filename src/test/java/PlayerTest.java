import model.Hand;
import model.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {

    @Test
    public void testPointIncreasing() {
        Player player = new Player(0, new Hand());

        assertEquals(0, player.getPoints());

        player.addPoints(50);

        assertEquals(50, player.getPoints());
    }

    @Test
    public void testInvalidPointsAdd() {
        Player player = new Player(0, new Hand());

        assertEquals(0, player.getPoints());

        player.addPoints(53);

        assertEquals(0, player.getPoints());
    }
}
