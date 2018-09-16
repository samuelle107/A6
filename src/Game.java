import javax.swing.JFrame;
import java.awt.Toolkit;

public class Game extends JFrame
{
    //Member variables
    Model model;
    View view;
    Controller controller;
    Mario mario;

    public Game()
    {
        mario = new Mario();
        model = new Model(); //Making a new Model object.
        model.sprites.add(mario); //Adds Mario to the sprite arrayList
        controller = new Controller(model, mario); //Making a new Controller object.
        view = new View(controller, model); //Making a View object and passing in the Controller object, controller.

        //"this" refers to the current object.
        view.addMouseListener(controller);
        this.addKeyListener(controller);

        //Setting a bunch of stuff for the window
        this.setTitle("Legend of Mario");
        this.setSize(1000, 1000);
        this.setFocusable(true);
        this.getContentPane().add(view);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void run()
    {
        while(true)
        {
            controller.update();
            model.update();
            view.repaint(); // Indirectly calls View.paintComponent
            Toolkit.getDefaultToolkit().sync(); // Updates screen

            // Go to sleep for 50 miliseconds
            try
            {
                Thread.sleep(40);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public static void main(String[] args)
    {
        Game g = new Game();
        g.run();
    }
}
