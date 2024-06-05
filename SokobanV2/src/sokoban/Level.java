package sokoban;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Ben Collins 21006366
 * 15/05/2024
 * Version 1
 * Represents a level in the Sokoban game.
 */
public class Level {

    //Variables
    private char[][] map;
    private int rows;
    private int cols;

    /**
     * Loads a level from a file and initializes the map.
     *
     * @param filename the name of the file containing the level data
     */
    public void loadLevel(String filename) {
        List<String> lines = new ArrayList<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            if (is == null) {
                throw new IOException("File not found: " + filename);
            }

            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!lines.isEmpty()) {
            rows = lines.size();
            cols = lines.get(0).length();
            map = new char[rows][cols];

            for (int i = 0; i < rows; i++) {
                map[i] = lines.get(i).toCharArray();
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