import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class Coin extends Sprite
{
    private BufferedImage coinImage;

    private double verticalVelocity;
    private double horizontalVelocity;

    Coin(Model model, int x, int y)
    {
        this.model = model;
        this.x = x;
        this.y = y;
        w = 75;
        h = 75;
        //Gives the coin a random vertical velocity when the coin generates
        Random random = new Random();
        verticalVelocity = -15;
        horizontalVelocity = random.nextInt(10 + 1 + 10) - 10;
    }

    Coin(Model m, Coin copyCoin)
    {
        this.x = copyCoin.x;
        this.y = copyCoin.y;
        this.w = copyCoin.w;
        this.h = copyCoin.h;
        this.model = m;
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

        x += horizontalVelocity;
    }

    public void draw(Graphics g)
    {
        g.drawImage(loadImage(),x - this.model.scrollPos(), y, w, h, null);
    }

    @Override
    public Sprite clone(Model m, Sprite s)
    {
        return (new Coin(m, (Coin)s));
    }
}
