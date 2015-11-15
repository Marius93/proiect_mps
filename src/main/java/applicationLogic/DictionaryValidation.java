package applicationLogic;

import java.io.IOException;
import java.util.Set;

/**
 * Created by ciprian on 05.11.2015.
 */
public class DictionaryValidation {
    private static Set<String> wordsInRomanian;

    public static void initDictionary() throws IOException {
        DictionaryReader reader = new DictionaryReader("loc-reduse-5.0.txt");
        wordsInRomanian = reader.readAllAvaibleWords();
    }
    public static boolean validateWord(String word) {
        return wordsInRomanian.contains(word);
    }
}
