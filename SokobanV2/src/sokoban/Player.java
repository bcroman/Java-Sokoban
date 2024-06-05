package sokoban;

import java.awt.Image;
import java.awt.Graphics;

/**
 * @author Ben Collins 21006366
 * @since 15/05/2024
 * @version Version 1 Represents the player in the Sokoban game. The player can
 * move around the game map.
 */
public class Player extends MapElement {

    /**
     * Constructs a new Player object with the specified coordinates and image.
     *
     * @param x the x-coordinate of the player
     * @param y the y-coordinate of the player
     * @param image the image representing the player
     */
    public Player(int x, int y, Image image) {
        super(x, y, image);
    }

    /**
     * Moves the player by the specified amount in the x and y directions.
     *
     * @param dx the amount to move in the x direction
     * @param dy the amount to move in the y direction
     */
    public void move(int dx, int dy) {
        setX(getX() + dx);
        setY(getY() + dy);
    }

    /**
     * Draws the player on the game map.
     *
     * @param g the graphics object to draw on
     */
    @Override
public void draw(Graphics g) {
    g.drawImage(getImage(), getX() * 32, getY() * 32, null); // Assuming each tile is 32x32 pixels
}

}
