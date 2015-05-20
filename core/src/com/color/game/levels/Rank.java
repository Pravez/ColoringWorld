package com.color.game.levels;

public class Rank {

    private int     minScore;
    private boolean reached = false;

    public Rank(int minimum) {
        this.minScore = minimum;
    }

    public void reached(int score) {
        this.reached = this.minScore <= score;
    }

    public boolean isReached() {
        return this.reached;
    }
}
