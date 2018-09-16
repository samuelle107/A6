import java.awt.event.*;

class Controller implements MouseListener, KeyListener
{
    //Member variables
    View view;
    Model model;
    Mario mario;

    private boolean keyLeft;
    private boolean keyRight;
    private boolean keyUp;
    private boolean keyDown;
    private boolean keySpace;

    private int preXLocation; //X coordinate on mouse press
    private int preYLocation; //Y coordinate on mouse press
    private int postXLocation; //X coordinate on mouse release
    private int postYLocation; //Y coordinate on mouse release


    Controller(Model m, Mario ma) //Constructor
    {
        model = m; //I pass in the model object to this constructor and call it "m".  It is then assigned to model in this class.
        mario = ma;
    }

    void setView(View v)
    {
        view = v;
    }

    public void keyPressed(KeyEvent e)
    {
         //Movement speed of the camera

        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT: keyRight = true; break;
            case KeyEvent.VK_LEFT: keyLeft = true; break;
            case KeyEvent.VK_UP: keyUp = true; break;
            case KeyEvent.VK_DOWN: keyDown = true; break;
            case KeyEvent.VK_L: Json j = Json.load("maps.json"); model.unMarshal(j); break;
            case KeyEvent.VK_S: model.marshal().save("maps.json"); break;
            case KeyEvent.VK_SPACE:
            {
                keySpace = true;
                if(mario.canJump)
                {
                    if(mario.marioJumpTime  == 0) //Short jump
                        mario.jump(false);
                    else if (mario.marioJumpTime > 2) //Long jump
                        mario.jump(true);
                }


            } break;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_RIGHT: keyRight = false; mario.marioImageIndex = 3;  break;
            case KeyEvent.VK_LEFT: keyLeft = false; mario.marioImageIndex = 3;   break;
            case KeyEvent.VK_UP: keyUp = false; break;
            case KeyEvent.VK_DOWN: keyDown = false; break;
            case KeyEvent.VK_SPACE: keySpace = false; mario.canJump = false; break;
        }
    }

    public void keyTyped(KeyEvent e)
    {

    }

    void update() //This function updates every few ms and updates the model's location based on the keypress
    {
        mario.locationOfMarioPast(); //Gets the 'current' position of mario and stores it in the 'previous' variables

        if(mario.isGrounded)
            mario.canJump = true;

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
