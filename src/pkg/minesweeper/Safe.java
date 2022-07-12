package pkg.minesweeper;

public class Safe extends Node{

    // Attributes.
    private int adjacentBombs;
    private boolean uncovered;

    // Constructor.
    public Safe(){}

    // Methods.
    public int getAdjacentBombs() {return adjacentBombs;}
    public void setAdjacentBombs(int adjacentBombs) {this.adjacentBombs = adjacentBombs;}


    public boolean isUncovered() {return uncovered;}
    public void setUncovered(boolean uncovered) {this.uncovered = uncovered;}

}
