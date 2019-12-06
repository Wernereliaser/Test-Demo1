
package pong;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author werner
 */
public class Tennis extends Applet implements Runnable, KeyListener {
    final int WIDTH = 700, HEIGHT = 500;
    Thread thread;
    HumanPaddle p1;
    AIPaddle p2;
    Ball b1;
    boolean gameStarted;
    Graphics gfx;
    Image img;
    
    
    @Override
    public void init() {
        this.resize(WIDTH, HEIGHT);
        gameStarted = false;
        this.addKeyListener(this);
        p1 = new HumanPaddle(1);
        b1 = new Ball();
        p2 = new AIPaddle(2, b1);
        img = createImage(WIDTH,HEIGHT);
        gfx = img.getGraphics();
        thread = new Thread(this);
        thread.start();

    }

    @Override
    public void paint(Graphics g) {
        gfx.setColor(Color.blue);
        gfx.fillRect(0, 0, WIDTH, HEIGHT);

        if (b1.getX() < -10 || b1.getX() > 710) {
            g.setColor(Color.red);
            g.drawString("Game over", 300, 350);
        } else {
            p1.draw(gfx);
            b1.draw(gfx);
            p2.draw(gfx);
        }
        
        if (!gameStarted) {
            gfx.setColor(Color.YELLOW);
            gfx.drawString("Tennis game", 300, 100);
            gfx.drawString("Press enter to start", 283, 130); //
        }
//        Scores
// g.setColor(Color.white);
//        g.setFont(new Font("serif",Font.BOLD,25));
//       g.drawString("Scores: ", 300, 350);
//        //Image
      g.drawImage(img, 0, 0, this);
    }

    @Override
    public void update(Graphics g) {
       paint(g);
    }

    @Override
    public void run() {
        for (;;) {
            if (gameStarted) {
                p1.move();
                p2.move();
                b1.move();
                b1.checkPaddleCollission(p1, p2);   
            }
            repaint();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
 
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            repaint();
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                p1.setUpAccel(true);
                break;
            case KeyEvent.VK_DOWN:
                p1.setDownAccel(true);
                break;
            case KeyEvent.VK_ENTER:
                gameStarted = true;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            p1.setUpAccel(false);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            p1.setDownAccel(false);
        }
    }
}
