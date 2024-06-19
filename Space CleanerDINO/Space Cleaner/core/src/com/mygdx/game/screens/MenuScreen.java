package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.mainSettings.GameResources;
import com.mygdx.game.mainSettings.MyGdxGame;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.view.TextView;

public class MenuScreen extends ScreenAdapter {
    ButtonView startButton, settingsButton, exitButton;
    TextView titleView;
    MovingBackgroundView backgroundView;
    MyGdxGame myGdxGame;

    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleView = new TextView(myGdxGame.largeWhiteFont, 170, 960, "SPACE CLEANER");
        startButton = new ButtonView(140, 646, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "START");
        settingsButton = new ButtonView(140, 551, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "SETTINGS");
        exitButton = new ButtonView(140, 456, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "EXIT");
    }

    @Override
    public void render(float delta) {
        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.BLACK);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        titleView.draw(myGdxGame.batch);
        startButton.draw(myGdxGame.batch);
        settingsButton.draw(myGdxGame.batch);
        exitButton.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (startButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            if (settingsButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.settingsScreen);
            }
                if (exitButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                    Gdx.app.exit();
                }
            }
        }

    @Override
    public void dispose(){
        backgroundView.dispose();
        titleView.dispose();
        startButton.dispose();
        settingsButton.dispose();
        exitButton.dispose();
    }
}


