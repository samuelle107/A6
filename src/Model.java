import java.util.ArrayList;

class Model
{
    ArrayList<Sprite> sprites;

    Model()
    {
        sprites = new ArrayList<>(); //Whenever this is called in Game.java, it will create an AL of Sprite
    }

    void addBrick(int x, int y, int w, int h)
    {
        //Create a new Brick object called b and adds it to the sprites array
        Brick b = new Brick(x, y, w, h);
        sprites.add(b);
    }

    void addCoinBlock()
    {
        for(int i = 1; i <= 5; i++)
        {
            CoinBlock coinBlock = new CoinBlock(700* i, 350); //Spaces out the coin blocks every 700 pixels
            sprites.add(coinBlock);
        }
    }


    private void deleteCoin(Sprite c, int index)
    {
        if(c.y > 1500) //y =1500 is off the screen, so once it gets there, remove the coin from the arrayList
            sprites.remove(index);
    }

    void update()
    {
        Mario m = (Mario)sprites.get(0); //Casting the first sprite (which is mario) to a mario object
        for(int i = 0; i < sprites.size(); i++)
        {
            sprites.get(i).update(); //Updates all of the sprites

            if(!sprites.get(i).isMario()) //Collision logic for mario and an object
                sprites.get(i).collisionDetection(this, m, sprites.get(i));

            if(sprites.get(i).isCoin()) //Handles coin deletion
                deleteCoin(sprites.get(i),i);
        }
    }

    Json marshal()
    {
        Json ob = Json.newObject();
        Json brickList = Json.newList();
        Json coinBlockList = Json.newList();

        ob.add("bricks", brickList);
        ob.add("coinblock", coinBlockList);

        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isBrick()) //Checks the sprite arrayList if the index is a brick
            {
                Brick brick = (Brick)sprites.get(i); //Casts the brick sprite to a brick
                brickList.add(brick.marshal());
            }
            else if(sprites.get(i).isCoinBlock())
            {
                CoinBlock coinBlock = (CoinBlock)sprites.get(i);
                coinBlockList.add(coinBlock.marshal());
            }
        }
        return ob;
        }

    void unMarshal (Json ob)
    {
        for(int i = sprites.size() -1; i > 0; i--) //deletes all of the current bricks that are loaded in the game.  Does not clear Mario
            sprites.remove(i);

        Json jsonBrickList = ob.get("bricks"); //Creating a Json object that targets bricks
        Json jsonCoinBlockList = ob.get("coinblock"); //Creating a Json object that targets coinblock

        for(int i = 0; i <jsonBrickList.size(); i++)  //Goes through the json object and adds all of the bricks to the sprite arrayList
            sprites.add(new Brick(jsonBrickList.get(i))); //

        for(int i = 0; i < jsonCoinBlockList.size(); i++)
            sprites.add(new CoinBlock(jsonCoinBlockList.get(i)));
    }
}
