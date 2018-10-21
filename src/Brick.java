import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Brick extends Sprite
{
    private static BufferedImage brickImage; //Static because the image is shared between all of the objects

    //Brick constructor
    Brick(Model model, int x, int y, int w, int h)
    {
        this.model = model;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    //Un-marshaling constructor.  Extracts the data from the JSON file and stores it in the member variables
    Brick(Model model, Json ob)
    {
        this.model = model;
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
    }

    Brick(Model m, Brick copyBrick)
    {
        this.x = copyBrick.x;
        this.y = copyBrick.y;
        this.w = copyBrick.w;
        this.h = copyBrick.h;
        this.model = m;
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
        g.drawImage(loadBrickImage(), x - this.model.scrollPos(), y, w, h, null);
    }

    @Override
    public Sprite clone(Model m, Sprite s)
    {
        return (new Brick(m, (Brick)s));
    }
}
