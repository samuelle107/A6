import java.awt.*;
import java.util.ArrayList;

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

    // Marshals this object into a JSON DOM
    Json marshal()
    {
        Json ob = Json.newObject(); //Makes a new JSON file and adds the parameters of the bricks to it
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    boolean collisionDetection(Model model, ArrayList<Sprite> sprites) //Model, Target and object  Model is passed through for unmarshalling
    {
        for(int i = 0; i < sprites.size(); i++)
        {
            if(this != sprites.get(i))
            {
                if(this.x > sprites.get(i).x + sprites.get(i).w)
                    return false;
                if(this.x + this.w < sprites.get(i).x)
                    return false;
                if(this.y > sprites.get(i).y + sprites.get(i).h)
                    return false;
                if(this.y + this.h < sprites.get(i).y)
                    return false;

                collisionHandler(model, sprites.get(i)); //Goes through this function if and only if it detects intersection
                return true;
            }
        }
        return true;
    }

    private void collisionHandler(Model model, Sprite sprite) // "This" will refer to what ever the sprite is hitting
    {
        if(sprite.isMario() && !this.isCoin())
        {
            Mario m = (Mario)sprite;

            if(m.y <= this.y + this.h && m.prevY > this.y + this.h) // Hits bottom
            {
                if(this.isCoinBlock())
                    generateCoin(model,this);

                m.y = this.y + this.h + 1;
                m.verticalVelocity = 0;
            }
            else if (m.x <= this.x + this.w && m.prevX > this.x + this.w) // Hits right wall
            {
                m.x = this.x + this.w + 1;
                Mario.scrollPos = m.x;
            }
            else if(m.y + m.h >= this.y && m.prevY + m.h < this.y) // Lands on top
            {
                m.y = this.y - m.h - 1;
                m.isGrounded = true;
                m.verticalVelocity = 0.0;
                m.marioJumpTime = 0;
            }
            else if(m.x + m.w >= this.x && m.prevX < this.x) // Hits left wall
            {
                m.x = this.x - m.w - 1;
                Mario.scrollPos = m.x;
            }
        }
    }

    private void generateCoin(Model model, Sprite cb)
    {
        ((CoinBlock) cb).addCoin(model, cb.x, cb.y - 75); //adds a coin at the location of the coin block and shifts up
    }
}
