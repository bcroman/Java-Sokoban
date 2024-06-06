package sokoban;

// Imports
import java.awt.Image;
import java.awt.Graphics;

/**
 * Ben Collins 21006366
 * 06/06/2024
 * Version 2
 * Represents the player in the Sokoban game.
 */
public class Player extends MapElement {

    // Constructor
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
