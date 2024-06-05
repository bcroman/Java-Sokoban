package sokoban;

import java.awt.Image;
import java.awt.Graphics;

/**
 * @author Ben Collins 21006366 
 * @since 15/05/2024 
 * @version Version 1
 * Represents the Wall in the Sokoban game.
 * The Wall will stop the player walking outside the map.
 */

public class Wall extends MapElement {
    /**
     * Constructs a new Wall object with the specified coordinates and image.
     *
     * @param x     the x-coordinate of the Wall
     * @param y     the y-coordinate of the Wall
     * @param image the image representing the Wall
     */
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
