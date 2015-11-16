package csvReader;

/**
 * Created by ciprian on 05.11.2015.
 */
public class Player {

    private String name;
    private String password;

    public Player(String name,String password){
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
