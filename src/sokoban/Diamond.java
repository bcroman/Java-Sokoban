package sokoban;

// Imports
import java.awt.Graphics;
import java.awt.Image;

/**
 * Ben Collins 21006366
 * 06/06/2024
 * Version 2.0
 * Represents a diamond in the Sokoban game.
 */
public class Diamond extends MapElement {

    //Construstor
    public Diamond(int x, int y, Image image) {
        super(x, y, image);
    }

    /**
     * Draws the diamond on the game board
     *
     * @param g
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(getImage(), getX() * TILE_SIZE, getY() * TILE_SIZE, null);
    }

}
