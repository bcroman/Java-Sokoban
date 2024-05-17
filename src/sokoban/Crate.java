package sokoban;

import java.awt.Image;
import java.awt.Graphics;

/**
 * Ben Collins 21006366
 * 15/05/2024
 * Version 1
 * Represents a crate in the Sokoban game.
 * Inherits from the MapElement class.
 */
public class Crate extends MapElement {
    /**
     * Constructs a crate object with the specified coordinates and image.
     *
     * @param x     The x-coordinate of the crate.
     * @param y     The y-coordinate of the crate.
     * @param image The image representing the crate.
     */
    public Crate(int x, int y, Image image) {
        super(x, y, image);
    }

    /**
     * Moves the crate by the specified amount in the x and y directions.
     *
     * @param dx The amount to move the crate in the x direction.
     * @param dy The amount to move the crate in the y direction.
     */
    public void move(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    /**
     * Draws the crate on the specified graphics context.
     *
     * @param g The graphics context on which to draw the crate.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), getX() * TILE_SIZE, getY() * TILE_SIZE, null);
    }
}