package com.color.game.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.color.game.ColorGame;

public class CreditsScreen extends BaseScreen {

    /**
     * Constructor of the BaseScreen
     *
     * @param game the {@link ColorGame}
     */
    public CreditsScreen(ColorGame game) {
        super(game);

        Table table = new Table();

        table.add(createLabel("Credits", TITLE_SIZE, TITLE_COLOR)).padBottom(50).row();

        table.add(createLabel("Made by ;", TEXT_SIZE, TEXT_COLOR)).row();
        table.add(createLabel("Paul BRETON", SMALL_TEXT_SIZE, TEXT_COLOR)).row();
        table.add(createLabel("Alexis DUFRENNE", SMALL_TEXT_SIZE, TEXT_COLOR)).row();
        table.add(createLabel("Supervised by ;", TEXT_SIZE, TEXT_COLOR)).row();
        table.add(createLabel("Claus ARANHA", SMALL_TEXT_SIZE, TEXT_COLOR)).row();
        table.add(createLabel("For the University of Tsukuba", SMALL_TEXT_SIZE, TEXT_COLOR)).row();

        addMenuButton();

        table.setFillParent(true);
        stage.addActor(table);
    }
}
