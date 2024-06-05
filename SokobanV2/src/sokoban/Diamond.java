package sokoban;

import java.awt.Graphics;
import java.awt.Image;

/**
 * B@author Ben Collins 21006366 
 * @since 15/05/2024 
 * @version Version 1
 * Diamond class is used to store the 
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
