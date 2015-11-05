package csvReader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ciprian on 05.11.2015.
 */
public class CSVReaderWriter implements ICSVReaderWriter {

    private final String csvSplit = ",";
    private String path;
    private String line = "";

    public CSVReaderWriter(String path){
        this.path = path;
    }

    @Override
    public List<Player> readAllPlayersFromCSV() throws IOException {
        List<Player> players = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        while ((line = bufferedReader.readLine()) != null){
            String[] words  = line.split(csvSplit);
            if(words.length == 1) {
                players.add(new Player(words[0]));
            }
            else{
                players.add(new Player(words[0],Long.parseLong(words[1])));
            }
        }
        bufferedReader.close();
        return players;
    }

    @Override
    public void writeAllPlayersToCSV(List<Player> players) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        for(Player player : players){
            bufferedWriter.write(player.getName() + "," + player.getScore() + "\r\n");
        }
        bufferedWriter.close();
    }
}
