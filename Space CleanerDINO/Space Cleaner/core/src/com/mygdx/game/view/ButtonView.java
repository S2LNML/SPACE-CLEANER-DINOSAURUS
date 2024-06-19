package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ButtonView extends View{
    Texture texture;
    BitmapFont font;
    String text;
    float textX, textY;
    public ButtonView(float x, float y, float width, float height, BitmapFont font, String texturePath, String text) {
        super(x, y, width, height);

        this.text = text;
        this.font = font;

        texture = new Texture(texturePath);

        GlyphLayout glyphLayout = new GlyphLayout(font, text);
        float textWidth = glyphLayout.width;
        float textHeight = glyphLayout.height;

        textX = x + (width - textWidth) / 2;
        textY = y + (height + textHeight) / 2;
    }
    public ButtonView(float x, float y, float width, float height, String texturePath) {
        super(x, y, width, height);
        texture = new Texture(texturePath);
    }
    @Override
    public void draw(SpriteBatch batch){
        batch.draw(texture, x, y, width, height);
        if (font != null) font.draw(batch, text, textX, textY);
    }
    @Override
    public void dispose(){
        texture.dispose();
        if (font != null) font.dispose();
    }
}
