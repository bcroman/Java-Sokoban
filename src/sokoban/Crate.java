package sokoban;

// Imports
import java.awt.Image;
import java.awt.Graphics;

/**
 * Ben Collins 21006366
 * 05/06/2024
 * Version 2.0
 * Represents a crate in the Sokoban game.
 */
public class Crate extends MapElement {

    // Constructor
    public Crate(int x, int y, Image image) {
        super(x, y, image);
    }

    /**
     * Moves the crate by the specified amount in the x and y directions.
     *
     * @param dx the amount to move in the x direction
     * @param dy the amount to move in the y direction
     */
    public void move(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    /**
     * Changes the image of the crate.
     *
     * @param image the new image of the crate
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Draws the crate on the game map.
     *
     * @param g the graphics object to draw on
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), getX() * TILE_SIZE, getY() * TILE_SIZE, null); // Assuming each tile is 32x32 pixels
    }
}
