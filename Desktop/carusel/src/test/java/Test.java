import applicationLogic.DictionaryReader;
import applicationLogic.DictionaryValidation;
import csvReader.CSVReaderWriter;
import csvReader.Player;
import hello.Application;
import hello.MainController;
import junit.framework.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by cezar on 16.11.2015.
 */
@SpringApplicationConfiguration(classes = Application.class)
public class Test {

    @Autowired MainController mc;

    @org.junit.Test
    public void testReadDictionary() throws IOException{

        Set<String> wordsInRomanian = new HashSet<String>();
        DictionaryReader reader = new DictionaryReader("loc-reduse-5.0.txt");
        wordsInRomanian = reader.readAllAvaibleWords();
        int lines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("loc-reduse-5.0.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines++;
            }
        }
        Assert.assertEquals(wordsInRomanian.size(), lines);
    }

    @org.junit.Test
    public void testReadAllAvailableWords() throws  IOException{
        Set<String> wordsInRomanian;
        DictionaryReader reader = new DictionaryReader("loc-reduse-5.0.txt");
        wordsInRomanian = reader.readAllAvaibleWords();
        DictionaryValidation dv = new DictionaryValidation();
        Assert.assertEquals(wordsInRomanian.contains("casa"), true);
        Assert.assertEquals(wordsInRomanian.contains("c12asa"), false);
        Assert.assertEquals(wordsInRomanian.contains(" "), false);

    }

    @org.junit.Test
    public void testReadAllPlayersFromCSV() throws IOException{
        CSVReaderWriter csv = new CSVReaderWriter("players");
        List<Player> players = csv.readAllPlayersFromCSV();
        int lines = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("players"))) {
            String line;
            while ((line = br.readLine()) != null) {
                lines++;
            }
        }
        Assert.assertEquals(players.size(), lines);

    }

    @org.junit.Test
    public void testWriteAllPlayersToCSV() throws  IOException{
        CSVReaderWriter csv = new CSVReaderWriter("players");
        Player p1 = new Player("Cezar","123456");
        Player p2 = new Player("Marian","xxxxxx");
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        csv.writeAllPlayersToCSV(players);
        Assert.assertEquals(csv.readAllPlayersFromCSV().size(), players.size());
    }

}
