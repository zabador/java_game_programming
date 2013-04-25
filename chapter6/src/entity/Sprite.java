package entity;
import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Sprite extends Object {

    private ImageEntity entity;
    protected Point pos;
    protected Point vel;
    protected double rotRate;
    protected int currentState;

    public Sprite(JFrame a, Graphics2D g2d) {
        entity = new ImageEntity(a);
        entity.setGraphics(g2d);
        entity.setAlive(false);
        pos = new Point(0, 0);
        vel = new Point(0, 0);
        rotRate = 0.0;
        currentState = 0;
    }

    //load bitmap file
    public void load(String filename) {
        entity.load(filename);
    }

    //perform affine transformations
    public void transform() {
        entity.setX(pos.x);
        entity.setY(pos.y);
        entity.transform();
    }

    //draw the image
    public void draw() {
        entity.g2d.drawImage(entity.getImage(), entity.at, entity.frame);
    }

    //draw bounding rectangle around sprite
    public void drawBounds(Color c) {
        entity.g2d.setColor(c);
        entity.g2d.draw(getBounds());
    }

    //update the postion based on velocity
    public void updatePostion() {
        pos.x += vel.x;
        pos.y += vel.y;
    }

    //methods relate to automatic rotation factor
    public double rotationRate() {
        return this.rotRate;
    }

    public void setRotationRate(double rate) {
        rotRate = rate;
    }

    public void updateRotation() {
        setFaceAngle(faceAngle() + rotRate);
        if(faceAngle() < 0)
            setFaceAngle(360 - rotRate);
        else if(faceAngle() > 360)
            setFaceAngle(rotRate);
    }

    //generic sprite state variable(alive, dead, collied, etc)
    public int state() {
        return this.currentState;
    }

    public void setState(int state) {
        this.currentState = state;
    }

    //returns a bounding rectangle
    public Rectangle getBounds() {
        return entity.getBounds();
    }

    //sprite postion
    public Point position() {
        return this.pos;
    }

    public void setPosition(Point pos) {
        this.pos = pos;
    }

    //sprite movement velocity
    public Point velocity() {
        return vel;
    }

    public void setVelocity(Point vel) {
        this.vel = vel;
    }

    //returns the center of the sprite as a Point
    public Point center() {
        int x = (int)entity.getCenterX();
        int y = (int)entity.getCenterY();
        return(new Point(x,y));
    }

    //generic variable for selectively using sprites
    public boolean alive() {
        return entity.isAlive();
    }

    public void setAlive(boolean alive) {
        entity.setAlive(alive);
    }

    //face angle indicates which direction sprite is facing
    public double faceAngle() {
        return entity.getFaceAngle();
    }

    public void setFaceAngle(double angle) {
        entity.setFaceAngle(angle);
    }

    public void setFaceAngle(float angle) {
        entity.setFaceAngle((double)angle);
    }

    public void setFaceAngle(int angle) {
        entity.setFaceAngle((double)angle);
    }

    public double moveAngle() {
        return entity.getMoveAngle();
    }

    public void setMoveAngle(double angle) {
        entity.setMoveAngle(angle);
    }

    public void setMoveAngle(float angle) {
        entity.setMoveAngle((double)angle);
    }

    public void setMoveAngle(int angle) {
        entity.setMoveAngle((double)angle);
    }

    //returns the source image width / height
    public int imageWidth() {
        return entity.width();
    }

    public int imageHeight() {
        return entity.height();
    }

    //check for collision with a rectangular shape
    public boolean collidesWith(Rectangle rect) {
        return (rect.intersects(getBounds()));
    }

    // check for collision with another sprite
    public boolean collidesWith(Sprite sprite) {
        return (getBounds().intersects(sprite.getBounds()));
    }

    //check for collision with a point
    public boolean collidesWith(Point point) {
        return (getBounds().contains(point.x, point.y));
    }

    public JFrame frame() {
        return entity.frame;
    }

    public Graphics2D graphics() {
        return entity.g2d;
    }

    public Image image() {
        return entity.image;
    }

    public void setImage(Image image) {
        entity.setImage(image);
    }
}
