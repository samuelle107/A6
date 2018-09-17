import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Coin extends Sprite
{
    private BufferedImage coinImage;

    private double verticalVelocity;

    Coin(int x, int y)
    {
        this.x = x;
        this.y = y;
        w = 75;
        h = 75;
        //Gives the coin a random vertical velocity when the coin generates
        Random random = new Random();
        verticalVelocity = -(random.nextInt(20) + 5);
    }

    private BufferedImage loadImage()
    {
        if(coinImage == null)
        {
            try
            {
                coinImage = ImageIO.read(new File("coin.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
        return coinImage;
    }

    public void update()
    {
        verticalVelocity += 1.2;
        this.y += verticalVelocity;
    }

    public void draw(Graphics g)
    {
        g.drawImage(loadImage(),x - (Mario.scrollPos - 500), y, w, h, null);
    }

    public boolean isBrick() {
        return false;
    }

    public boolean isMario() {
        return false;
    }

    public boolean isCoinBlock() {
        return false;
    }

    public boolean isCoin() {
        return true;
    }
}
