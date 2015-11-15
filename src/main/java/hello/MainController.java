package hello;

import java.io.IOException;
import java.rmi.server.RMIClassLoader;
import java.util.*;

import applicationLogic.DictionaryReader;
import applicationLogic.DictionaryValidation;
import com.google.common.collect.Lists;
import csvReader.CSVReaderWriter;
import csvReader.Player;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

@RestController
public class MainController {
    //GreetingController
    DictionaryReader dictionaryReader;
    CSVReaderWriter csvReaderWriter;
    Set<String> words;
    List<Player> players;
    List<String> dices;
    long numberOfPlayers = 0;
    MainController() throws IOException {
        dictionaryReader = new DictionaryReader("loc-reduse-5.0.txt");
        csvReaderWriter = new CSVReaderWriter("loginCredentials");
        words = dictionaryReader.readAllAvaibleWords();
        players = csvReaderWriter.readAllPlayersFromCSV();
        DictionaryValidation.initDictionary();
        dices = new ArrayList<String>();
        dices.add("AAUIHJ");
        dices.add("TRNSMB");
        dices.add("AARCDM");
        dices.add("EEIODF");
        dices.add("AEUSFV");
        dices.add("TLNPGC");
        dices.add("AIOEXZ");
        dices.add("NSTRGB");
        dices.add("IIUELP");
    }

    @RequestMapping(value = "/getAllWords", method = RequestMethod.GET)
    public List<String> getAllWords(
            @RequestParam(value = "id", defaultValue = "0") int id) {
        return Lists.newArrayList(words);
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(@RequestParam(value = "username")String name,@RequestParam(value = "password")String password){
        for(Player player : players)
            if(player.getName().compareTo(name) == 0 && player.getPassword().compareTo(password) == 0)
                return "OK";
        return "NOK";
    }

    @RequestMapping(value = "/startGame",method = RequestMethod.GET)
    public String startGame(@RequestParam(value= "gameType", defaultValue = "singleplayer") String gameType) throws InterruptedException {
        if(gameType.compareTo("singleplayer") == 0 ){
            String diceRoll = "";
            for(String dice : dices) {
                diceRoll += dice.charAt((int) (Math.random() * 5));
            }
            return diceRoll;
        } else{
            if(numberOfPlayers == 4){
                this.notifyAll();
                String diceRoll = "";
                for(String dice : dices) {
                    diceRoll += dice.charAt((int) (Math.random() * 5));
                }
                numberOfPlayers = 0;
                return diceRoll;
            }
            else{
                this.wait(200000);
                String diceRoll = "";
                for(String dice : dices) {
                    diceRoll += dice.charAt((int) (Math.random() * 5));
                }
                numberOfPlayers = 0;
                return diceRoll;
            }
        }
    }

    @RequestMapping(value = "/validateWords",method = RequestMethod.POST)
    public long validateWords(@RequestParam(value = "words") String words){
        long score = 0l;
        StringTokenizer st = new StringTokenizer(words.trim(),",");
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            if(word != null && DictionaryValidation.validateWord(word))
                score += word.length()*2;
        }
        return score;
    }
}
