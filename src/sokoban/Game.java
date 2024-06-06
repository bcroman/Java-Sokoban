package sokoban;

// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Ben Collins 21006366
 * 06/06/2024
 * Version 2.0
 * The `Game` class represents the main game panel for the Sokoban game.
 * It extends the `JPanel` class and contains the game logic and UI components.
 */
public class Game extends JPanel {

    // Instance variables
    private Level level;
    private Map<Coord, MapElement> elements;
    private Map<Character, Image> imageMap;
    private Player player;
    private int moveCount;
    private GameEngine gameEngine;
    private int currentLevelIndex;
    private final String[] mapFiles = {
            "maps/level1.txt",
            "maps/level2.txt",
            "maps/level3.txt",
            "maps/level4.txt",
            "maps/level5.txt"
    };

    // Constructor
    public Game() {
        level = new Level();
        elements = new HashMap<>();
        imageMap = new HashMap<>();
        loadImages();
        setupUI();
        currentLevelIndex = 0;
        loadLevel(mapFiles[currentLevelIndex]); // Load the first level by default
        setFocusable(true);
    }


    /**
     * Loads the images used in the game.
     * The images are loaded from the specified file paths and stored in the imageMap.
     * If an error occurs while loading the images, an error message is displayed and the application exits.
     */
    private void loadImages() {
        try {
            imageMap.put('X', loadImage("graphics/wall.png"));
            imageMap.put(' ', loadImage("graphics/floor.png"));
            imageMap.put('@', loadImage("graphics/player.png"));
            imageMap.put('*', loadImage("graphics/crate.png"));
            imageMap.put('.', loadImage("graphics/diamond.png"));
            imageMap.put('C', loadImage("graphics/CrateInPlace.png")); // New image for crate on diamond
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading images: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1); // Exit the application if images can't be loaded
        }
    }

    /**
     * Represents an image that can be loaded from a file.
     */
    private Image loadImage(String path) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);
        if (is == null) {
            throw new IOException("Image file not found: " + path);
        }
        return ImageIO.read(is);
    }

    /**
     * Sets up the user interface for the game. Configures the layout and adds a key
     * listener.
     */
    private void setupUI() {
        setLayout(new BorderLayout());
        addKeyListener(new KeyHandler());
    }

    /**
     * Loads a level from the specified file and initializes the game state.
     *
     * @param filename the name of the file containing the level data
     */
    private void loadLevel(String filename) {
        level.loadLevel(filename);
        level.displayMap(); // Display the map in the console
        elements.clear();
        moveCount = 0;
        initializeElements();
        gameEngine = new GameEngine(level, elements, player, imageMap.get(' '), imageMap.get('.'), imageMap.get('*'),
                imageMap.get('C'), this); // Initialize GameEngine with floor, diamond, default crate, and crate on
                                          // diamond images
        repaint();
    }

    /**
     * Loads the next level if available, or displays a win message if all levels
     * are completed.
     */
    public void loadNextLevel() {
        currentLevelIndex++;
        if (currentLevelIndex < mapFiles.length) {
            showLevelCompleteMessage();
            loadLevel(mapFiles[currentLevelIndex]);
        } else {
            JOptionPane.showMessageDialog(this, "Congratulations! You completed all levels!", "Game Over",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Shows a message with the number of moves and current level.
     */
    private void showLevelCompleteMessage() {
        JOptionPane.showMessageDialog(this,
                "Moves: " + moveCount + "\nLevel: " + currentLevelIndex + "/" + mapFiles.length, "Level Complete",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Initializes the elements of the game based on the map provided by the level.
     * Each character in the map represents a specific element in the game. The
     * elements are created and stored in the elements map.
     */
    private void initializeElements() {
        char[][] map = level.getMap(); // Get the map from the level
        for (int y = 0; y < level.getRows(); y++) { // Iterate over the rows
            for (int x = 0; x < level.getCols(); x++) { // Iterate over the columns
                char c = map[y][x]; // Get the character at the current position
                MapElement element = null; // Initialize the element to null
                switch (c) {
                    case 'X': // Wall
                        element = new Wall(x, y, imageMap.get('X'));
                        break;
                    case ' ': // Floor
                        element = new Floor(x, y, imageMap.get(' '));
                        break;
                    case '@': // Player
                        player = new Player(x, y, imageMap.get('@'));
                        element = player;
                        break;
                    case '*': // Crate
                        element = new Crate(x, y, imageMap.get('*'));
                        break;
                    case '.': // Diamond
                        element = new Diamond(x, y, imageMap.get('.'));
                        break;
                }
                if (element != null) { // Add the element to the map if it is not null
                    elements.put(new Coord(x, y), element);
                }
            }
        }

        // Debug print to ensure elements are initialized
        System.out.println("Initialized elements:");
        for (Map.Entry<Coord, MapElement> entry : elements.entrySet()) {
            System.out
                    .println("Coord: " + entry.getKey() + ", Element: " + entry.getValue().getClass().getSimpleName());
        }
    }

    /**
     * A class that handles key events for the game.
     */
    private class KeyHandler extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) { // Handle key press events
            int key = e.getKeyCode();
            int dx = 0, dy = 0;

            switch (key) { // Handle arrow keys and WASD keys for movement
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    dy = -1; // Move up
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    dy = 1; // Move down
                    break;
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    dx = -1; // Move left
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    dx = 1; // Move right
                    break;
                default:
                    return; // If the key is not one of the above, do nothing
            }

            // Call the game engine's movePlayer method to handle player movement
            gameEngine.movePlayer(dx, dy);

            // Update the move count from the game engine
            moveCount = gameEngine.getMoveCount();

            // Check for win condition
            if (gameEngine.checkWin()) {
                loadNextLevel();
            }

            // Repaint the game view to reflect the new state
            repaint();
        }
    }

    /**
     * Overrides the paintComponent method to draw the game elements and display the
     * move count.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (MapElement element : elements.values()) {
            element.draw(g);
        }
        g.setColor(Color.BLACK);
        g.drawString("Moves: " + moveCount, 10, 20);
    }

    /**
     * The entry point of the Sokoban game.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sokoban Game");
        Game game = new Game();
        frame.add(game);
        frame.setSize(800, 600); // Set the size of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
