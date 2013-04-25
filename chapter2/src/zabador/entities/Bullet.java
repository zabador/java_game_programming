package zabador.entities;

import java.awt.*;
import java.awt.Rectangle;

public class Bullet extends BaseVectorShape{

    public Bullet(){

        setShape(new Rectangle(0,0,1,1));
        setAlive(false);
    }

    public Rectangle getBounds(){

        return new Rectangle((int)getX(), (int)getY(), 1,1);
    }
}
