import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Mario extends Sprite
{
    int prevX;
    int prevY;

    final int marioMovementSpeed = 10;
    int marioImageIndex; //Integer that keeps track of the current mario sprite
    int marioJumpTime; //Contains how many frames it has been since Mario has been on solid ground
    double verticalVelocity;
    boolean isGrounded;
    boolean isFacingRight; //boolean to determine if mario is facing right

    private static BufferedImage[] marioImages;
    private static BufferedImage[] reversedMarioImages;

    Mario(int x, int y) // Normal constructor
    {
        this.x = x;
        this.y = y;
        w = 60;
        h = 95;
        marioImageIndex = 0;
        isFacingRight = true;
        isGrounded = true;

        marioImages = loadMarioImages("mario");
        reversedMarioImages = loadMarioImages("rmario");
    }

    Mario(Mario copyMario)
    {
        this.x = copyMario.x;
        this.y = copyMario.y;
        this.w = copyMario.y;
        this.h = copyMario.h;

        this.prevX = copyMario.prevX;
        this.prevY = copyMario.prevY;

        this.marioImageIndex = copyMario.marioImageIndex;
        this.marioJumpTime = copyMario.marioJumpTime;
        this.verticalVelocity = copyMario.verticalVelocity;
        this.isGrounded = copyMario.isGrounded;
        this.isFacingRight = copyMario.isFacingRight;
    }

    @Override
    public Sprite clone(Model m, Sprite s)
    {
        return (new Mario((Mario)s));
    }

    Mario(Model model, Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        marioImageIndex = 0;
        isFacingRight = true;
        isGrounded = true;
    }

    private BufferedImage[] loadMarioImages(String fileName) //Loads the mario images into a new image array and returns it
    {
        BufferedImage[] images = new BufferedImage[5];

        for(int i = 0; i < 5; i++)
        {
            try
            {
                images[i] = ImageIO.read(new File(fileName + Integer.toString(i+1) + ".png"));
            }
            catch (Exception e)
            {
                e.printStackTrace(System.err);
                System.exit(1);
            }
        }
        return images;
    }

    public void update()
    {
        verticalVelocity += 1.2;
        y += verticalVelocity;

        //Prevents mario from falling below the ground
        if(y > 500)
        {
            verticalVelocity = 0.0; //Will basically ground mario, so it will make the vertical velocity 0
            y = 500; // snap back to the ground
        }

        if(verticalVelocity == 0.0) //Mario is grounded
        {
            isGrounded = true;
            marioJumpTime = 0;
        }
        else
        {
            //This block will execute if mario is in the air
            isGrounded = false;
            marioJumpTime++; //Counts how many frames mario has been in the air
        }
    }

    public void draw(Graphics g)
    {
        if(isFacingRight)
            g.drawImage(marioImages[marioImageIndex],500,y,null);
        else
            g.drawImage(reversedMarioImages[marioImageIndex],500,y,null);
    }

    void jump()
    {

        if(marioJumpTime < 25 && verticalVelocity <=0) //Mario can only stay in the air for so long
        {
            if(isGrounded)
            {
                if(true)
                {
                    locationOfMarioPast();
                    verticalVelocity = -23;
                }
            }
        }
    }

    void locationOfMarioPast()
    {
        prevX = x;
        prevY = y;
    }

    void marioImageCycle()
    {
        if(marioImageIndex != 4)
            marioImageIndex++;
        else
            marioImageIndex = 0;
    }

}
