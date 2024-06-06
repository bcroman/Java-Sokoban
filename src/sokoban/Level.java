package sokoban;

// Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Ben Collins 21006366
 * 06/06/2024
 * Version 2
 * Represents a level in the Sokoban game.
 */
public class Level {

    // Variables
    private char[][] map;
    private int rows;
    private int cols;

    /**
     * Loads a level from a file and initializes the map.
     *
     * @param filename the name of the file containing the level data
     */
    public void loadLevel(String filename) {
        List<String> lines = new ArrayList<>(); // Create a list to store the lines of the file

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename); // Get the input stream for the
                                                                                         // file
                BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) { // Check if the file exists
                throw new IOException("File not found: " + filename); // Error Message
            }

            String line;
            while ((line = br.readLine()) != null) { // Read each line of the file
                lines.add(line); // Add the line to the list
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!lines.isEmpty()) { // Check if the list is not empty
            rows = lines.size(); // Set the number of rows to the size of the lines list
            cols = lines.get(0).length(); // Set the number of columns to the length of the first line

            map = new char[rows][cols]; // Create a new char array with the dimensions of the level

            for (int i = 0; i < rows; i++) {
                map[i] = lines.get(i).toCharArray(); // Convert each line to a char array and assign it to the map
            }
        }
    }

    /**
     * Returns the map of the level.
     *
     * @return the map of the level
     */
    public char[][] getMap() {
        return map;
    }

    /**
     * Returns the number of rows in the level.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the level.
     *
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Displays the map of the level.
     */
    public void displayMap() {
        for (char[] map1 : map) {
            for (int j = 0; j < map1.length; j++) {
                System.out.print(map1[j]);
            }
            System.out.println();
        }
    }
}