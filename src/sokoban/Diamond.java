package sokoban;

import java.awt.Graphics;
import java.awt.Image;

/**
 * The Diamond class represents a diamond element in the Sokoban game.
 * It extends the MapElement class and provides methods to draw the diamond on the game board.
 */
public class Diamond extends MapElement {

    /**
     * Constructs a new Diamond object with the specified coordinates and image.
     *
     * @param x     the x-coordinate of the diamond
     * @param y     the y-coordinate of the diamond
     * @param image the image representing the diamond
     */
    public Diamond(int x, int y, Image image) {
        super(x, y, image);
    }

    /**
     * Draws the diamond on the game board using the specified graphics context.
     *
     * @param g the graphics context to draw on
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), getX() * TILE_SIZE, getY() * TILE_SIZE, null);
    }

}
