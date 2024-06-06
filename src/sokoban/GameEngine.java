package sokoban;

// Imports
import java.util.Map;
import java.awt.Image;

/**
 * Ben Collins 21006366
 * 06/06/2024
 * Version 2.0
 * Represents the game engine for the Sokoban game. It manages the game logic.
 */
public class GameEngine {

    // Variables
    private Level level;
    private Map<Coord, MapElement> elements; // Aggregation of MapElement objects
    private Player player;
    private int moveCount;
    private Image floorImage;
    private Image diamondImage;
    private Image crateInPlaceImage;
    private Image crateImage; // New member for the default crate image
    private Game game;

    // Constructor
    public GameEngine(Level level, Map<Coord, MapElement> elements, Player player, Image floorImage, Image diamondImage,
            Image crateImage, Image crateInPlaceImage, Game game) {
        this.level = level;
        this.elements = elements;
        this.player = player;
        this.moveCount = 0;
        this.floorImage = floorImage;
        this.diamondImage = diamondImage;
        this.crateImage = crateImage;
        this.crateInPlaceImage = crateInPlaceImage;
        this.game = game;
    }

    /**
     * Checks if the coordinates are within the game boundaries.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the coordinates are within the game boundaries, false
     *         otherwise
     */
    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < level.getCols() && y < level.getRows();
    }

    /**
     * Checks if the player can move to the specified coordinates.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the player can move to the specified coordinates, false
     *         otherwise
     */
    public boolean canMoveTo(int x, int y) {
        if (!isWithinBounds(x, y)) {
            return false;
        }
        Coord newCoord = new Coord(x, y);
        MapElement element = elements.get(newCoord);
        return (element == null || element instanceof Floor || element instanceof Diamond)
                && level.getMap()[y][x] != 'X';
    }

    /**
     * Moves a crate in the specified direction if the player pushes it.
     *
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     * @return true if the crate was successfully moved, false otherwise
     * 
     */
    private boolean moveCrate(int dx, int dy) {
        int crateX = player.getX() + dx;
        int crateY = player.getY() + dy;
        Coord crateCoord = new Coord(crateX, crateY);
        MapElement crateElement = elements.get(crateCoord);

        if (crateElement instanceof Crate crate) { // Check if the element is a crate
            int beyondX = crateX + dx;
            int beyondY = crateY + dy;

            if (canMoveTo(beyondX, beyondY)) { // Check if the crate can be moved
                Coord beyondCoord = new Coord(beyondX, beyondY); //
                elements.remove(crateCoord);
                elements.put(beyondCoord, crateElement);
                crate.move(dx, dy); // Update crate's coordinates

                // Check if the crate is on a diamond
                if (level.getMap()[beyondY][beyondX] == '.') {
                    crate.setImage(crateInPlaceImage); // Set crate image to crateInPlaceImage if on a diamond
                } else {
                    crate.setImage(crateImage); // Reset to default crate image if not on a diamond
                }

                return true;
            }
        }
        return false;
    }

    /**
     * Moves the player in the specified direction.
     *
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    public void movePlayer(int dx, int dy) {
        int newX = player.getX() + dx;
        int newY = player.getY() + dy;
        Coord newCoord = new Coord(newX, newY);
        MapElement element = elements.get(newCoord);

        System.out.println("Attempting to move player to: " + newCoord);

        if (element instanceof Crate) { // Check if the player is pushing a crate
            if (moveCrate(dx, dy)) { // Check if the crate was successfully moved
                updatePlayerPosition(dx, dy);
            }
        } else if (canMoveTo(newX, newY)) { // Check if the player can move to the new coordinates
            updatePlayerPosition(dx, dy);
        }
    }

    /**
     * Updates the player's position.
     *
     * @param dx The change in x-coordinate.
     * @param dy The change in y-coordinate.
     */
    private void updatePlayerPosition(int dx, int dy) {
        Coord currentCoord = new Coord(player.getX(), player.getY()); // Get the player's current coordinates
        System.out.println("Updating player position from: " + currentCoord); // Print the current coordinates
        // Determine the original element type at the player's position
        char originalChar = level.getMap()[currentCoord.getY()][currentCoord.getX()]; //
        MapElement originalElement;
        if (originalChar == '.') { // Check if the player was on a diamond
            originalElement = new Diamond(currentCoord.getX(), currentCoord.getY(), diamondImage);
        } else { // Player was on a floor
            originalElement = new Floor(currentCoord.getX(), currentCoord.getY(), floorImage);
        }
        elements.put(currentCoord, originalElement);

        player.move(dx, dy); // Move the player
        Coord newCoord = new Coord(player.getX(), player.getY()); // Get the player's new coordinates
        elements.put(newCoord, player); // Update the player's position in the elements map
        System.out.println("Player moved to: " + newCoord);
        moveCount++; // Increment the move count by 1

        // Print the state of the map for debugging
        printMapState();
    }

    /**
     * Prints the current state of the map for debugging.
     */
    private void printMapState() {
        System.out.println("Current map state:");
        for (int y = 0; y < level.getRows(); y++) {
            for (int x = 0; x < level.getCols(); x++) {
                Coord coord = new Coord(x, y);
                MapElement element = elements.get(coord); // Get the element at the current coordinates
                if (element instanceof Player) {
                    System.out.print('@');
                } else if (element instanceof Crate) {
                    System.out.print('*');
                } else if (element instanceof Wall) {
                    System.out.print('X');
                } else if (element instanceof Diamond) {
                    System.out.print('.');
                } else if (element instanceof Floor) {
                    System.out.print(' ');
                } else {
                    System.out.print(' '); // Print empty space if no element is present
                }
            }
            System.out.println();
        }
    }

    /**
     * Checks if the game is won (i.e., all crates are on diamonds).
     *
     * @return true if all crates are on diamonds, false otherwise
     */
    public boolean checkWin() {
        for (MapElement element : elements.values()) { // Iterate through all elements
            if (element instanceof Crate) { // Check if the element is a crate
                Coord coord = new Coord(element.getX(), element.getY());
                char mapChar = level.getMap()[coord.getY()][coord.getX()]; //
                if (mapChar != '.') { // Check if the crate is not on a diamond
                    return false; // Crate is not on a diamond
                }
            }
        }
        return true; // All crates are on diamonds
    }

    /**
     * Returns the number of moves made in the game.
     *
     * @return the number of moves made
     */
    public int getMoveCount() {
        return moveCount;
    }
}
