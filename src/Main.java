import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    public static void main(String[] args) {
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        players.add(new Player("Player 3"));
        players.add(new Player("Player 4"));

        Game game = new Game(players);
        System.out.println(game.getPool() + " " + game.getPool().size());
        System.out.println(game.getIndicator() + " " + game.getJoker());

        System.out.println(players.get(0).getRack() + " " + players.get(0).getRack().size());
        System.out.println(players.get(1).getRack() + " " + players.get(1).getRack().size());
        System.out.println(players.get(2).getRack() + " " + players.get(2).getRack().size());
        System.out.println(players.get(3).getRack() + " " + players.get(3).getRack().size());
        System.out.println("\n\n\n");
        Collections.sort(players.get(0).getRack(), new Sequential());
        System.out.println(players.get(0).getRack() + " " + players.get(0).getRack().size());
        Collections.sort(players.get(0).getRack(), new SameNumber());
        System.out.println(players.get(0).getRack() + " " + players.get(0).getRack().size());
    }
}
