package com.color.game.screens;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.gui.AnimatedCube;
import com.color.game.levels.ScoreHandler;

public class WinScreen extends BaseScreen {

    private final static int STAR_WIDTH  = 100;
    private final static int STAR_HEIGHT = 100;

    private RayHandler rayHandler;

    private Image bronzeStar;
    private Image silverStar;
    private Image goldStar;

    private PointLight bronzeLight;
    private PointLight silverLight;
    private PointLight goldLight;

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

        this.rayHandler = new RayHandler(new World(new Vector2(0, 0), true));
        this.rayHandler.setAmbientLight(Color.BLACK);
        this.rayHandler.setCombinedMatrix(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()).combined);

        this.bronzeLight = new PointLight(this.rayHandler, 15, new Color(187/255f, 172/255f, 157/255f, 1), 200, 0, 0);
        this.silverLight = new PointLight(this.rayHandler, 15, new Color(52/255f, 172/255f, 157/255f, 1), 200, 0, 0);
        this.goldLight   = new PointLight(this.rayHandler, 15, new Color(187/255f, 90/255f, 90/255f, 1), 200, 0, 0);

        initScreen();

        Table table  = new Table();

        // Title
        table.add(createLabel("You won !", TITLE_SIZE, TITLE_COLOR)).padBottom(30).colspan(2).row();

        // Level logs
        addLogs(table);

        // Star Table
        addStarTable();

        // Over informations
        addMessages(table);

        table.setFillParent(true);
        this.stage.addActor(table);

        initSentences();
    }

    private void initScreen() {
        this.bronzeStar = new Image();
        this.silverStar = new Image();
        this.goldStar   = new Image();

        this.scoreTimer = new Timer();
    }

    private void addLogs(Table table) {
        this.score = createLabel("", SMALL_TEXT_SIZE, Color.WHITE);
        this.time = createLabel("", SMALL_TEXT_SIZE, Color.WHITE);
        this.deaths = createLabel("", SMALL_TEXT_SIZE, Color.WHITE);
        this.bestScore = createLabel("", SMALL_TEXT_SIZE, Color.WHITE);

        addLog(table, "Final Score ; ", SMALL_TEXT_SIZE, TEXT_COLOR, this.score);
        addLog(table, "Time passed ; ", SMALL_TEXT_SIZE, TEXT_COLOR, this.time);
        addLog(table, "Number of deaths ; ", SMALL_TEXT_SIZE, TEXT_COLOR, this.deaths);
        addLog(table, "Best Score ; ", SMALL_TEXT_SIZE, TEXT_COLOR, this.bestScore);
    }

    private void addLog(Table table, String label, int labelSize, Color labelColor, Label value) {
        table.add(BaseScreen.createLabel(label, labelSize, labelColor)).right();
        table.add(value).row();
    }

    private void addStarTable() {
        float width = STAR_WIDTH*3 + 20;
        float x = Gdx.graphics.getWidth()/2 - width/2;

        Vector2 center = new Vector2(Gdx.graphics.getWidth()/2 - STAR_WIDTH/2, Gdx.graphics.getHeight()/2 - STAR_HEIGHT/2);

        addStar(this.bronzeStar, x, this.bronzeLight, center);
        addStar(this.silverStar, x + STAR_WIDTH + 10, this.silverLight, center);
        addStar(this.goldStar, x + (STAR_WIDTH + 10)*2, this.goldLight, center);
    }

    private void addStar(Image star, float x, PointLight light, Vector2 center) {
        star.setDrawable(new SpriteDrawable(new Sprite(Assets.getTexture(AnimatedCube.class))));
        star.setSize(STAR_WIDTH, STAR_HEIGHT);
        star.setPosition(x, 300);
        this.stage.addActor(star);

        light.setActive(false);
        light.setPosition(star.getX() - center.x, star.getY() - center.y);
    }

    private void addMessages(Table table) {
        this.newBestScore = createLabel("New Best Score !", SMALL_TEXT_SIZE, Color.ORANGE);
        this.message      = createLabel("", SMALL_TEXT_SIZE, TEXT_COLOR);
        this.space        = createLabel("Press SPACE to continue", TEXT_SIZE, TEXT_COLOR);

        table.add(this.newBestScore).padTop(120).colspan(2).row();
        table.add(this.message).padTop(20).colspan(2).row();
        table.add(this.space).padTop(60).colspan(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.rayHandler.updateAndRender();

        this.stage.act();
        this.stage.draw();

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

        handleStar(this.bronzeStar, new Color(187/255f, 172/255f, 157/255f, 1), this.bronzeLight, score.isBronzeReached());
        handleStar(this.silverStar, new Color(52/255f, 172/255f, 157/255f, 1), this.silverLight, score.isSilverReached());
        handleStar(this.goldStar, new Color(187/255f, 90/255f, 90/255f, 1), this.goldLight, score.isGoldReached());

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

    private void handleStar(Image star, Color color, PointLight light, boolean reached) {
        star.setColor(color.r, color.g, color.b, reached ? 0.8f : 0.2f);
        light.setActive(reached);
    }

    public void setNext(boolean isLastLevel) {
        this.isLastLevel = isLastLevel;
    }
}
