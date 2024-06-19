package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class View implements Disposable {
    float x, y, width, height;
    public View (float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public View (float x, float y){
        this.x = x;
        this.y = y;
    }
    @Override
    public void dispose() {

    }
    public void draw(SpriteBatch batch){

    }
    public boolean isHit(float tx, float ty) {
        return (tx >= x && tx <= x + width && ty >= y && ty <= y + height);
    }
}
