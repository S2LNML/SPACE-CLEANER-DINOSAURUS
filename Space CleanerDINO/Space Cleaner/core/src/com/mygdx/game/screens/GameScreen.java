package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.mainSettings.GameState;
import com.mygdx.game.managers.ContactManager;
import com.mygdx.game.mainSettings.GameResources;
import com.mygdx.game.mainSettings.GameSession;
import com.mygdx.game.mainSettings.GameSettings;
import com.mygdx.game.managers.MemoryManager;
import com.mygdx.game.view.ButtonView;
import com.mygdx.game.view.ImageView;
import com.mygdx.game.view.LiveView;
import com.mygdx.game.view.MovingBackgroundView;
import com.mygdx.game.mainSettings.MyGdxGame;
import com.mygdx.game.objects.BulletObject;
import com.mygdx.game.objects.ShipObject;
import com.mygdx.game.objects.TrashObject;
import com.mygdx.game.view.RecordsListView;
import com.mygdx.game.view.TextView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {
    RecordsListView recordsListView;
    ButtonView pauseButton, homeButton, continueButton, homeButton2;
    TextView scoreTextView, pauseTextView, recordsTextView;
    LiveView liveView;
    ImageView imageView, fullBlackoutView;
    MovingBackgroundView movingBackgroundView;
    MyGdxGame myGdxGame;
    GameSession gameSession;
    ShipObject shipObject;

    ArrayList<TrashObject> trashArray;
    ArrayList<BulletObject> bulletArray;
    ContactManager contactManager;

    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();
        movingBackgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        imageView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        liveView = new LiveView(305, 1215);
        scoreTextView = new TextView(myGdxGame.commonWhiteFont, 50, 1215);
        pauseButton = new ButtonView(605, 1200, 46, 54, GameResources.PAUSE_IMG_PATH);
        fullBlackoutView = new ImageView(0, 0, GameResources.FULL_BLACKOUT_IMG_PATH);
        pauseTextView = new TextView(myGdxGame.commonWhiteFont, 330, 1000, "PAUSED");
        homeButton = new ButtonView(200, 800, 160, 60, myGdxGame.commonWhiteFont, GameResources.BUTTON_BACKGROUND_IMG_PATH, "HOME" );
        continueButton = new ButtonView(400, 800, 180, 60, myGdxGame.commonWhiteFont, GameResources.BUTTON_BACKGROUND_IMG_PATH, "CONTINUE");
        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 690);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 842, "LAST RECORDS");
        homeButton2 = new ButtonView(280, 365, 160, 70, myGdxGame.commonBlackFont,
                GameResources.BUTTON_BACKGROUND_IMG_PATH,
                "HOME");

        contactManager = new ContactManager(myGdxGame.world);

        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();

        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
    }

    @Override
    public void show() {
        restartGame();
    }

    @Override
    public void render(float delta) {
        handleInput();

        if (gameSession.state == GameState.PLAYING){
        if (gameSession.shouldSpawnTrash()) {
            TrashObject trashObject = new TrashObject(
                    GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                    GameResources.TRASH_IMG_PATH,
                    myGdxGame.world
            );
            trashArray.add(trashObject);
        }
        if (shipObject.needToShoot()) {
            BulletObject laserBullet = new BulletObject(
                    shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                    GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                    GameResources.BULLET_IMG_PATH,
                    myGdxGame.world
            );
            bulletArray.add(laserBullet);
            if (myGdxGame.audioManager.isSoundsOn) myGdxGame.audioManager.shootSound.play(0.1f);
        }
        if (!shipObject.isAlive()) {
            gameSession.endGame();
            recordsListView.setRecords(MemoryManager.loadRecordsTable());
        }
        updateTrash();
        updateBullets();
        movingBackgroundView.move();
        gameSession.updateScore();
        scoreTextView.setText("Score " + gameSession.getScore());
        liveView.setLeftLives(shipObject.getliveLeft());

        myGdxGame.stepWorld();
        }
        draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    shipObject.move(myGdxGame.touch);
                    break;
                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
                case ENDED:
                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)){
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }
        }
    }

    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        movingBackgroundView.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) trash.draw(myGdxGame.batch);
        shipObject.draw(myGdxGame.batch);
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        imageView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);
        if (gameSession.state == GameState.PAUSED){
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED){
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
        }
        myGdxGame.batch.end();
    }

    private void updateTrash() {
        for (int i = 0; i < trashArray.size(); i++) {
            boolean hasToBeDestroyed = !trashArray.get(i).isAlive() || !trashArray.get(i).isInFrame();
            if (!trashArray.get(i).isAlive()) {
                if (myGdxGame.audioManager.isSoundsOn) myGdxGame.audioManager.explosionSound.play(0.4f);
            }
            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(trashArray.get(i).body);
                trashArray.remove(i--);
            }
        }
    }

    private void updateBullets() {
        for (int i = 0; i < bulletArray.size(); i++) {
            if (bulletArray.get(i).hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bulletArray.get(i).body);
                bulletArray.remove(i--);
            }
        }
    }
    private void restartGame(){
        for (int i = 0; i < trashArray.size(); i++) {
            myGdxGame.world.destroyBody(trashArray.get(i).body);
            trashArray.remove(i--);
        }
        if (shipObject != null){
            myGdxGame.world.destroyBody(shipObject.body);
        }
        shipObject = new ShipObject(GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world);
        bulletArray.clear();
        gameSession.startGame();
    }

}

