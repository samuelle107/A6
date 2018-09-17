import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CoinBlock extends Sprite
{
    static BufferedImage coinBlockImage;
    static BufferedImage coinBlockImageEmpty;

    Model model;

    int coinCounter;

    CoinBlock(Model m, int x, int y)
    {
        model = m;
        loadImage();
        this.x = x;
        this.y = y;
        this.w = 75;
        this.h = 75;
    }

    public void loadImage()
    {
        if(coinBlockImage == null && coinBlockImage == null)
        {
            try
            {
                coinBlockImage = ImageIO.read(new File("block1.png"));
                coinBlockImageEmpty = ImageIO.read(new File("block2.png"));
            }
            catch(Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
    }

    public void addCoin(int x, int y)
    {
        if(coinCounter < 5)
        {
            coinCounter++;
            Coin coin = new Coin(x, y);
            model.sprites.add(coin);
        }
    }

    public void update()
    {

    }

    public void draw(Graphics g)
    {
        if(coinCounter < 5)
            g.drawImage(coinBlockImage,x - (Mario.scrollPos - 500), y, w, h, null);
        else
            g.drawImage(coinBlockImageEmpty,x - (Mario.scrollPos - 500), y, w, h, null);

    }

    public boolean isBrick()
    {
        return false;
    }

    public boolean isMario()
    {
        return false;
    }

    public boolean isCoinBlock() {
        return true;
    }

    public boolean isCoin() {
        return false;
    }
}
