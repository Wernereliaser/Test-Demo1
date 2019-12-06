
package pong;

import java.awt.Graphics;

/**
 *
 * @author werner
 */
public interface Paddle {
    public void draw(Graphics g);
    public void move();
    public int getY();
}
