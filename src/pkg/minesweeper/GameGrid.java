package pkg.minesweeper;

import java.util.Random;

public class GameGrid {

    // Attributes.
    private Node[][] grid;
    private int columns;
    private int rows;
    private int mineCount;
    private boolean gameUp;

    // Constructor.
    public GameGrid (int columns, int rows, int mineCount){
        this.grid = new Node[rows][columns];
        this.rows = rows;
        this.columns = columns;
        this.mineCount = mineCount;
        this.gameUp = true;
    }

    // Interact with grid.
    public boolean selectNode(int row, int col){
        if(this.grid[row][col] instanceof Safe){    // If the selected node is safe.
            // Uncover all neighbours for safe node with 0 adjacent bombs.
            uncoverNeighbours(row, col);
        }else if(this.grid[row][col] instanceof Bomb){   // If the selected node is a bomb.
            this.gameUp = ((Bomb) this.grid[row][col]).blow();
        }

        return this.gameUp;
    }

    private void uncoverNeighbours(int row, int col){
        // Node is only uncovered if it has been processed.
        if(!((Safe) this.grid[row][col]).isUncovered()){

            ((Safe) this.grid[row][col]).setUncovered(true);

            // If bomb count is not greater than 0, process neighbours.
            if(!(((Safe) this.grid[row][col]).getAdjacentBombs() > 0)){
                // Check row above if it exists.
                if(row > 0){
                    if(col > 0){
                        uncoverNeighbours(row-1,col-1);   // Check upper left.
                    }
                    uncoverNeighbours(row-1,col);   // Check upper middle.
                    if(col < (this.columns-1)){
                        uncoverNeighbours(row-1,col+1);   // Check upper right.
                    }
                }

                // Check current row.
                if(col > 0){
                    uncoverNeighbours(row,col-1);   // Check middle left.
                }
                if(col < (this.columns-1)){
                    uncoverNeighbours(row,col+1);   // Check middle right.
                }

                // Check row below if it exists.
                if(row < (this.rows-1)){
                    if(col > 0){
                        uncoverNeighbours(row+1,col-1);   // Check lower left.
                    }
                    uncoverNeighbours(row+1,col);   // Check lower middle.
                    if(col < (this.columns-1)){
                        uncoverNeighbours(row+1,col+1);   // Check lower right.
                    }
                }
            }
        }
    }


    // Build initial game state in grid.
    public void buildGrid(){
        // Setup random number generator.
        Random rand = new Random();

        // Mine counter.
        int mineCounter = this.mineCount;

        // Select random locations until all mines have been placed.
        while (mineCounter > 0) {
            // Generate random coordinates within boundaries of grid.
            int x = rand.nextInt(this.columns-1);
            int y = rand.nextInt(this.rows-1);

            // Set node to bomb.
            if(!(grid[y][x] instanceof Bomb) || !(grid[y][x] instanceof Safe)){
                grid[y][x] = new Bomb();
                mineCounter--;
            }
        }

        // Loop through all squares.
        for (int row = 0; row < this.rows; row++){
            for (int col = 0; col < this.columns; col++){
                // Add safe nodes if position is not filled with a bomb.
                if(!(this.grid[row][col] instanceof Bomb)){
                    this.grid[row][col] = new Safe();
                    // Calculate adjacent bombs.
                    int bombCount = checkBombCount(row, col);
                    if (this.grid[row][col] instanceof Safe){
                        ((Safe) this.grid[row][col]).setAdjacentBombs(bombCount);
                    }
                }
            }
        }
    }

    private int checkBombCount(int row, int col){
        int bombTotal = 0;

        // Check row above if it exists.
        if(row > 0){
            if(col > 0){
                if (this.grid[row-1][col-1] instanceof Bomb){bombTotal++;}
            }
            if (this.grid[row-1][col] instanceof Bomb){bombTotal++;}
            if(col < (this.columns-1)){
                if (this.grid[row-1][col+1] instanceof Bomb){bombTotal++;}
            }
        }

        // Check current row.
        if(col > 0){
            if (this.grid[row][col-1] instanceof Bomb){bombTotal++;}
        }
        if(col < (this.columns-1)){
            if (this.grid[row][col+1] instanceof Bomb){bombTotal++;}
        }

        // Check row below if it exists.
        if(row < (this.rows-1)){
            if(col > 0){
                if (this.grid[row+1][col-1] instanceof Bomb){bombTotal++;}
            }
            if (this.grid[row+1][col] instanceof Bomb){bombTotal++;}
            if(col < (this.columns-1)){
                if (this.grid[row+1][col+1] instanceof Bomb){bombTotal++;}
            }
        }

        return bombTotal;
    }

    // Print grid to console.
    public void printGrid(){
        // Draw top outline.
        System.out.print("   ");
        for (int i = 0; i < this.columns; i++){
            System.out.print(i+1);
            System.out.print(" ");
        }
        System.out.print("\n");

        // Counter var.
        int x = 1;

        // Build string with each row. Print by line.
        for (Node[] rows : this.grid){
            // Variable to store rows text.
            StringBuilder rowText = new StringBuilder();

            // Add row label.
            System.out.print(x + " ");

            // Add left boundary.
            rowText.append("|");

            // Print grid.
            for (Node node : rows){
                if(node instanceof Safe && ((Safe) node).isUncovered()){
                    rowText.append(((Safe) node).getAdjacentBombs());
                }else{
                    rowText.append("-");
                }
                // Add separator character.
                rowText.append("|");
            }

            System.out.println(rowText);

            x++;
        }
    }

    // Evaluate state of game.
    public boolean gameComplete(){

        // Counter var.
        int uncoveredSquares = 0;

        // Iterate through grid.
        for (Node[] rows : this.grid){
            for (Node node : rows){
                if (node instanceof Safe){
                    // Check if safe nodes are 'uncovered'.
                    if(((Safe) node).isUncovered()){
                        uncoveredSquares++;
                    }
                }
            }
        }

        int totalSquares = ((this.columns * this.rows) - this.mineCount);

        return uncoveredSquares == totalSquares;
    }

}
