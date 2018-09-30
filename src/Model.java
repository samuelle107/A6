import java.util.ArrayList;
import java.util.Random;

class Model
{
    static ArrayList<Sprite> sprites;

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
        for(int i = 1; i <= 10; i++)
        {
            Random random = new Random();
            int x = random.nextInt(3000) + 500;
            int y = random.nextInt(300) + 200;

            CoinBlock coinBlock = new CoinBlock(x, 500 - y); //Spaces out the coin blocks every 700 pixels
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
        for(int i = 0; i < sprites.size(); i++)
        {
            sprites.get(i).update();
            sprites.get(i).collisionDetection(this, sprites);

            if(sprites.get(i).isCoin())
                deleteCoin(sprites.get(i),i);
        }
    }

    Json marshall()
    {
        Json ob = Json.newObject();
        Json brickList = Json.newList();
        Json coinBlockList = Json.newList();
        Json marioList = Json.newList();

        ob.add("mario", marioList);
        ob.add("bricks", brickList);
        ob.add("coinblocks", coinBlockList);

        for(int i = 0; i < sprites.size(); i++)
        {
            if(sprites.get(i).isMario())
            {
                Mario mario = (Mario)sprites.get(i);
                marioList.add(mario.marshal());
            }
            else if(sprites.get(i).isBrick()) //Checks the sprite arrayList if the index is a brick
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
        sprites.clear();

        Json jsonMarioList = ob.get("mario");
        Json jsonBrickList = ob.get("bricks");
        Json jsonCoinBlockList = ob.get("coinblocks");

        for(int i = 0; i < jsonMarioList.size(); i++)
            sprites.add(new Mario(jsonMarioList.get(i)));

        for(int i = 0; i <jsonBrickList.size(); i++)
            sprites.add(new Brick(jsonBrickList.get(i))); //

        for(int i = 0; i < jsonCoinBlockList.size(); i++)
            sprites.add(new CoinBlock(jsonCoinBlockList.get(i)));
    }
}
