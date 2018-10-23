import com.sun.org.apache.xpath.internal.operations.Mod;

import javax.jws.WebParam;
import java.awt.event.*;

class Controller implements MouseListener, KeyListener
{
    //Member variables
    private View view;
    private Model model;
    Mario mario;

    private boolean keyLeft;
    private boolean keyRight;
    private boolean keySpace;
    private boolean coinBlockSwitch;

    private int preXLocation; //X coordinate on mouse press
    private int preYLocation; //Y coordinate on mouse press
    private int postXLocation; //X coordinate on mouse release
    private int postYLocation; //Y coordinate on mouse release

    Controller(Model m) //Constructor
    {
        model = m; //I pass in the model object to this constructor and call it "m".  It is then assigned to model in this class.
        this.mario = model.mario;
    }

    void setView(View v)
    {
        view = v;
    }

    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT: keyRight = true; break;
            case KeyEvent.VK_LEFT: keyLeft = true; break;
            case KeyEvent.VK_L: Json j = Json.load("maps.json"); model.unMarshal(j); break;
            case KeyEvent.VK_S: model.marshall().save("maps.json"); break;
            case KeyEvent.VK_C: coinBlockSwitch = !coinBlockSwitch; break;
            case KeyEvent.VK_SPACE:
            {
                keySpace = true;
                if(mario.marioJumpTime  == 0) //Short jump
                    mario.jump();
                else if (mario.marioJumpTime > 2) //Long jump
                    mario.jump();
            }
            break;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT: keyRight = false; mario.marioImageIndex = 3;  break;
            case KeyEvent.VK_LEFT: keyLeft = false; mario.marioImageIndex = 3;   break;
            case KeyEvent.VK_SPACE: keySpace = false; break;
        }
    }

    public void keyTyped(KeyEvent e)
    {

    }

    private void evaluateBest()
    {
        double runScore = model.evaluateAction(Model.marioActions.RUN, 0);
        double jumpScore = model.evaluateAction(Model.marioActions.JUMP, 0);
        double waitScore = model.evaluateAction(Model.marioActions.WAIT, 0);
        double runAndJumpScore = model.evaluateAction(Model.marioActions.RUN_AND_JUMP, 0);

        System.out.println("r : " + runScore);
        System.out.println("j : " + jumpScore);
        System.out.println("w : " + waitScore);
        System.out.println("rj: " + runAndJumpScore);

        if(runAndJumpScore > runScore && runAndJumpScore > waitScore && runAndJumpScore > jumpScore)
        {
            model.doAction(Model.marioActions.RUN_AND_JUMP);
            System.out.println("RUN AND JUMP");
        }
        else if(jumpScore > runScore && jumpScore > waitScore)
        {
            model.doAction(Model.marioActions.JUMP);
            System.out.println("JUMP");
        }
        else if(waitScore > runScore)
        {
            model.doAction(Model.marioActions.WAIT);
            System.out.println("WAIT");
        }
        else
        {
            model.doAction(Model.marioActions.RUN);
            System.out.println("RUN");
        }
        System.out.println();
    }

    void update() //This function updates every few ms and updates the model's location based on the keypress
    {
        mario.locationOfMarioPast(); //Gets the 'current' position of mario and stores it in the 'previous' variables

        //Handles Mario's movement and stores mario's new location in the current variables
        if(keyLeft)
        {
            mario.isFacingRight = false;
            mario.marioImageCycle();
            mario.x -= mario.marioMovementSpeed;
        }
        if(keyRight)
        {
            mario.isFacingRight = true; //When the player moves right, Mario is facing right
            mario.marioImageCycle();
            mario.x += mario.marioMovementSpeed;
        }

        evaluateBest();
    }

    public void mousePressed(MouseEvent e)
    {
        //Getting the preCoordinates of the brick
        preXLocation = e.getX();
        preYLocation = e.getY();
    }

    public void mouseReleased(MouseEvent e)
    {
        int xFinal; //The final value for the x coordinate
        int yFinal; //The final value for the y coordinate
        int w; //Magnitude of the difference of the pre and post x coordinates
        int h; //Magnitude of the difference of the pre and post y coordinates

        //Getting the postCoordinates of the brick
        postXLocation = e.getX();
        postYLocation = e.getY();

        //Calculating the absolute width and height
        w = Math.abs(postXLocation - preXLocation);
        h = Math.abs(postYLocation - preYLocation);

        //Determines the final location of the brick based on the way the box is drawn.  This is because the image is always drawn in the left corner.
        if(postYLocation > preYLocation)
        {
            if(postXLocation > preXLocation)
            {
                xFinal = preXLocation;
                yFinal = preYLocation;
            }
            else
            {
                xFinal = preXLocation - w;
                yFinal = postYLocation - h;
            }
        }
        else
        {
            if(postXLocation > preXLocation)
            {
                xFinal = postXLocation - w;
                yFinal = preYLocation - h;
            }
            else
            {
                xFinal = postXLocation;
                yFinal = postYLocation;
            }
        }
        if(coinBlockSwitch)
            model.addCoinBlock(xFinal + (mario.x - 500), yFinal,w,h); //Adds the tube to the array
        else
            model.addBrick(xFinal + (mario.x - 500), yFinal,w,h); //Adds the tube to the array
    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {

    }

    public void mouseClicked(MouseEvent e)
    {

    }
}
