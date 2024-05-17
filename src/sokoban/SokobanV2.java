package sokoban;

/**
 * Ben Collins 21006366
 * 15/05/2024
 * Version 1
 * The main class for the Sokoban game.
 */
public class SokobanV2 {

    /**
     * The main method that starts the Sokoban game.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Level level = new Level();
        level.loadLevel("Maps/level3.txt"); // Load the level
        level.displayMap(); // Display the map
    }
    
}
