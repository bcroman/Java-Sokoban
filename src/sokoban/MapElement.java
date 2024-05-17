package sokoban;

import java.awt.Image;
import java.awt.Graphics;

/**
 * Ben Collins 21006366
 * 15/05/2024
 * Version 1
 * The MapElement class represents an element in the game map.
 * It is an abstract class that provides common properties and methods for all map elements.
 */
public abstract class MapElement {
    private int x;
    private int y;
    private Image image;
    protected static final int TILE_SIZE = 32; // Adjust tile size if needed

    /**
     * Constructs a MapElement object with the specified coordinates and image.
     *
     * @param x     the x-coordinate of the map element
     * @param y     the y-coordinate of the map element
     * @param image the image representing the map element
     */
    public MapElement(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    /**
     * Returns the x-coordinate of the map element.
     *
     * @return the x-coordinate of the map element
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the map element.
     *
     * @param x the new x-coordinate of the map element
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the map element.
     *
     * @return the y-coordinate of the map element
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the map element.
     *
     * @param y the new y-coordinate of the map element
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the image representing the map element.
     *
     * @return the image representing the map element
     */
    public Image getImage() {
        return image;
    }

    /**
     * Draws the map element on the specified graphics context.
     *
     * @param g the graphics context on which to draw the map element
     */
    public abstract void draw(Graphics g);
}
