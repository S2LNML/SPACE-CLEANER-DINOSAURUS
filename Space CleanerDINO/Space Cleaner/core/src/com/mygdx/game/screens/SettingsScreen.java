package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.mainSettings.GameResources;
import com.mygdx.game.mainSettings.MyGdxGame;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.ImageView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.view.TextView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {
    MovingBackgroundView backgroundView;
    ImageView imageView;
    ButtonView buttonView;
    TextView titleView, musicView, soundsView, recordsView;
    MyGdxGame myGdxGame;
    public SettingsScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        imageView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        buttonView = new ButtonView(280, 447, 200, 60, myGdxGame.commonBlackFont, GameResources.BUTTON_BACKGROUND_IMG_PATH, "RETURN");
        titleView = new TextView(myGdxGame.largeWhiteFont, 256, 956, "SETTINGS");
        musicView = new TextView(myGdxGame.commonWhiteFont, 173, 717, "MUSIC: " + translateStateToText(MemoryManager.loadIsMusicOn()));
        soundsView = new TextView(myGdxGame.commonWhiteFont, 173, 658, "SOUNDS: " + translateStateToText(MemoryManager.loadIsSoundsOn()));
        recordsView = new TextView(myGdxGame.commonWhiteFont, 173, 599, "CLEAR RECORDS");
    }
    @Override
    public void render(float delta){

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        titleView.draw(myGdxGame.batch);
        imageView.draw(myGdxGame.batch);
        musicView.draw(myGdxGame.batch);
        soundsView.draw(myGdxGame.batch);
        recordsView.draw(myGdxGame.batch);
        buttonView.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }
    @Override
    public void dispose(){
        backgroundView.dispose();
        titleView.dispose();
        imageView.dispose();
        musicView.dispose();
        soundsView.dispose();
        recordsView.dispose();
        buttonView.dispose();
    }
    public void handleInput(){
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (buttonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (recordsView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveTableOfRecords(new ArrayList<>());
                recordsView.setText("CLEAR RECORDS");
            }
            if (musicView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
                musicView.setText("MUSIC: " + translateStateToText(MemoryManager.loadIsMusicOn()));
                myGdxGame.audioManager.updateMusicFlag();
            }
            if (soundsView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundsOn());
                soundsView.setText("SOUNDS: " + translateStateToText(MemoryManager.loadIsSoundsOn()));
                myGdxGame.audioManager.updateSoundFlag();
            }
        }
    }
    private String translateStateToText(boolean state){
        return state ? "ON" : "OFF";
    }
}
