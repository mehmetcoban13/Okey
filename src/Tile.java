import java.util.Comparator;

public class Tile implements Comparable<Tile>{

    private Color color;
    private int number;
    private int pointer;

    public Tile(Color color, int number, int pointer){
        setColor(color);
        setNumber(number);
        setPointer(pointer);
    }
    @Override
    public int compareTo(Tile tile1) {
        return this.getPointer()%13 - tile1.getPointer()%13;
    }
    public String toString(){
        return color + " " + number ; //+ " " + pointer
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public Color getColor() {
        return this.color;
    }

    public int getNumber() {
        return this.number;
    }

    public int getPointer() {
        return this.pointer;
    }
}

class SameNumber implements Comparator<Tile> {
    public int compare(Tile a, Tile b) {
        return a.getPointer()%13 - b.getPointer()%13;
    }
}

class Sequential implements Comparator<Tile> {
    public int compare(Tile a, Tile b) {
        return a.getPointer()%52 - b.getPointer()%52;
    }
}
