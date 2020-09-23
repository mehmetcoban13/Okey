import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        System.out.println("***All Hands***");
        for (Player player: players) {
            System.out.println(player.getName() + ": " + player.getRack());
        }
        ArrayList<Player> playerList = seqFirst(numberFirst(this.players));
        double max = playerList.get(0).getPoint();
        int index = 0;
        for (int i=0; i<playerList.size(); i++) {
            if (playerList.get(i).getPoint() > max){
                max = playerList.get(i).getPoint();
                index = i;
            }
        }
        System.out.println("\n"+players.get(index).getName() + " Has The Best Hand!");
    }

    private ArrayList<Player> seqFirst(ArrayList<Player> playerList){
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0 ; i<playerList.size();i++){
            players.add(playerList.get(i)) ;
        }
        for (Player player : players) {
            HashMap<Color, ArrayList<Tile>> map  = new HashMap<>();
            Collections.sort(player.getRack(), new Sequential());
            for (Tile tile: player.getRack()) {
                map.putIfAbsent(tile.getColor(), new ArrayList<Tile>());
                if(!map.get(tile.getColor()).stream().
                        anyMatch(element -> element.getNumber() == tile.getNumber())){
                    map.get(tile.getColor()).add(tile);
                }
            }for (ArrayList<Tile> tiles : map.values()){
                if (tiles.size()<3){
                    continue;
                }
                else {
                    int count = 0;
                    for(int i=0; i<tiles.size()-1; i++){
                        if (tiles.get(i).getNumber()+1 == tiles.get(i+1).getNumber()){
                            count++;
                        }
                        else {
                            if(count == 2){
                                player.setPoint(player.getPoint()+1.0);
                            }else if (count == 3){
                                player.setPoint(player.getPoint()+1.5);
                            }else if(count == 4){
                                player.setPoint(player.getPoint()+2.0);
                            }
                            count = 0;
                        }
                    }
                }
            }
        }

        return players;
    }
    private ArrayList<Player> numberFirst(ArrayList<Player> playerList){
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0 ; i<playerList.size();i++){
            players.add(playerList.get(i)) ;
        }
        for (Player player : players) {
            if (player.getRack().stream().anyMatch(element -> element.getColor() == Color.Joker)){
                player.setPoint(player.getPoint()+1);
            }
            HashMap<Integer, ArrayList<Tile>> map  = new HashMap<>();
            Collections.sort(player.getRack(), new SameNumber());
            for (Tile tile: player.getRack()) {
                map.putIfAbsent(tile.getNumber(), new ArrayList<Tile>());
                if(!map.get(tile.getNumber()).stream().
                        anyMatch(element -> element.getColor() == tile.getColor())){
                    map.get(tile.getNumber()).add(tile);
                }
            }
            for (ArrayList<Tile> tiles : map.values()){
                if (tiles.size()<3){ continue; }
                else if(tiles.size()==3){
                    for (Tile tile : tiles) {
                        player.getRack().remove(tile);
                    }
                    player.setPoint(player.getPoint()+1);
                }else if (tiles.size() == 4){
                    for (Tile tile : tiles) {
                        player.getRack().remove(tile);
                    }
                    player.setPoint(player.getPoint()+1.5);
                }
            }
        }

        return players;
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
