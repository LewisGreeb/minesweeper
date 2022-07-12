package pkg.minesweeper;

public abstract class Node {

    // Attributes.
    private int xCoord;
    private int yCoord;

    // Constructor
    public Node(){}

    // Methods.
    public int getXCoord() {return xCoord;}
    public void setXCoord(int xCoord) {this.xCoord = xCoord;}

    public int getYCoord() {return yCoord;}
    public void setYCoord(int yCoord) {this.yCoord = yCoord;}

}
