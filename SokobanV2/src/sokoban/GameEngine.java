package sokoban;

import java.util.Map;
import java.awt.Image;

/**
 * Represents the game engine for the Sokoban game. It manages the game logic, including player and crate movements.
 * 
 * @since 15/05/2024
 * @version 1.3
 */
public class GameEngine {

    private Level level;
    private Map<Coord, MapElement> elements;
    private Player player;
    private int moveCount;
    private Image floorImage;
    private Image diamondImage;
    private Image crateInPlaceImage;
    private Image crateImage; // New member for the default crate image
    private Game game;

    /**
     * Constructs a new GameEngine object.
     *
     * @param level    the level of the game
     * @param elements a map of coordinates to map elements
     * @param player   the player object
     * @param floorImage the image representing the floor
     * @param diamondImage the image representing the diamond
     * @param crateImage the image representing the default crate
     * @param crateInPlaceImage the image representing the crate on a diamond
     * @param game the game instance
     */
    public GameEngine(Level level, Map<Coord, MapElement> elements, Player player, Image floorImage, Image diamondImage, Image crateImage, Image crateInPlaceImage, Game game) {
        this.level = level;
        this.elements = elements;
        this.player = player;
        this.moveCount = 0;
        this.floorImage = floorImage;
        this.diamondImage = diamondImage;
        this.crateImage = crateImage; // Initialize the default crate image
        this.crateInPlaceImage = crateInPlaceImage;
        this.game = game;
    }

    /**
     * Checks if the coordinates are within the game boundaries.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the coordinates are within the game boundaries, false otherwise
     */
    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < level.getCols() && y < level.getRows();
    }

    /**
     * Checks if the player can move to the specified coordinates.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the player can move to the specified coordinates, false otherwise
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
     */
    private boolean moveCrate(int dx, int dy) {
        int crateX = player.getX() + dx;
        int crateY = player.getY() + dy;
        Coord crateCoord = new Coord(crateX, crateY);
        MapElement crateElement = elements.get(crateCoord);

        if (crateElement instanceof Crate crate) {
            int beyondX = crateX + dx;
            int beyondY = crateY + dy;

            if (canMoveTo(beyondX, beyondY)) {
                Coord beyondCoord = new Coord(beyondX, beyondY);
                elements.remove(crateCoord);
                elements.put(beyondCoord, crateElement);
                crate.move(dx, dy); // Update crate's coordinates

                // Check if the crate is on a diamond
                if (level.getMap()[beyondY][beyondX] == '.') {
                    crate.setImage(crateInPlaceImage);
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

        if (element instanceof Crate) {
            if (moveCrate(dx, dy)) {
                updatePlayerPosition(dx, dy);
            }
        } else if (canMoveTo(newX, newY)) {
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
        Coord currentCoord = new Coord(player.getX(), player.getY());
        System.out.println("Updating player position from: " + currentCoord);
        // Determine the original element type at the player's position
        char originalChar = level.getMap()[currentCoord.getY()][currentCoord.getX()];
        MapElement originalElement;
        if (originalChar == '.') {
            originalElement = new Diamond(currentCoord.getX(), currentCoord.getY(), diamondImage);
        } else {
            originalElement = new Floor(currentCoord.getX(), currentCoord.getY(), floorImage);
        }
        elements.put(currentCoord, originalElement);

        player.move(dx, dy);
        Coord newCoord = new Coord(player.getX(), player.getY());
        elements.put(newCoord, player);
        System.out.println("Player moved to: " + newCoord);
        moveCount++;

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
                MapElement element = elements.get(coord);
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
                    System.out.print(' ');
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
        for (MapElement element : elements.values()) {
            if (element instanceof Crate) {
                Coord coord = new Coord(element.getX(), element.getY());
                char mapChar = level.getMap()[coord.getY()][coord.getX()];
                if (mapChar != '.') {
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
