package com.color.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Array;
import com.color.game.ColorGame;
import com.color.game.assets.Assets;
import com.color.game.levels.ScoreHandler;

public class WinScreen extends BaseScreen {

    private final static int STAR_WIDTH = 100;
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
    private Label message;
    private Label space;

    /**
     * Constructor of the BaseScreen
     *
     * @param game the {@link ColorGame}
     */
    public WinScreen(ColorGame game) {
        super(game);

        this.starTexture      = Assets.manager.get("sprites/star.png", Texture.class);
        this.emptyStarTexture = Assets.manager.get("sprites/star-empty.png", Texture.class);

        this.bronzeStar = new Image();
        this.silverStar = new Image();
        this.goldStar   = new Image();

        this.texture = new Texture(Gdx.files.internal("backgrounds/background0.png"));
        this.stage   = new Stage();
        Table table  = new Table();
        table.setBackground(new SpriteDrawable(new Sprite(this.texture)));

        Color blueColor = new Color(142f/255, 188f/255, 224f/255, 1);

        Label title       = new Label("You won !", new Label.LabelStyle(Assets.getBasicFont(28), blueColor));
        Label scoreLabel  = new Label("Final Score : ", new Label.LabelStyle(Assets.getBasicFont(14), blueColor));
        this.score        = new Label("", new Label.LabelStyle(Assets.getBasicFont(18), Color.WHITE));
        Label timeLabel   = new Label("Time passed : ", new Label.LabelStyle(Assets.getBasicFont(14), blueColor));
        this.time         = new Label("", new Label.LabelStyle(Assets.getBasicFont(18), Color.WHITE));
        Label deathsLabel = new Label("Number of deaths : ", new Label.LabelStyle(Assets.getBasicFont(14), blueColor));
        this.deaths       = new Label("", new Label.LabelStyle(Assets.getBasicFont(18), Color.WHITE));
        this.message      = new Label("", new Label.LabelStyle(Assets.getBasicFont(16), blueColor));
        this.space        = new Label("Press SPACE to continue", new Label.LabelStyle(Assets.getBasicFont(18), blueColor));

        // Infos
        table.add(title).padBottom(30).colspan(2).row();
        table.add(scoreLabel).right();
        table.add(this.score).row();
        table.add(timeLabel).right();
        table.add(this.time).row();
        table.add(deathsLabel).right();
        table.add(this.deaths).row();

        // Star Table
        Table starTable = new Table();
        starTable.add(this.bronzeStar).size(STAR_WIDTH, STAR_HEIGHT).pad(10);
        starTable.add(this.silverStar).size(STAR_WIDTH, STAR_HEIGHT).pad(10);
        starTable.add(this.goldStar).size(STAR_WIDTH, STAR_HEIGHT).pad(10);
        table.add(starTable).colspan(2).fill().padBottom(20).row();

        table.add(this.message).padTop(30).colspan(2).row();
        table.add(this.space).padTop(200).colspan(2);

        table.setFillParent(true);
        stage.addActor(table);

        initSentences();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            this.game.setLevelSelectionScreen();
        }
    }

    @Override
    public void show() {
        this.space.addAction(Actions.sequence(Actions.alpha(0), Actions.delay(0.4f), Actions.alpha(1, 1.2f)));
    }

    @Override
    public void hide() {
        this.space.clearActions();
    }

    public void initSentences() {
        this.sentences.add("You are better than I thought...");
        this.sentences.add("Will you beat it next time ?");
        this.sentences.add("Keep going on and you will finish the game !");
        this.sentences.add("Hey ! It was too easy, wasn't it ?");
        this.sentences.add("I hope the next one will be a nightmare for you !");
        this.sentences.add("Are you happy ? It won't last !");
    }

    public void handle(ScoreHandler score, int time, int deaths) {
        this.message.setText(this.sentences.get(MathUtils.random(0, this.sentences.size - 1)));
        this.score.setText("" + score.getScore());
        this.time.setText(time + " seconds");
        this.deaths.setText("" + deaths);

        handleStar(this.bronzeStar, score.isBronzeReached());
        handleStar(this.silverStar, score.isSilverReached());
        handleStar(this.goldStar, score.isGoldReached());
    }

    private void handleStar(Image star, boolean reached) {
        if (reached)
            star.setDrawable(new SpriteDrawable(new Sprite(this.starTexture)));
        else
            star.setDrawable(new SpriteDrawable(new Sprite(this.emptyStarTexture)));
    }
}
