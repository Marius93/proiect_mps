package csvReader;

import java.io.IOException;
import java.util.List;

/**
 * Created by ciprian on 05.11.2015.
 */
public class MainTest {
    public static void main(String args[]) throws IOException {
        String path = "players";
        CSVReaderWriter CSVReaderWriter = new CSVReaderWriter(path);
        List<Player> players = CSVReaderWriter.readAllPlayersFromCSV();
        for(Player player : players){
            System.out.println(player.getName() + " " + player.getScore());
        }
        players.add(new Player("Ciprian"));
        CSVReaderWriter.writeAllPlayersToCSV(players);
    }
}
