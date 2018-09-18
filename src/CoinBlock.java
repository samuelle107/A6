import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class CoinBlock extends Sprite
{
    private static BufferedImage coinBlockImage;
    private static BufferedImage coinBlockImageEmpty;

    int coinCounter; //Counts how many times mario has hit the block

    CoinBlock(int x, int y)
    {
        loadImage();
        this.x = x;
        this.y = y;
        this.w = 75;
        this.h = 75;
    }

    // Marshals this object into a JSON DOM
    Json marshal()
    {
        Json ob = Json.newObject(); //Makes a new JSON file and adds the parameters of the coinBLock to it
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        return ob;
    }

    //Un-marshaling constructor.  Extracts the data from the JSON file and stores it in the member variables
    CoinBlock(Json ob)
    {
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
        if(coinCounter < 5) //If mario hits it 5 times, then it will stop generating coins
        {
            coinCounter++;
            Coin coin = new Coin(x, y);
            m.sprites.add(coin);
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
