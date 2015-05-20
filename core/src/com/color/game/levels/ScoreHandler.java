package com.color.game.levels;

/**
 * Class to calculate the score of a level and handle the ranks reached
 */
public class ScoreHandler {

    public static final int BRONZE_SCORE = 1000;

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

    public void calculate(int deaths, int time) {
        this.newBestScore = false;
        this.score        = 1000;

        if (deaths < this.maxDeaths)
            this.score += 1000 * (this.maxDeaths - deaths);
        if (time < this.maxTime)
            this.score += 20 * (this.maxTime - time);

        this.bronzeRank.reached(this.score);
        this.silverRank.reached(this.score);
        this.goldRank.reached(this.score);

        if (this.bestScore < this.score) {
            this.bestScore = this.score;
            this.newBestScore = true;
        }
    }

    public int getScore() {
        return this.score;
    }

    public int getBestScore() {
        return this.bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
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
