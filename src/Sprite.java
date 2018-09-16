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

    boolean collisionDetection(Mario m, Sprite s)
    {
        if(m.x > s.x + s.w)
            return false;
        if(m.x + m.w < s.x)
            return false;
        if(m.y > s.y + s.h)
            return false;
        if(m.y + m.h < s.y)
            return false;

        collisionHandler(m, s);
        return true;
    }

    private void collisionHandler(Mario m, Sprite s)
    {
        if(m.y <= s.y + s.h && m.prevY > s.y + s.h ) // Hits bottom
        {
            m.y = s.y + s.h + 1;
            m.verticalVelocity = 0;
        }
        else if (m.x <= s.x + s.w && m.prevX > s.x + s.w) // Hits right
            m.x = s.x + s.w + 1;
        else if(m.y + m.h >= s.y && m.prevY + m.h < s.y) // Lands on top
        {
            m.y = s.y - m.h - 1;
            m.isGrounded = true;
            m.verticalVelocity = 0;
            m.marioJumpTime = 0;
        }
        else if (m.x + m.w >= s.x && m.prevX < s.x) // Hit left
            m.x = s.x - m.w - 1;
    }
}
