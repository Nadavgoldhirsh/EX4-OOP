package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.*;
import danogl.util.Vector2;
import pepse.world.trees.Fruit;

import java.util.*;

import java.awt.event.KeyEvent;

/**
 * This class is the avatar of the game
 */
public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final int WAIT = 30;
    private static final String ASSETS_IDLE_0_PNG = "assets/idle_0.png";
    private static final String ASSETS_IDLE_PATH = "assets/idle_";
    private static final String PNG_FOR_PATH = ".png";
    private static final String ASSETS_JUMP_PATH = "assets/jump_";
    private static final String ASSETS_RUN_PATH = "assets/run_";

    private final Deque<GameObject> other = new ArrayDeque<>();
    private static final float BONUS = 10;

    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static float energy = 100;
    private static boolean jumpValue = false;
    private static final float START_ENERGY = 100;
    private static final float JUMP_ENERGY = 10;
    private static final float MOVE_ENERGY = 0.5F;
    private static final float REST_ENERGY = 1;
    private static final int AVATAR_X_DIM = 50;
    private static final int AVATAR_Y_DIM = 78;
    private static final int LEN = 4;
    private static final int RUNLEN = 6;
    private static final double TIME = 0.1;

    private final UserInputListener inputListener;
    private ImageRenderable[] idleArr;
    private ImageRenderable[] jumpArr;
    private ImageRenderable[] runArr;
    private final AnimationRenderable idleAnimation;
    private final AnimationRenderable runAnimation;
    private final AnimationRenderable jumpAnimation;
    private final GameObjectCollection gameObjectCollection;


    /**
     * Class Ctor
     * @param pos the position of the avatar
     * @param inputListener a listener
     * @param imageReader an imagereader
     * @param gameObjectCollection the game collection of the game
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader,
                  GameObjectCollection gameObjectCollection){
        super(pos, Vector2.of(AVATAR_X_DIM,AVATAR_Y_DIM ), new ImageRenderable(imageReader.readImage
                (ASSETS_IDLE_0_PNG,true).getImage()));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.gameObjectCollection = gameObjectCollection;
        idleArr = new ImageRenderable[LEN];
        for (int i = 0; i < idleArr.length; i++){
            idleArr[i] = new ImageRenderable(imageReader.readImage
                    (ASSETS_IDLE_PATH + i + PNG_FOR_PATH,true).getImage());
        }
        jumpArr = new ImageRenderable[LEN];
        for (int i = 0; i < jumpArr.length; i++){
            jumpArr[i] = new ImageRenderable(imageReader.readImage
                    (ASSETS_JUMP_PATH + i + PNG_FOR_PATH,true).getImage());
        }
        runArr = new ImageRenderable[RUNLEN];
        for (int i = 0; i < runArr.length; i++){
            runArr[i] = new ImageRenderable(imageReader.readImage
                    (ASSETS_RUN_PATH + i + PNG_FOR_PATH,true).getImage());
        }
        idleAnimation = new AnimationRenderable(idleArr,TIME);
        runAnimation = new AnimationRenderable(runArr,TIME);
        jumpAnimation = new AnimationRenderable(jumpArr,TIME);
        renderer().setRenderable(idleAnimation);
    }

    /**
     * This method returns the current  energy level
     * @return current  energy level
     */
    public float getEnergy () {
        return energy;
    }

    /**
     * This method returns if the avatar is jumping now
     * @return true iff the avatar is jumping now
     */
    public static boolean getJumpValue() {
        if (jumpValue){
            jumpValue = false;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * This method updates the avatar
     * @param deltaTime The time elapsed, in seconds, since the last frame. Can
     *                  be used to determine a new position/velocity by multiplying
     *                  this delta with the velocity/acceleration respectively
     *                  and adding to the position/velocity:
     *                  velocity += deltaTime*acceleration
     *                  pos += deltaTime*velocity
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if(energy < MOVE_ENERGY) {
            renderer().setRenderable(idleAnimation);
        }
        if(getVelocity().y() != 0 && energy >= MOVE_ENERGY) {
            renderer().setRenderable(jumpAnimation);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy >= MOVE_ENERGY){
            xVel -= VELOCITY_X;
            renderer().setRenderable(runAnimation);
            renderer().setIsFlippedHorizontally(true);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy >= MOVE_ENERGY){
            xVel += VELOCITY_X;
            renderer().setRenderable(runAnimation);
            renderer().setIsFlippedHorizontally(false);
        }
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 && energy >= JUMP_ENERGY) {
            jumpCase();
        }
        if(getVelocity().x() == 0 && getVelocity().y() == 0 && energy <= START_ENERGY){
            noEnergyCase();
        }
        else if((getVelocity().x() != 0) && energy >= MOVE_ENERGY){
            energy -= MOVE_ENERGY;
        }
    }

    private void noEnergyCase() {
        renderer().setRenderable(idleAnimation);
        if(energy + REST_ENERGY > START_ENERGY){
            energy = START_ENERGY;
        }
        else{
            energy += REST_ENERGY;
        }
    }

    private void jumpCase() {
        transform().setVelocityY(VELOCITY_Y);
        energy -= JUMP_ENERGY;
        renderer().setRenderable(jumpAnimation);
        jumpValue = true;
    }

    /***
     * This method runs the collision strategy and updates the bricksAmount counter to have 1 less brick
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     *                  A reasonable elastic behavior can be achieved with:
     *                  setVelocity(getVelocity().flipped(collision.getNormal()));
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals(Fruit.FRUIT_TAG)){
            this.other.addFirst(other);
            gameObjectCollection.removeGameObject(other);
            if(energy <= START_ENERGY){
                energy=Math.min(energy+=BONUS,START_ENERGY);
            }
            new ScheduledTask(
                    this,
                    WAIT,
                    false,
                    this::t1);
        }


    }
    private void t1() {
        gameObjectCollection.addGameObject(other.removeLast());

    }

}
