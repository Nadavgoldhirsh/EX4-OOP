package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import daynight.Night;
import daynight.Sun;
import daynight.SunHalo;
import world.Block;
import world.Sky;
import world.Terrain;

import java.util.List;
import java.util.Random;

public class PepseGameManager extends GameManager {

    private static final int SEED = 0;
    private static final int CYCLE_LENGTH = 30;

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
        Night myNightCreator = new Night();
        GameObject night =  myNightCreator.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(night,Layer.BACKGROUND);
        GameObject sun = Sun.create(windowController.getWindowDimensions(),CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND);
        GameObject sunHalo = SunHalo.create(sun);
        sunHalo.addComponent((float deltaTime)-> sunHalo.setCenter(sun.getCenter()));
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND);



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