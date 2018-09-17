import javax.swing.JPanel;
import java.awt.*;
import java.util.Iterator;

class View extends JPanel //The view class shows what the user will see.
{
    //Member variables
    private Model model;
    private Background bg;

    View(Controller c, Model m)
    {
        model = m;
        c.setView(this);
        bg = new Background();
    }

    public void paintComponent(Graphics g)
    {
        g.drawImage(bg.loadImage(),-(int)(model.sprites.get(0).x * 0.1f),0,null); //Loads the background

        Iterator<Sprite> it = model.sprites.iterator();
        while(it.hasNext())
            it.next().draw(g);

        g.setColor(Color.gray);
        g.drawLine(0,596,2000,596); //Draws the ground
    }
}
