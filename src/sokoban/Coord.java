package sokoban;

/**
 * Ben Collins 21006366
 * 15/05/2024
 * Version 1
 * Represents a coordinate in a 2D space.
 */
public class Coord {
    private int x, y;

    /**
     * Constructs a new Coord object with the specified x and y coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x coordinate of this Coord object.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of this Coord object.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }
}

