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

    abstract public boolean isCoinBlock();

    abstract public boolean isCoin();

    boolean collisionDetection(Model model, Sprite t, Sprite o) //Model, Target and object  Model is passed through for unmarshalling
    {
        if(t.x > o.x + o.w )
            return false;
        if(t.x + t.w < o.x)
            return false;
        if(t.y > o.y + o.h)
            return false;
        if(t.y + t.h < o.y)
            return false;
        collisionHandler(model, t, o); //Goes through this function if and only if it detects intersection
        return true;
    }

    private void collisionHandler(Model model, Sprite t, Sprite o)
    {

        if(t.isMario() && !o.isCoin()) //Handles collision if t is Mario.  Prevents mario from being hit by coins
        {
            Mario m = (Mario)t; //Casts t to Mario if t is Mario

            if(m.y <= o.y + o.h && m.prevY > o.y + o.h) // Hits bottom
            {
                if(o.isCoinBlock()) //If the object that the target hit was a CoinBlock, then it will generate a coin
                    generateCoin(model, o);

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

    private void generateCoin(Model model, Sprite cb)
    {
        ((CoinBlock) cb).addCoin(model, cb.x, cb.y - 75); //adds a coin at the location of the coin block and shifts up
    }
}
