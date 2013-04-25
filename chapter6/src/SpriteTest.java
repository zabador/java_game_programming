import entity.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.net.*;
import java.util.*;

public class SpriteTest extends JFrame implements Runnable {
    int screenWidth = 640;
    int screenHeight = 480;

    //double buffer
    BufferedImage backbuffer;
    Graphics2D g2d;

    Sprite asteroid;
    ImageEntity background;
    Thread gameloop;
    Random rand = new Random();

    public static void main(String[] args) {
        new SpriteTest();
    }

    public SpriteTest() {
        super("Sprite Test");
        setSize(screenWidth, screenHeight);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create the back buffer for smooth graphics
        backbuffer = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();

        //load the background
        background = new ImageEntity(this);
        background.load("assets/bluespace.png");

        //load the asteroid sprite
        asteroid = new Sprite(this, g2d);
        asteroid.load("assets/asteroid2.png");

        gameloop = new Thread(this);
        gameloop.start();
    }

    public void run() {
        Thread t = Thread.currentThread();
        int i = 0;
        int j = 0;
        while (t == gameloop) {
            try{
                Thread.sleep(30);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            //draw the backgournd
            g2d.drawImage(background.getImage(), 0,0,screenWidth-1, screenHeight-1, this);

            int width = screenWidth - asteroid.imageWidth() - 1;
            int height = screenHeight - asteroid.imageHeight() - 1;

            Point point = new Point(i+=20, j+=20);
            asteroid.setPosition(point);
            asteroid.transform();
            asteroid.draw();

            repaint();

            if(i > screenWidth || j > screenWidth){
                j = 0; i = 0;
            }
        }
    }

        public void paint(Graphics g) {
            g.drawImage(backbuffer, 0,0,this);
        }
}
