import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Tile> rack;
    private double point;

    public Player(String name){
        setName(name);
        setRack(new ArrayList<>());
        setPoint(0);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setRack(ArrayList<Tile> rack) {
        this.rack = rack;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Tile> getRack(){
        return this.rack;
    }

    public double getPoint() {
        return point;
    }
}
