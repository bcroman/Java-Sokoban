package sokoban;

// Imports
import java.awt.Image;
import java.awt.Graphics;

/**
 * Ben Collins 21006366
 * 06/06/2024
 * Version 2
 * Represents the wall in the Sokoban game.
 */

public class Wall extends MapElement {

    // Constructor
    public Wall(int x, int y, Image image) {
        super(x, y, image);
    }

    /**
     * Moves the Wall by the specified amount in the x and y directions.
     *
     * @param dx the amount to move in the x direction
     * @param dy the amount to move in the y direction
     */
    public void move(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    /**
     * Draws the Wall on the specified graphics context.
     *
     * @param g the graphics context to draw on
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), getX() * TILE_SIZE, getY() * TILE_SIZE, null);
    }
}
