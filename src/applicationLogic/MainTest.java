package applicationLogic;

import java.io.IOException;

/**
 * Created by ciprian on 05.11.2015.
 */
public class MainTest {
    public static void main(String args[]) throws IOException {
        DictionaryValidation.initDictionary();
        System.out.println(DictionaryValidation.validateWord("casa"));
        System.out.println(DictionaryValidation.validateWord("asdaasf"));
    }
}
