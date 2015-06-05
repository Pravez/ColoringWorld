package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.levels.ScoreHandler;

public class WinScreen extends BaseScreen {

    private final static int STAR_WIDTH  = 100;
    private final static int STAR_HEIGHT = 100;

    private Texture starTexture;
    private Texture emptyStarTexture;

    private Image bronzeStar;
    private Image silverStar;
    private Image goldStar;

    private Array<String> sentences = new Array<>();

    private Label score;
    private Label time;
    private Label deaths;
    private Label bestScore;
    private Label message;
    private Label space;

    private Label newBestScore;

    private Timer scoreTimer;
    private int   scoreAmount;
    private int   finalScore;
    private final static int REPETITION_COUNT = 60;

    private boolean isLastLevel = false;

    /**
     * Constructor of the BaseScreen
     *
     * @param game the {@link ColorGame}
     */
    public WinScreen(ColorGame game) {
        super(game);

        initScreen();

        Table table  = new Table();

        // Title
        table.add(createLabel("You won !", 28, TEXT_COLOR)).padBottom(30).colspan(2).row();

        // Level logs
        addLogs(table);

        // Star Table
        addStarTable(table);

        // Over informations
        addMessages(table);

        table.setFillParent(true);
        this.stage.addActor(table);

        initSentences();
    }

    private void initScreen() {
        this.starTexture      = Assets.manager.get("sprites/star.png", Texture.class);
        this.emptyStarTexture = Assets.manager.get("sprites/star-empty.png", Texture.class);

        this.bronzeStar = new Image();
        this.silverStar = new Image();
        this.goldStar   = new Image();

        this.scoreTimer = new Timer();
    }

    private void addLogs(Table table) {
        this.score = createLabel("", 18, Color.WHITE);
        this.time = createLabel("", 18, Color.WHITE);
        this.deaths = createLabel("", 18, Color.WHITE);
        this.bestScore = createLabel("", 18, Color.WHITE);

        addLog(table, "Final Score : ", 14, TEXT_COLOR, this.score);
        addLog(table, "Time passed : ", 14, TEXT_COLOR, this.time);
        addLog(table, "Number of deaths : ", 14, TEXT_COLOR, this.deaths);
        addLog(table, "Best Score : ", 12, TEXT_COLOR, this.bestScore);
    }

    private void addLog(Table table, String label, int labelSize, Color labelColor, Label value) {
        table.add(new Label(label, new Label.LabelStyle(Assets.getBasicFont(labelSize), labelColor))).right();
        table.add(value).row();
    }

    private void addStarTable(Table table) {
        float pad = 10;
        Table starTable = new Table();
        starTable.add(this.bronzeStar).size(STAR_WIDTH, STAR_HEIGHT).pad(pad);
        starTable.add(this.silverStar).size(STAR_WIDTH, STAR_HEIGHT).pad(pad);
        starTable.add(this.goldStar).size(STAR_WIDTH, STAR_HEIGHT).pad(pad);
        table.add(starTable).colspan(2).fill().padBottom(20).row();
    }

    private void addMessages(Table table) {
        this.newBestScore = createLabel("New Best Score !", 14, Color.ORANGE);
        this.message      = createLabel("", 16, TEXT_COLOR);
        this.space        = createLabel("Press SPACE to continue", 18, TEXT_COLOR);

        table.add(this.newBestScore).colspan(2).row();
        table.add(this.message).padTop(30).colspan(2).row();
        table.add(this.space).padTop(100).colspan(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (this.isLastLevel)
                this.game.setLevelSelectionScreen();
            else
                this.game.setGameScreen();
        }
    }

    @Override
    public void show() {
        this.space.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(0.4f), Actions.alpha(1, 1.2f)));
    }

    @Override
    public void hide() {
        this.space.clearActions();
        this.scoreTimer.stop();
    }

    public void initSentences() {
        this.sentences.add("You are better than I thought...");
        this.sentences.add("Will you beat it next time ?");
        this.sentences.add("Keep going on and you will finish the game !");
        this.sentences.add("Hey ! It was too easy, wasn't it ?");
        this.sentences.add("I hope the next one will be a nightmare for you !");
        this.sentences.add("Are you happy ? It won't last !");
    }

    public void handle(final ScoreHandler score) {
        this.message.setText(this.sentences.get(MathUtils.random(0, this.sentences.size - 1)));
        this.finalScore = score.getScore();
        launchScoreAnimation();
        this.score.setText("" + this.scoreAmount);
        this.time.setText(score.getTime() + " seconds");
        this.deaths.setText("" + score.getDeaths());
        this.bestScore.setText("" + score.getBestScore());

        handleStar(this.bronzeStar, score.isBronzeReached());
        handleStar(this.silverStar, score.isSilverReached());
        handleStar(this.goldStar, score.isGoldReached());

        if (score.isNewBestScore())
            this.newBestScore.addAction(Actions.sequence(Actions.alpha(0), Actions.alpha(1, 0.5f)));
        else
            this.newBestScore.addAction(Actions.alpha(0));
    }

    private void launchScoreAnimation() {
        this.scoreAmount = 0;
        this.scoreTimer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                scoreAmount = (scoreAmount + finalScore / REPETITION_COUNT) > finalScore ? finalScore : scoreAmount + finalScore / REPETITION_COUNT;
                score.setText("" + scoreAmount);
            }
        }, 0.02f, 0.02f, REPETITION_COUNT);
        this.scoreTimer.start();
    }

    private void handleStar(Image star, boolean reached) {
        if (reached)
            star.setDrawable(new SpriteDrawable(new Sprite(this.starTexture)));
        else
            star.setDrawable(new SpriteDrawable(new Sprite(this.emptyStarTexture)));
    }

    public void setNext(boolean isLastLevel) {
        this.isLastLevel = isLastLevel;
    }
}
