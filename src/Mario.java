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
    boolean canJump; //Variable that tries to prevent mario from double jumping

    private static BufferedImage[] marioImages;
    private static BufferedImage[] reversedMarioImages;

    static int scrollPos; //Static variable that keeps track of mario's position.  Allows for stationary objects to use to determine where they should be

    Mario()
    {
        x = 500;
        y = 500; //Initializes mario to be at y = 500
        w = 60;
        h = 95;
        marioImageIndex = 0;
        isFacingRight = true;
        isGrounded = true;

        marioImages = loadMarioImages("mario");
        reversedMarioImages = loadMarioImages("rmario");
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
        scrollPos = x;
    }

    public void draw(Graphics g)
    {
        if(isFacingRight)
            g.drawImage(marioImages[marioImageIndex],500,y,null);
        else
            g.drawImage(reversedMarioImages[marioImageIndex],500,y,null);
    }

    public boolean isMario()
    {
        return true;
    }

    public boolean isCoinBlock() {
        return false;
    }

    public boolean isCoin() {
        return false;
    }

    public boolean isBrick()
    {
        return false;
    }

    void jump(boolean longJump)
    {
        if(marioJumpTime < 25 && verticalVelocity <=0) //Mario can only stay in the air for so long
        {
            if(isGrounded)
            {
                if(!longJump)
                {
                    locationOfMarioPast();
                    verticalVelocity = -23;
                }
            }
            else if(longJump)
            {
                locationOfMarioPast();
                verticalVelocity = -9;
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
