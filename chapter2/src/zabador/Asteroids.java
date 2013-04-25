package zabador;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.*;

import zabador.entities.Ship;
import zabador.entities.Bullet;
import zabador.entities.Asteroid;

public class Asteroids extends Applet implements Runnable, KeyListener{

    Thread gameloop; // main thread becomes game loop
    BufferedImage backbuffer;// use this as a double buffer
    Graphics2D g2d;// the main drawing object for the back buffer
    boolean showBounds = false; //toggle for drawing bounding boxes
    
    //create asteroid array
    final int ASTEROIDS = 20;
    Asteroid[] ast = new Asteroid[ASTEROIDS];

    // create bullet array
    final int BULLETS = 10;
    Bullet[] bullet = new Bullet[BULLETS];
    int currentBullet = 0;

    // the player's ship
    Ship ship = new Ship();

    // create the identity transform
    AffineTransform identity = new AffineTransform();

    //create a ranom number
    Random rand = new Random();

    public void init(){

        //create the back buffer for smooth graphics
        backbuffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        g2d = backbuffer.createGraphics();

        //set up the ship
        ship.setX(320);
        ship.setY(240);

        //set up the bullets
        for(int n = 0; n<BULLETS; n++)
            bullet[n] = new Bullet();

        //create the asteroids
        for(int n = 0; n<ASTEROIDS; n++){
            ast[n] = new Asteroid();
            ast[n].setRotationVelocity(rand.nextInt(3)+1);
            ast[n].setX((double)rand.nextInt(600)+20);
            ast[n].setY((double)rand.nextInt(440)+20);
            ast[n].setMoveAngle(rand.nextInt(360));
            double ang = ast[n].getMoveAngle()-90;
            ast[n].setVelX(calcAngleMoveX(ang));
            ast[n].setVelY(calcAngleMoveY(ang));
        }

        //start the user input listener
        addKeyListener(this);
    }

    public void update(Graphics g){

       //start off transforms at identiy
       g2d.setTransform(identity);

       //erase the background
       g2d.setPaint(Color.BLACK);
       g2d.fillRect(0,0,getSize().width,getSize().height);

       //print some status information
       g2d.setColor(Color.WHITE);
       g2d.drawString("Ship: "+ Math.round(ship.getX())+", "+Math.round(ship.getY()),5,10);
       g2d.drawString("Move angle: "+ Math.round(ship.getMoveAngle())+90,5,25);
       g2d.drawString("Face angle: "+ Math.round(ship.getFaceAngle())+90,5,40);

       //draw the game graphics
       drawShip();
       drawBullets();
       drawAsteroids();

       //repaint the applet window
       paint(g);
    }

    public void drawShip(){

        g2d.setTransform(identity);
        g2d.translate(ship.getX(), ship.getY());
        g2d.rotate(Math.toRadians(ship.getFaceAngle()));
        g2d.setColor(Color.ORANGE);
        g2d.fill(ship.getShape());
    }

    public void drawBullets(){

        //iterate through the bullet array
        for (int i = 0;i<BULLETS;i++){
            //is the bullet currently in use
            if(bullet[i].isAlive()){
                //draw the bullet
                g2d.setTransform(identity);
                g2d.translate(bullet[i].getX(), bullet[i].getY());
                g2d.setColor(Color.MAGENTA);
                g2d.draw(bullet[i].getShape());
            }
        }
    }

    public void drawAsteroids(){

        for(int i=0;i<ASTEROIDS;i++){
            if(ast[i].isAlive()){
                g2d.setTransform(identity);
                g2d.translate(ast[i].getX(), ast[i].getY());
                g2d.rotate(Math.toRadians(ast[i].getMoveAngle()));
                g2d.setColor(Color.DARK_GRAY);
                g2d.fill(ast[i].getShape());
            }
        }
    }

    public void paint(Graphics g){
        //draw the back buffer onto the applet window
        g.drawImage(backbuffer, 0,0,this);
    }

    public void start(){
    
        gameloop = new Thread(this);
        gameloop.start();
    }

    public void run(){
        //acquire the current thread
        Thread t = Thread.currentThread();

        //keep going as long as the thread is alive
        while(t == gameloop){
            try{
                //update game loop
                gameUpdate();

                //target framerate is 50 fps
                Thread.sleep(20);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            repaint();
        }
    }

    public void stop(){
        //kill game loop
        gameloop = null;
    }

    public void gameUpdate(){

        updateShip();
        updateBullets();
        updateAsteroids();
        checkCollisions();
    }

    public void updateShip(){

        //update ship's X position
        ship.incX(ship.getVelX());

        //wrap around left/right
        if(ship.getX()<-10)
            ship.setX(getSize().width +10);
        else if (ship.getX() > getSize().width + 10)
            ship.setX(-10);

        //update ship's Y position
        ship.incY(ship.getVelY());

        //warp around top/botton
        if(ship.getY()<-10)
            ship.setY(getSize().height +10);
        else if (ship.getY() > getSize().height + 10)
            ship.setY(-10);
    }

    public void updateBullets(){

        // move each of the bullets
        for(int i=0;i<BULLETS;i++){

            //is the bullet alive
            if(bullet[i].isAlive()){
                //update bullet x position
                bullet[i].incX(bullet[i].getVelX());

                //kill bullet if it goes of screen
                if(bullet[i].getX() < 0 || bullet[i].getX() > getSize().width)
                    bullet[i].setAlive(false);

                //update bullet y position
                bullet[i].incY(bullet[i].getVelY());

                //kill bullet if it goes of screen
                if(bullet[i].getY() < 0 || bullet[i].getY() > getSize().height)
                    bullet[i].setAlive(false);
            }
        }
    }

    public void updateAsteroids(){

        for(int i=0;i<ASTEROIDS;i++){
            if(ast[i].isAlive()){

                //update x value
                ast[i].incX(ast[i].getVelX());

                //warp left/right
                if(ast[i].getX() < -20)
                    ast[i].setX(getSize().width+20);
                else if (ast[i].getX() > getSize().width +20)
                    ast[i].setX(-20);

                //update y value
                ast[i].incX(ast[i].getVelX());

                //warp top/bottom
                if(ast[i].getY() < -20)
                    ast[i].setY(getSize().height+20);
                else if (ast[i].getY() > getSize().height +20)
                    ast[i].setY(-20);

                //update rotation value
                ast[i].incMoveAngle(ast[i].getRotationVelocity());

                //keep the agle within 0-359 degrees
                if(ast[i].getMoveAngle() < 0)
                    ast[i].setMoveAngle(360 - ast[i].getRotationVelocity());
                else if (ast[i].getMoveAngle() > 360)
                    ast[i].setMoveAngle(ast[i].getRotationVelocity());
            }
        }
    }

    public void checkCollisions(){

        for(int a=0;a<ASTEROIDS;a++){
            if(ast[a].isAlive()){
                //check for collisions with bullet
                for(int b = 0;b<BULLETS;b++){
                    if(bullet[b].isAlive()){
                        //perform collison test
                        if(ast[a].getBounds().contains(bullet[b].getX(), bullet[b].getY())){
                            bullet[b].setAlive(false);
                            ast[a].setAlive(false);
                            continue;
                        }
                    }
                }

                //check for collision with ship
                if(ast[a].getBounds().intersects(ship.getBounds())){
                    ast[a].setAlive(false);
                    ship.setX(320);
                    ship.setY(240);
                    ship.setFaceAngle(0);
                    ship.setVelX(0);
                    ship.setVelY(0);
                    continue;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent k){ }
    @Override
    public void keyTyped(KeyEvent k){ }
    @Override
    public void keyPressed(KeyEvent k){

        int keyCode = k.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_LEFT:
                //left arrow rotates ship left 5 degress
                ship.incFaceAngle(-5);
                if(ship.getFaceAngle() < 0)
                    ship.setFaceAngle(360-5);
                break;
            case KeyEvent.VK_RIGHT:
                //right arrow rotates ship 5 degrees
                ship.incFaceAngle(5);
                if(ship.getFaceAngle()>360)
                    ship.setFaceAngle(5);
                break;
            case KeyEvent.VK_UP:
                //up arrow adds thrust to ship (1/10 normal speed)
                ship.setMoveAngle(ship.getFaceAngle()-90);
                ship.incVelX(calcAngleMoveX(ship.getMoveAngle())*0.1);
                ship.incVelY(calcAngleMoveY(ship.getMoveAngle())*0.1);
                break;
            //CTRL, Enter, or space can fire bullet
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_SPACE:
                currentBullet++;
                if(currentBullet > BULLETS -1)
                    currentBullet=0;
                bullet[currentBullet].setAlive(true);

                //point bullet in direction ship is facing
                bullet[currentBullet].setX(ship.getX());
                bullet[currentBullet].setY(ship.getY());
                bullet[currentBullet].setMoveAngle(ship.getFaceAngle()-90);

                //fire bullet at angle of ship
                double angle = bullet[currentBullet].getMoveAngle();
                double svx = ship.getVelX();
                double svy = ship.getVelY();
                bullet[currentBullet].setVelX(svx + calcAngleMoveX(angle)*2);
                bullet[currentBullet].setVelY(svy + calcAngleMoveY(angle)*2);
                break;
        }
    }

    public double calcAngleMoveX(double angle){
        return (double) (Math.cos(angle * Math.PI / 180));
    }

    public double calcAngleMoveY(double angle){
        return (double) (Math.sin(angle * Math.PI / 180));
    }

}
