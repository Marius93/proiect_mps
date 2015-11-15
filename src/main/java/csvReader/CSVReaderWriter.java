package csvReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciprian on 05.11.2015.
 */
public class CSVReaderWriter {

    private final String csvSplit = ",";
    private String path;
    private String line = "";

    public CSVReaderWriter(String path){
        this.path = path;
    }

    public List<Player> readAllPlayersFromCSV() throws IOException {
        List<Player> players = new ArrayList<Player>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        while ((line = bufferedReader.readLine()) != null){
            String[] words  = line.split(csvSplit);
            players.add(new Player(words[0],words[1]));
        }
        bufferedReader.close();
        return players;
    }

    public void writeAllPlayersToCSV(List<Player> players) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        for(Player player : players){
            bufferedWriter.write(player.getName() + "," + player.getPassword() + "\r\n");
        }
        bufferedWriter.close();
    }
}
