package sokoban;

// Imports
import java.awt.Graphics;
import java.awt.Image;

/**
 * Ben Collins 21006366 
 * 06/06/2024 Version 2.0 
 * The `Floor` class represents a floor tile in the Sokoban game.
 */
public class Floor extends MapElement {

    // Constructor
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
