package world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.*;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;
import java.util.Timer;

public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
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

    private UserInputListener inputListener;
    private ImageRenderable[] idleArr;
    private ImageRenderable[] jumpArr;
    private ImageRenderable[] runArr;
    private AnimationRenderable idleAnimation;
    private AnimationRenderable runAnimation;
    private AnimationRenderable jumpAnimation;


    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader){
        super(pos, Vector2.of(AVATAR_X_DIM,AVATAR_Y_DIM ), new ImageRenderable(imageReader.readImage
                ("assets/assets/idle_0.png",true).getImage()));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        idleArr = new ImageRenderable[LEN];
        for (int i = 0; i < idleArr.length; i++){
            idleArr[i] = new ImageRenderable(imageReader.readImage
                    ("assets/assets/idle_" + i + ".png",true).getImage());
        }
        jumpArr = new ImageRenderable[LEN];
        for (int i = 0; i < jumpArr.length; i++){
            jumpArr[i] = new ImageRenderable(imageReader.readImage
                    ("assets/assets/jump_" + i + ".png",true).getImage());
        }
        runArr = new ImageRenderable[RUNLEN];
        for (int i = 0; i < runArr.length; i++){
            runArr[i] = new ImageRenderable(imageReader.readImage
                    ("assets/assets/run_" + i + ".png",true).getImage());
        }
        idleAnimation = new AnimationRenderable(idleArr,TIME);
        runAnimation = new AnimationRenderable(runArr,TIME);
        jumpAnimation = new AnimationRenderable(jumpArr,TIME);
        renderer().setRenderable(idleAnimation);
    }

    public float getEnergy () {
        return energy;
    }
    public boolean getJumpValue() {
        if (jumpValue){
            jumpValue = false;
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
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
            transform().setVelocityY(VELOCITY_Y);
            energy -= JUMP_ENERGY;
            renderer().setRenderable(jumpAnimation);
            jumpValue = true;
        }
        if(getVelocity().x() == 0 && getVelocity().y() == 0 && energy <= START_ENERGY){
            renderer().setRenderable(idleAnimation);
            if(energy + REST_ENERGY > START_ENERGY){
                energy = START_ENERGY;
            }
            else{
                energy += REST_ENERGY;
            }
        }
        else if((getVelocity().x() != 0 || getVelocity().y() != 0 ) && energy >= MOVE_ENERGY){
            energy -= MOVE_ENERGY;
        }
    }

}
