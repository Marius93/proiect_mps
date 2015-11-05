package csvReader;

/**
 * Created by ciprian on 05.11.2015.
 */
public class Player implements IPlayer{

    private String name;
    private Long score;

    public Player(String name){
        this.name = name;
        this.score = 0l;
    }
    public Player(String name,Long score){
        this.name = name;
        this.score = score;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getScore() {
        return score;
    }
}
