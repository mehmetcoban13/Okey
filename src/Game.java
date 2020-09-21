import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;


public class Game {
    private ArrayList<Player> players;
    private ArrayList<Tile> pool;
    private Tile indicator;
    private Tile joker;

    public Game(ArrayList<Player> players){
        setPlayers(players);
        initializePool();
        setup();
    }

    private void setup(){
        setIndicator();
        ArrayList<Tile> jokers = setJoker();
        setFalseJokers(jokers);
        tileDistribution();
    }

    private void initializePool(){
        this.pool = new ArrayList<>();
        Color[] colors = Color.values();
        int pointer = 0;
        for (int i = 0; i<2; i++){
            for (int j=0; j<colors.length-1; j++) {
                for (int number=1; number<=13; number++){
                    pool.add(new Tile(colors[j], number, pointer));
                    pointer++;
                }
            }
        }
        pool.add(new Tile(Color.Joker,0, pointer));
        pointer++;
        pool.add(new Tile(Color.Joker,0, pointer));
        Collections.shuffle(pool);
    }

    private void tileDistribution(){
        int randomNumber = new SecureRandom().nextInt(players.size());
        for (int i=0; i<players.size(); i++){
            if(randomNumber == i){
                players.get(i).setRack(getNewRack(15));
            }else{
                players.get(i).setRack(getNewRack(14));
            }
        }
    }



    public ArrayList<Tile> getNewRack(int totalItems) {
        SecureRandom secureRandom = new SecureRandom();
        ArrayList<Tile> newRack = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {
            int randomIndex = secureRandom.nextInt(pool.size());
            newRack.add(pool.get(randomIndex));
            pool.remove(randomIndex);
        }
        return newRack;
    }

    private void setIndicator(){
        this.indicator = pool.get(new SecureRandom().nextInt(pool.size()));
        if(indicator.getNumber()==0){
            setIndicator();
        }else{
            pool.remove(indicator);
        }
    }
    private ArrayList<Tile> setJoker(){
        this.joker = new Tile(indicator.getColor(),
                (indicator.getNumber()%13)+1, -1);

        ArrayList<Tile> jokers = (ArrayList<Tile>) pool.stream()
                .filter(tile -> (tile.getNumber() == joker.getNumber())
                && tile.getColor() == joker.getColor())
                .collect(Collectors.toList());
        this.joker.setColor(Color.Joker);
        this.joker.setNumber(-1);
        return jokers;
    }
    private void setFalseJokers(ArrayList<Tile> jokers){
        ArrayList<Tile> falseJokers = (ArrayList<Tile>) pool.stream()
                .filter(tile -> tile.getNumber() == 0).collect(Collectors.toList());
        for (var i=0; i<falseJokers.size(); i++) {
            falseJokers.get(i).setColor(jokers.get(i).getColor());
            falseJokers.get(i).setNumber(jokers.get(i).getNumber());
            falseJokers.get(i).setPointer(jokers.get(i).getPointer());

            jokers.get(i).setPointer(-1);
            jokers.get(i).setColor(Color.Joker);
            jokers.get(i).setNumber(-1);
        }
    }

    public void setPlayers(ArrayList<Player> players) {
        if (players.size() != 4){
            System.out.println("The game requires exactly 4 players!" +
                    "\nPlease follow that rule and come back...");
            System.exit(0);
        }else { this.players = players; }
    }
    public ArrayList<Player> getPlayers() {
        return this.players;
    }
    public ArrayList<Tile> getPool() {
        return this.pool;
    }
    public Tile getIndicator(){
        return this.indicator;
    }
    public Tile getJoker(){
        return this.joker;
    }
}
