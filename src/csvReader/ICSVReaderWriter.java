package csvReader;

import java.io.IOException;
import java.util.List;

/**
 * Created by ciprian on 05.11.2015.
 */
public interface ICSVReaderWriter {
    public List<Player> readAllPlayersFromCSV() throws IOException;
    public void writeAllPlayersToCSV(List<Player> players) throws IOException;
}
