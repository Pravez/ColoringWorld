package com.color.game.elements.dynamicelements.enemies;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.color.game.command.elements.EndJumpCommand;
import com.color.game.command.elements.StartJumpCommand;
import com.color.game.elements.dynamicelements.states.LandedState;
import com.color.game.elements.userData.UserData;
import com.color.game.levels.Level;

@Deprecated
public class JumpingEnemy extends MovingEnemy{

    private static final Vector2 JUMPING_VELOCITY = new Vector2(0,500f);

    private final Array<Body> bodies;

    public JumpingEnemy(Vector2 position, int width, int height, Level level, boolean canFall) {
        super(position, width, height, level, canFall);

        Array<Body> tempbodies = new Array<>();
        bodies = new Array<>();
        level.getWorld().getBodies(tempbodies);

        for(Body b : tempbodies){
            if(!UserData.isDynamicBody(b)){
                bodies.add(b);
            }
        }
    }

    @Override
    public void act(float delta){
        super.act(delta);
        preventJump();
    }

    @Override
    public void startJump(){
        this.physicComponent.startJump();
    }

    @Override
    public Vector2 getJumpVelocity() {
        return JUMPING_VELOCITY;
    }

    private void preventJump(){
        float dy = this.physicComponent.getBody().getPosition().y;
        float dx = this.physicComponent.getBody().getPosition().x;

        Rectangle r = new Rectangle(dx, dy+this.physicComponent.getUserData().getHeight()/2, 1, this.physicComponent.getUserData().getHeight());

        if(this.getAloftState() instanceof LandedState) {
            for (Body b : bodies) {
                switch (this.current_direction){
                    case -1 :
                        if(b.getPosition().x < dx)
                            if (((b.getPosition().y + ((UserData) b.getUserData()).getHeight() / 2)) >= r.y - r.height - 0.1) {
                                if ((b.getPosition().y + ((UserData) b.getUserData()).getHeight() / 2) <= r.y + r.height + 3) {
                                    if ((Math.abs(dx - b.getPosition().x)) < 5) {
                                        this.addCommand(new StartJumpCommand());
                                        this.addCommand(new EndJumpCommand());
                                    }
                                }
                            }
                        break;
                    case 1 :
                        if(b.getPosition().x > dx)
                            if (((b.getPosition().y + ((UserData) b.getUserData()).getHeight() / 2)) >= r.y - r.height - 0.1) {
                                if ((b.getPosition().y + ((UserData) b.getUserData()).getHeight() / 2) <= r.y + r.height + 3) {
                                    if ((Math.abs(dx - b.getPosition().x)) < 5) {
                                        this.addCommand(new StartJumpCommand());
                                        this.addCommand(new EndJumpCommand());
                                    }
                                }
                            }
                        break;
                }

            }
        }

    }
}
