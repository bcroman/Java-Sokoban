package sokoban;

// Imports
import java.util.Objects;

/**
 * Ben Collins 21006366 
 * 05/06/2024 
 * Version 2.0 
 * The Coord class represents a coordinate with x and y values.
 */
public class Coord {

    // Variables
    private int x;
    private int y;

    // Constructor
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value of the coordinate.
     *
     * @return the x value of the coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y value of the coordinate.
     *
     * @return the y value of the coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Checks if this Coord object is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) // Check if the objects are the same
        {
            return true;
        }
        if (o == null || getClass() != o.getClass()) // Check if the object is null or not an instance of Coord
        {
            return false;
        }
        Coord coord = (Coord) o; // Cast the object to a Coord object
        return x == coord.x && y == coord.y;
    }

    /**
     * Returns the hash code value for this Coord object.
     *
     * @return the hash code value for this Coord object
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of this Coord object.
     *
     * @return a string representation of this Coord object
     */
    @Override
    public String toString() {
        return "Coord{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }
}
