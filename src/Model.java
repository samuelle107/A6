import java.util.ArrayList;
import java.util.Iterator;

class Model
{
    ArrayList<Sprite> sprites; //ArrayList of sprites

    Model()
    {
        sprites = new ArrayList<Sprite>(); //Whenever this is called in Game.java, it will create an AL of Sprite
    }

    public void addBrick(int x, int y, int w, int h)
    {
        //Create a new Brick object called b and adds it to the sprites array
        Brick b = new Brick(x, y, w, h);
        sprites.add(b);
    }

    public void update()
    {
        Mario mario = (Mario)sprites.get(0); //Casting the first sprite (which is a mario) to a mario object

        Iterator<Sprite> it = sprites.iterator();
        while(it.hasNext())
        {
            Sprite s = it.next();
            s.update();
            if(!s.isMario()) // Checks collision on not mario objects
                s.collisionDetection(mario,s);
        }
    }

    //Marshall Method
    Json marshal()
    {
        Json ob = Json.newObject();
        Json brickList = Json.newList();
        ob.add("bricks", brickList);

        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isBrick()) //Checks the sprite arrayList if the index is a brick
            {
                Brick brick = (Brick)sprites.get(i); //Casts the brick sprite to a brick
                brickList.add(brick.marshal());
            }
        }
        return ob;
        }

    //Unmarshal method
    void unMarshal (Json ob)
    {
        for(int i = sprites.size() -1; i > 0; i--) //deletes all of the current bricks that are loaded in the game.  Does not clear Mario
            sprites.remove(i);
        Json jsonList = ob.get("bricks"); //Creating a Json object that targets bricks
        for(int i = 0; i <jsonList.size(); i++)  //Goes through the json object and adds all of the bricks to the sprite arrayList
            sprites.add(new Brick(jsonList.get(i)));
    }
}
