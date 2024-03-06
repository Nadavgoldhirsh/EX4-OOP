package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import daynight.Night;
import daynight.Sun;
import daynight.SunHalo;
import world.*;


import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class PepseGameManager extends GameManager {

    private static final int SEED = 0;
    private static final int CYCLE_LENGTH = 30;
    private static final int AVATAR_X_DIM = 50;
    private static final int AVATAR_Y_DIM = 80;
    private static final float START_ENERGY = 100;
    private static final int COUNTERLENGTH = 50;
    private static final int FACTOR = -3;
    private static final Vector2 ADJ = Vector2.of(5,80);
    private static Avatar avatar;
    private static Energy energy;


    public static void main(String[] args) {
        new PepseGameManager().run();

    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Random random = new Random(SEED);
        createSky(windowController);
        createTerrain(windowController, random);
        GameObject night =  Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night,Layer.BACKGROUND);
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        sunHalo.addComponent((float deltaTime)-> sunHalo.setCenter(sun.getCenter()));
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND);
        avatar = new Avatar(Vector2.of(windowController.getWindowDimensions().x()/2-AVATAR_X_DIM,
                Terrain.groundHeightAt(windowController.getWindowDimensions().x()/2-AVATAR_X_DIM)-AVATAR_Y_DIM),
                inputListener, imageReader);
        gameObjects().addGameObject(avatar);
        energy = new Energy(windowController.getWindowDimensions().add(
                new Vector2(COUNTERLENGTH, COUNTERLENGTH).mult(FACTOR).add(ADJ)),
                new Vector2(COUNTERLENGTH, COUNTERLENGTH),
                new TextRenderable("" + START_ENERGY), avatar::getEnergy);
        gameObjects().addGameObject(energy);

    }

    private void createSky(WindowController windowController) {
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
    }

    private void createTerrain(WindowController windowController, Random random) {
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), random.nextInt());
        List<Block> myBlockList = terrain.createInRange(0,(int) windowController.getWindowDimensions().x());
        for (Block curBlock: myBlockList){
            gameObjects().addGameObject(curBlock, Layer.STATIC_OBJECTS);
        }
    }


}