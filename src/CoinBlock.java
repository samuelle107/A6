import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CoinBlock extends Sprite
{
    private static BufferedImage coinBlockImage;
    private static BufferedImage coinBlockImageEmpty;

    private Model model;

    private int coinCounter; //Counts how many times mario has hit the block

    CoinBlock(Model m, int x, int y)
    {
        model = m;
        loadImage();
        this.x = x;
        this.y = y;
        this.w = 75;
        this.h = 75;
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

    void addCoin(int x, int y)
    {
        if(coinCounter < 5) //If mario hits it 5 times, then it will stop generating coins
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
        //Picture depends on if the block is empty or not
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
