package com.mygdx.game.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.lib.GifDecoder;

public class ResourceManager {
    //textures

    public AssetManager assetManager;
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

    public Animation<TextureRegion> loading;

    public Texture[] tiles;

    public Texture map;

    public Sound tapsound;

    public Texture hit;

    public ResourceManager() {
        assetManager = new AssetManager();
        assetManager.load("textures.atlas", TextureAtlas.class);

        assetManager.finishLoading();
        //atlas
        atlas = assetManager.get("textures.atlas", TextureAtlas.class);
        // load font
        pixel10 = new BitmapFont(Gdx.files.internal("fonts/pixel.fnt"), atlas.findRegion("pixel"), false);

        //load skin
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

        //gif
        loading = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("resources/loading.gif").read());

        //tiles
        tiles = new Texture[3];
        tiles[0] = null;
        tiles[1] = new Texture(Gdx.files.internal("resources/botonNormal.png"));
        tiles[2] = new Texture(Gdx.files.internal("resources/botonMantenido.png"));

        //map
        map = new Texture(Gdx.files.internal("resources/Mapa.png"));

        //on touch media
        tapsound = Gdx.audio.newSound(Gdx.files.internal("resources/touch.mp3"));
        hit = new Texture(Gdx.files.internal("resources/Hit.png"));
    }
}
