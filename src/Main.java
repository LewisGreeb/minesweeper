import pkg.minesweeper.GameGrid;

import java.util.Scanner;

public class Main {

    public static void main (String[] args){
        // Prep scanner for user input.
        Scanner scanner = new Scanner(System.in);

        // Control game loop.
        String cntue = "";

        // Game loop.
        do{

            // Activate game.
            startGame(scanner);

            // Check if user wants to end the program.
            System.out.println("Please enter 'end' to close the program. Otherwise, press enter to continue.");
            cntue = scanner.nextLine();

        } while (!cntue.equalsIgnoreCase("End"));

    }

    public static void startGame(Scanner scanner){
        // Choose difficulty.
        System.out.println("Choose game difficulty:");
        System.out.println("Beginner (1) - Intermediate (2) - Expert (3)");

        // Initialise user selection variables.
        String input = scanner.nextLine();
        // Validate user input.
        while(!input.equals("1") && !input.equals("2") && !input.equals("3")){
            System.out.println("Please enter a number between 1 and 3.");
            input = scanner.nextLine();
        }

        // Declare grid variable.
        GameGrid grid;
        // Set grid size based on selected difficulty.
        switch (input) {
            case "2" -> grid = new GameGrid(15, 16, 40);
            case "3" -> grid = new GameGrid(16, 30, 99);
            default -> grid = new GameGrid(9, 9, 10);
        }

        // Initialise grid nodes.
        grid.buildGrid();

        // Initialise game tracker.
        boolean gameUp;

        // Game loop.
        do{
            // Display grid.
            grid.printGrid();

            // User prompt for row.
            System.out.println("Please enter a row number:");
            String row = scanner.nextLine();
            int iRow = Integer.parseInt(row)-1;
            // User prompt for column.
            System.out.println("Please enter a column number:");
            String col = scanner.nextLine();
            int iCol = Integer.parseInt(col)-1;

            gameUp = grid.selectNode(iRow, iCol);

        }while(!grid.gameComplete() && gameUp);

    }

}
