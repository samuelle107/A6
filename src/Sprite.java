import java.awt.*;

abstract public class Sprite
{
    int x;
    int y;
    int w;
    int h;

    abstract public void update();

    abstract public void draw(Graphics g);

    abstract public boolean isBrick();

    abstract public boolean isMario();

    boolean collisionDetection(Sprite t, Sprite o) //Target and object
    {
        if(t.x > o.x + o.w )
            return false;
        if(t.x + t.w < o.x)
            return false;
        if(t.y > o.y + o.h)
            return false;
        if(t.y + t.h < o.y)
            return false;
        collisionHandler(t, o);
        return true;
    }

    private void collisionHandler(Sprite t, Sprite o)
    {
        if(t.isMario()) //Handles collision if t is Mario
        {
            Mario m = (Mario)t; //Casts t to Mario if t is Mario

            if(m.y <= o.y + o.h && m.prevY > o.y + o.h) // Hits bottom
            {
                m.y = o.y + o.h + 1;
                m.verticalVelocity = 0;
            }
            else if(m.x <= o.x + o.w && m.prevX > o.x + o.w ) //Hits right wall
            {
                m.x = o.x + o.w + 1;
                Mario.scrollPos = m.x;
            }
            else if(m.y + m.h >= o.y && m.prevY + m.h < o.y) //Hits top wall
            {
                m.y = o.y - m.h - 1;
                m.isGrounded = true;
                m.verticalVelocity = 0.0;
                m.marioJumpTime = 0;
            }
            else if(m.x + m.w >= o.x && m.prevX < o.x) //Hits left wall
            {
                m.x = o.x - m.w - 1;
                Mario.scrollPos = m.x;
            }
        }
    }
}
