package sokoban;

import java.awt.Graphics;
import java.awt.Image;

/**
 * @author Ben Collins 21006366 
 * @since 15/05/2024 
 * @version Version 1
 * The `Floor` class represents a floor tile in the Sokoban game.
 * It extends the `MapElement` class and provides functionality for drawing the floor tile on the game map.
 */
public class Floor extends MapElement {

    /**
     * Constructs a new `Floor` object with the specified coordinates and image.
     *
     * @param x     the x-coordinate of the floor tile
     * @param y     the y-coordinate of the floor tile
     * @param image the image representing the floor tile
     */
    public Floor(int x, int y, Image image) {
        super(x, y, image);
    }
    
    /**
     * Draws the floor tile on the specified graphics context.
     *
     * @param g the graphics context on which to draw the floor tile
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), getX() * TILE_SIZE, getY() * TILE_SIZE, null);
    }

}
