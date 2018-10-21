import java.util.ArrayList;
import java.util.Random;

class Model
{
    int coins;
    int jumpCount;

    ArrayList<Sprite> sprites;
    Mario mario;

    public enum marioActions{ RUN, JUMP, RUN_AND_JUMP, WAIT, RUN_BACK }

    Model() // Normal constructor
    {
        this.mario = new Mario(500, 500);
        this.sprites = new ArrayList<>();
        this.sprites.add(mario);
    }

    Model(Model copyModel) // Copy Constructor
    {
        this.coins = copyModel.coins;
        this.jumpCount = copyModel.jumpCount;

        this.sprites = new ArrayList<>();
        for(Sprite s : copyModel.sprites)
            this.sprites.add(s.clone(this, s));
        this.mario = (Mario)this.sprites.get(0);
    }

    double evaluateAction(marioActions action, int depth)
    {
        int d = 20;
        int k = 7;

        // Evaluate the state
        if(depth >= d)
            return this.mario.x - this.jumpCount + (coins * 100);

        // Simulate the action
        Model copy = new Model(this);
        copy.doAction(action);
        copy.update();

        // Recurse
        if(depth % k != 0)
            return copy.evaluateAction(action, depth + 1);
        else
        {
            double best = copy.evaluateAction(marioActions.RUN, depth + 1);
            best = Math.max(best, copy.evaluateAction(marioActions.JUMP, depth + 1));
            best = Math.max(best, copy.evaluateAction(marioActions.WAIT, depth + 1));
            best = Math.max(best, copy.evaluateAction(marioActions.RUN_AND_JUMP, depth + 1));
            return best;
        }
    }

    void doAction(marioActions action)
    {
        if(action == marioActions.RUN)
        {
            this.mario.isFacingRight = true;
            this.mario.x += this.mario.marioMovementSpeed;
            this.mario.marioImageCycle();
        }
        else if(action == marioActions.RUN_AND_JUMP)
        {
            this.mario.x += this.mario.marioMovementSpeed;
            this.mario.marioImageCycle();
            this.mario.jump();
            this.jumpCount++;
        }
        else if(action == marioActions.WAIT)
        {
            this.mario.x = this.mario.prevX;
        }
        else if(action == marioActions.JUMP)
        {
            this.mario.x = this.mario.prevX;
            this.mario.jump();
            this.jumpCount++;
        }
        else if (action == marioActions.RUN_BACK)
        {
            this.mario.isFacingRight = false;
            this.mario.x -= this.mario.marioMovementSpeed;
            this.mario.marioImageCycle();
        }
        else
        {
            throw new RuntimeException("This was not suppose to happen");
        }
    }

    void addBrick(int x, int y, int w, int h)
    {
        //Create a new Brick object called b and adds it to the sprites array
        Brick b = new Brick(this, x, y, w, h);
        sprites.add(b);
    }

    void addCoinBlock(int x, int y, int w, int h)
    {
        CoinBlock cb = new CoinBlock(this, x, y, w, h);
        sprites.add(cb);
    }

    private void deleteCoin(Sprite c, int index)
    {
        if(c.y > 1500) //y =1500 is off the screen, so once it gets there, remove the coin from the arrayList
            sprites.remove(index);
    }

    int scrollPos()
    {
        return sprites.get(0).x - 500;
    }

    void update()
    {
        for(int i = 0; i < sprites.size(); i++) // Not a for each loop since I cannot modify content during iteration
        {
            sprites.get(i).update();
            sprites.get(i).collisionDetection(this, sprites);

            if(sprites.get(i) instanceof Coin)
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
            if(sprites.get(i) instanceof Mario)
            {
                Mario mario = (Mario)sprites.get(i);
                marioList.add(mario.marshal());
            }
            else if(sprites.get(i) instanceof Brick) //Checks the sprite arrayList if the index is a brick
            {
                Brick brick = (Brick)sprites.get(i); //Casts the brick sprite to a brick
                brickList.add(brick.marshal());
            }
            else if(sprites.get(i) instanceof CoinBlock)
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
            sprites.add(new Mario(this, jsonMarioList.get(i)));

        for(int i = 0; i <jsonBrickList.size(); i++)
            sprites.add(new Brick(this, jsonBrickList.get(i))); //

        for(int i = 0; i < jsonCoinBlockList.size(); i++)
            sprites.add(new CoinBlock(this, jsonCoinBlockList.get(i)));
        mario = (Mario)sprites.get(0);
    }
}
