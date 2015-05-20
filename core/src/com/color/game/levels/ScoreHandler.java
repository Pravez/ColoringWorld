package com.color.game.levels;

/**
 * Class to calculate the score of a level and handle the ranks reached
 */
public class ScoreHandler {

    public static final int BRONZE_SCORE = 1000;

    private int deaths = 0;
    private float time = 0;

    // Score parameters
    private int score;
    private int maxDeaths;
    private int maxTime;

    private int     bestScore = 0;
    private boolean newBestScore = false;

    // Ranks
    private Rank bronzeRank;
    private Rank silverRank;
    private Rank goldRank;

    /**
     * Constructor of the ScoreHandler
     * @param maxDeaths the number max of deaths the player can have
     * @param maxTime the time max the player can take to complete the level
     * @param bronzeScore the score for the bronze rank
     * @param silverScore the score for the silver rank
     * @param goldScore the score for the gold rank
     */
    public ScoreHandler(int maxDeaths, int maxTime, int bronzeScore, int silverScore, int goldScore) {
        this.maxDeaths  = maxDeaths;
        this.maxTime    = maxTime;
        this.bronzeRank = new Rank(bronzeScore);
        this.silverRank = new Rank(silverScore);
        this.goldRank   = new Rank(goldScore);
    }

    /**
     * Method called to reset the score after the level is completed
     */
    public void reset() {
        this.deaths = 0;
        this.time   = 0;

        this.bronzeRank.reached(this.bestScore);
        this.silverRank.reached(this.bestScore);
        this.goldRank.reached(this.bestScore);
    }

    /**
     * Method to calculate the score of the level in function of the time passed and the number of deaths
     */
    public void calculate() {
        this.newBestScore = false;
        this.score        = 1000;

        if (this.deaths < this.maxDeaths)
            this.score += 1000 * (this.maxDeaths - deaths);
        if (this.time < this.maxTime)
            this.score += 20 * (this.maxTime - this.time);

        this.bronzeRank.reached(this.score);
        this.silverRank.reached(this.score);
        this.goldRank.reached(this.score);

        if (this.bestScore < this.score) {
            this.bestScore = this.score;
            this.newBestScore = true;
        }
    }

    public void addTime(float delta) {
        this.time += delta;
    }

    public void addDeath() {
        this.deaths ++;
    }

    public int getTime() {
        return (int)this.time;
    }

    public int getDeaths() {
        return this.deaths;
    }

    public int getScore() {
        return this.score;
    }

    public int getBestScore() {
        return this.bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
        this.bronzeRank.reached(this.bestScore);
        this.silverRank.reached(this.bestScore);
        this.goldRank.reached(this.bestScore);
    }

    public boolean isNewBestScore() {
        return this.newBestScore;
    }

    public boolean isBronzeReached() {
        return this.bronzeRank.isReached();
    }

    public boolean isSilverReached() {
        return this.silverRank.isReached();
    }

    public boolean isGoldReached() {
        return this.goldRank.isReached();
    }
}
