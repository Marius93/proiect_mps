package applicationLogic;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ciprian on 05.11.2015.
 */
public class DictionaryReader {
    private String path;
    public DictionaryReader(String path){
        this.path =path;
    }
    public Set<String> readAllAvaibleWords() throws IOException {
        Set<String> wordsInRomanian = new HashSet<>();
        String word = "";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        while ((word = bufferedReader.readLine()) != null){
            wordsInRomanian.add(word);
        }
        bufferedReader.close();
        return wordsInRomanian;
    }
}
