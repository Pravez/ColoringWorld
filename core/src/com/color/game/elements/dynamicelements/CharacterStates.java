package com.color.game.elements.dynamicelements;

import com.color.game.elements.dynamicelements.states.*;

public class CharacterStates {
    private RunningState runningState;
    private SlidingState slidingState;
    private StandingState standingState;
    private WalkingState walkingState;
    private JumpingState jumpingState;

    public CharacterStates(){
        runningState = new RunningState();
        slidingState = new SlidingState();
        standingState = new StandingState();
        walkingState = new WalkingState();
        jumpingState = new JumpingState();
    }

    public RunningState getRunningState() {
        return runningState;
    }

    public void setRunningState(RunningState runningState) {
        this.runningState = runningState;
    }

    public SlidingState getSlidingState() {
        return slidingState;
    }

    public void setSlidingState(SlidingState slidingState) {
        this.slidingState = slidingState;
    }

    public StandingState getStandingState() {
        return standingState;
    }

    public void setStandingState(StandingState standingState) {
        this.standingState = standingState;
    }

    public WalkingState getWalkingState() {
        return walkingState;
    }

    public void setWalkingState(WalkingState walkingState) {
        this.walkingState = walkingState;
    }

    public JumpingState getJumpingState() {
        return jumpingState;
    }

    public void setJumpingState(JumpingState jumpingState) {
        this.jumpingState = jumpingState;
    }
}
