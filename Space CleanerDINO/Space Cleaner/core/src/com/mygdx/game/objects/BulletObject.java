package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.mainSettings.GameSettings;

public class BulletObject extends GameObject{
    boolean wasHit;
    public BulletObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, world, GameSettings.BULLET_BIT);
        body.setLinearVelocity(new Vector2(0, GameSettings.BULLET_VELOCITY));
        body.setBullet(true);
        wasHit = false;
    }
    public boolean hasToBeDestroyed(){
        return (getY() - height / 2 > GameSettings.SCREEN_HEIGHT) || wasHit;
    }
    @Override
    public void hit(){
        wasHit = true;
    }
}
