package com.mygdx.game.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.mainSettings.GameSettings;

import java.util.Random;

public class TrashObject extends GameObject{
    int livesLeft;
    private static final int paddingHorizontal = 30;
    public TrashObject(int width, int height, String texturePath, World world) {
        super(
                texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt((GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width)),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                world, GameSettings.TRASH_BIT
        );
        body.setLinearVelocity(new Vector2(0, -GameSettings.TRASH_VELOCITY));
        livesLeft = 1;
    }
    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }
    @Override
    public void hit(){
        livesLeft -= 1;
    }
    public boolean isAlive(){
        return livesLeft > 0;
    }
}
