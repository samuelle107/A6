import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Background
{
    BufferedImage background = null;

    public BufferedImage loadImage()
    {
        if(background == null)
        {
            try
            {
                background = ImageIO.read(new File("bg.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
        return background;
    }
}
