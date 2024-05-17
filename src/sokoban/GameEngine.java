package sokoban;

import java.util.Map;

/**
 * Ben Collins 21006366
 * 15/05/2024
 * Version 1
 * The GameEngine class represents the game engine of the Sokoban game. It
 * manages the game logic and provides methods for moving the player and crates.
 */
public class GameEngine {

    private Level level;
    private Map<Coord, MapElement> elements;
    private Player player;
    private int moveCount;

    /**
     * Constructs a new GameEngine object.
     *
     * @param level the level of the game
     * @param elements a map of coordinates to map elements
     * @param player the player object
     */
    public GameEngine(Level level, Map<Coord, MapElement> elements, Player player) {
        this.level = level;
        this.elements = elements;
        this.player = player;
        this.moveCount = 0;
    }

    /**
     * Checks if the player can move to the specified coordinates.
     *
     * @param x the x-coordinate to check
     * @param y the y-coordinate to check
     * @return true if the player can move to the specified coordinates, false
     * otherwise
     */
    public boolean canMoveTo(int x, int y) {
        if (x < 0 || y < 0 || x >= level.getCols() || y >= level.getRows()) {
            // Out of bounds
            return false;
        }
        Coord newCoord = new Coord(x, y);
        MapElement element = elements.get(newCoord);
        return (element == null || (element instanceof Floor) || (element instanceof Diamond)) && !(level.getMap()[y][x] == 'X');
    }

    /**
     * Moves a crate in the game based on the player's movement.
     *
     * @param playerX the x-coordinate of the player's position
     * @param playerY the y-coordinate of the player's position
     * @param dx the change in x-coordinate for the crate's movement
     * @param dy the change in y-coordinate for the crate's movement
     */
    public void moveCrate(int playerX, int playerY, int dx, int dy) {
        int crateX = playerX + dx;
        int crateY = playerY + dy;
        Coord crateCoord = new Coord(crateX, crateY);

        if (elements.containsKey(crateCoord) && elements.get(crateCoord) instanceof Crate) {
            int newCrateX = crateX + dx;
            int newCrateY = crateY + dy;
            Coord newCrateCoord = new Coord(newCrateX, newCrateY);

            if (canMoveTo(newCrateX, newCrateY)) {
                // Move crate
                Crate crate = (Crate) elements.get(crateCoord);
                elements.remove(crateCoord);
                crate.move(dx, dy);
                elements.put(newCrateCoord, crate);
            }
        }
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

        if (canMoveTo(newX, newY)) {
            // Valid move
            Coord currentCoord = new Coord(player.getX(), player.getY());
            Coord newCoord = new Coord(newX, newY);

            // Move player
            elements.remove(currentCoord);
            player.move(dx, dy);
            elements.put(newCoord, player);
            moveCount++;
        }
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
