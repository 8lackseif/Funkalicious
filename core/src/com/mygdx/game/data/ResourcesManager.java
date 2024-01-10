package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonReader;

public class ResourcesManager {
    public AssetManager assetManager;
    private JsonReader jsonReader;
    public TextureAtlas atlas;
    //skin
    public Skin skin;
    // Fonts
    public final BitmapFont pixel10;

    // Menu
    public TextureRegion[] title;
    public TextureRegion[] titleScreenBackground;
    public TextureRegion[][] playButton;
    public TextureRegion[][] menuButtons;
    public TextureRegion[] worldSelectBackgrounds;
    public TextureRegion[][] menuExitButton;
    public TextureRegion[][] enterButton;

    public ResourcesManager() {
        assetManager = new AssetManager();
        jsonReader = new JsonReader();
        assetManager.load("textures.atlas", TextureAtlas.class);
        assetManager.load("skins/ui.atlas", TextureAtlas.class);


        assetManager.finishLoading();

        // load font
        pixel10 = new BitmapFont(Gdx.files.internal("fonts/pixel.fnt"), atlas.findRegion("pixel"), false);
    }

    public void init(){
        //load skin

        atlas = assetManager.get("textures.atlas", TextureAtlas.class);
        skin = new Skin(atlas);
        skin.add("default-font", pixel10);
        skin.load(Gdx.files.internal("skins/ui.json"));

        // menu
        title = atlas.findRegion("unlucky_title").split(18, 24)[0];
        titleScreenBackground = atlas.findRegion("title_bg").split(200, 120)[0];
        playButton = atlas.findRegion("play_button").split(80, 40);
        menuButtons = atlas.findRegion("menu_buttons").split(16, 16);
        worldSelectBackgrounds = atlas.findRegion("stage_select_bg").split(200, 120)[0];
        menuExitButton = atlas.findRegion("menu_exit_button").split(14, 14);
        enterButton = atlas.findRegion("enter_button").split(79, 28);
    }
}
