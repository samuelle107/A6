import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Brick extends Sprite
{
    private static BufferedImage brickImage; //Static because the image is shared between all of the objects

    //Brick constructor
    Brick(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

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

    //Un-marshaling constructor.  Extracts the data from the JSON file and stores it in the member variables
    Brick(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
    }

    //Function to load the brick image
    private BufferedImage loadBrickImage()
    {
        //Prevents the image from trying to load if it is already equal to something
        if(brickImage == null)
        {
            try
            {
                brickImage = ImageIO.read(new File("brick.jpg"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
        return brickImage;
    }

    public void update()
    {

    }

    public void draw(Graphics g)
    {
        g.drawImage(loadBrickImage(), x - (Mario.scrollPos - 500), y, w, h, null);
    }

    public boolean isMario()
    {
        return false;
    }

    public boolean isCoinBlock() {
        return false;
    }

    public boolean isCoin() {
        return false;
    }

    public boolean isBrick()
    {
        return true;
    }
}
