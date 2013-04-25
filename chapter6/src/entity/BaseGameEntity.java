package entity;
public class BaseGameEntity extends Object {

    protected boolean alive;
    protected double x,y;
    protected double velX, velY;
    protected double moveAngle, faceAngle;

    public BaseGameEntity() {

        setAlive(false);
        setX(0.0);
        setY(0.0);
        setVelX(0.0);
        setVelY(0.0);
        setMoveAngle(0.0);
        setFaceAngle(0.0);
    }

    public boolean isAlive() {
        return this.alive;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getVelX() {
        return this.velX;
    }

    public double getVelY() {
        return this.velY;
    }

    public double getMoveAngle() {
        return this.moveAngle;
    }

    public double getFaceAngle() {
        return this.faceAngle;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public void setMoveAngle(double moveAngle) {
        this.moveAngle = moveAngle;
    }

    public void incMoveAngle(double angle) {
        this.moveAngle += angle;
    }

    public void setFaceAngle(double faceAngle) {
        this.faceAngle = faceAngle;
    }

    public void incFaceAngle(double angle) {
         this.faceAngle += angle;
    }
}
