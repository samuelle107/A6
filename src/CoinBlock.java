import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CoinBlock extends Sprite
{
    private static BufferedImage coinBlockImage;
    private static BufferedImage coinBlockImageEmpty;

    int coinCounter;

    CoinBlock(Model model, int x, int y, int w, int h)
    {
        this.model = model;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    CoinBlock(Model m, CoinBlock copyCoinBlock)
    {
        this.x = copyCoinBlock.x;
        this.y = copyCoinBlock.y;
        this.w = copyCoinBlock.w;
        this.h = copyCoinBlock.h;
        this.coinCounter = copyCoinBlock.coinCounter;
        this.model = m;
    }

    public Sprite clone(Model m, Sprite s)
    {
        return (new CoinBlock(m, (CoinBlock)s));
    }

    CoinBlock(Model model, Json ob)
    {
        this.model = model;
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
    }

    private void loadImage()
    {
        if(coinBlockImage == null && coinBlockImageEmpty == null)
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

    void addCoin(Model m, int x, int y)
    {
        if(this.coinCounter < 5) //If mario hits it 5 times, then it will stop generating coins
        {
            m.coins++;
            coinCounter++;
            Coin coin = new Coin(m, x, y);
            m.sprites.add(coin);
        }
    }

    public void update()
    {

    }

    public void draw(Graphics g)
    {
        loadImage();
        //Picture depends on if the block is empty or not
        if(coinCounter < 5)
            g.drawImage(coinBlockImage,x - model.scrollPos(), y, w, h, null);
        else
            g.drawImage(coinBlockImageEmpty,x - model.scrollPos(), y, w, h, null);

    }
}
