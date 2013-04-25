package zabador.entities;

import java.awt.Shape;

public class BaseVectorShape{

    private Shape shape;
    private boolean alive;
    private double x,y;
    private double velX, velY;
    private double moveAngle, faceAngle;

    public BaseVectorShape(){
        this.shape = null;
        this.alive = false;
        this.x = 0.0;
        this.y = 0.0;
        this.velX = 0.0;
        this.velY = 0.0;
        this.moveAngle = 0.0;
        this.faceAngle = 0.0;
    }

    public Shape getShape(){
        return this.shape;
    }

    public boolean isAlive(){
        return this.alive;
    }

    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }

    public double getVelX(){
        return this.velX;
    }

    public double getVelY(){
        return this.velY;
    }

    public double getMoveAngle(){
        return this.moveAngle;
    }

    public double getFaceAngle(){
        return this.faceAngle;
    }

    public void setShape(Shape shape){
        this.shape = shape;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

    public void incX(double i){
        this.x += i;
    }

    public void incY(double i){
        this.y += i;
    }

    public void setVelX(double velX){
        this.velX = velX;
    }

    public void setVelY(double velY){
        this.velY = velY;
    }

    public void incVelX(double i){
        this.velX += i;
    }

    public void incVelY(double i){
        this.velY += i;
    }

    public void setFaceAngle(double faceAngle){
        this.faceAngle = faceAngle;
    }

    public void setMoveAngle(double moveAngle){
        this.moveAngle = moveAngle;
    }

    public void incFaceAngle(double i){
        this.faceAngle += i;
    }

    public void incMoveAngle(double i){
        this.moveAngle += i;
    }
}
