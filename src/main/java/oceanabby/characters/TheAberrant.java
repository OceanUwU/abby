package oceanabby.characters;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpineAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import oceanabby.cards.Defend;
import oceanabby.cards.Strike;
import oceanabby.relics.MutationCatalyst;
import oceanabby.util.TexLoader;
import java.util.ArrayList;

import static oceanabby.AbbyMod.*;
import static oceanabby.characters.TheAberrant.Enums.ABERRANT_COLOUR;

public class TheAberrant extends CustomPlayer {

    static final String ID = makeID("ModdedCharacter");
    public static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;
    private static final float ORB_IMG_SCALE = 1.15F * Settings.scale;
    public static final float SIZE_SCALE = 0.8f;
    public static final Float ANIMATION_SPEED = 1.0F;

    private static class AbbyEnergyBubble {
        public static final Texture IMG = TexLoader.getTexture(makeCharacterPath("mainChar/orb/bubble.png"));
        public static final Texture IMG_DARK = TexLoader.getTexture(makeCharacterPath("mainChar/orb/bubbled.png"));
        public static final int SIZE = IMG.getWidth();
        public static final float HALFSIZE = (float)SIZE / 2f;

        private float time = MathUtils.random(0f, 100f);
        private float startX = MathUtils.random(-32f, 160f);
        private float xSway = MathUtils.random(15f, 35f);
        private float swayTime = MathUtils.random(0.8f, 1.8f) * (float)Math.PI;
        private float ySpeed = MathUtils.random(30f, 60f);
        public float scale = MathUtils.random(0.5f, 1f);
        public float x, y;

        public void update(float delta) {
            time += delta;
            x = startX + (float)Math.sin(time * swayTime) * xSway;
            y = (time * ySpeed) % 196f - 32f;
        }
    }

    public TheAberrant(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, makeCharacterPath("mainChar/orb/vfx.png"), new float[] {40f, 60f, 80f, 40f, 0f}) {
            private FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
            private Texture mask = TexLoader.getTexture(makeCharacterPath("mainChar/orb/mask.png"));
            private ArrayList<AbbyEnergyBubble> bubbles = new ArrayList<>();

            @Override
            public void updateOrb(int energyCount) {
                super.updateOrb(energyCount);
                float delta = Gdx.graphics.getDeltaTime() * (energyCount == 0 ? 0.25f : 1f);
                if (bubbles.size() == 0)
                    for (int i = 0; i < 16; i++)
                        bubbles.add(new AbbyEnergyBubble());
                for (AbbyEnergyBubble bubble : bubbles)
                    bubble.update(delta);
            }

            @Override
            public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
                sb.end();
                fbo.begin();
                Gdx.gl.glClearColor(0, 0, 0, 0);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glColorMask(true, true, true, true);
                sb.begin();
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                if (enabled)
                    for(int i = 0; i < energyLayers.length; ++i)
                        sb.draw(energyLayers[i], current_x - 64f, current_y - 64f, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
                else
                    for(int i = 0; i < noEnergyLayers.length; ++i)
                        sb.draw(noEnergyLayers[i], current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
                for (AbbyEnergyBubble b : bubbles)
                    sb.draw(enabled ? AbbyEnergyBubble.IMG : AbbyEnergyBubble.IMG_DARK, current_x - 64f + b.x, current_y - 64f + b.y, AbbyEnergyBubble.HALFSIZE, AbbyEnergyBubble.HALFSIZE, AbbyEnergyBubble.SIZE, AbbyEnergyBubble.SIZE, ORB_IMG_SCALE * b.scale, ORB_IMG_SCALE * b.scale, 0, 0, 0, AbbyEnergyBubble.SIZE, AbbyEnergyBubble.SIZE, false, false);
                sb.setBlendFunction(0, GL20.GL_SRC_ALPHA);
                sb.setColor(Color.WHITE);
                sb.draw(mask, current_x - 256, current_y - 256, 256, 256, 512, 512, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 512, 512, false, false);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb.end();
                fbo.end();
                sb.begin();
                TextureRegion drawTex = new TextureRegion(fbo.getColorBufferTexture());
                drawTex.flip(false, true);
                sb.draw(drawTex, -Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
                sb.draw(baseLayer, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
            }
        }, new SpineAnimation(
                makeCharacterPath("mainChar/abby.atlas"), makeCharacterPath("mainChar/abby.json"), 1f / SIZE_SCALE));
        initializeClass(makeCharacterPath("mainChar/abby.png"),
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 0f, -30f, 410.0F, 290.0F, new EnergyManager(3));


        loadAnimation(makeCharacterPath("mainChar/abby.atlas"), makeCharacterPath("mainChar/abby.json"), 1f / SIZE_SCALE);
        AnimationState.TrackEntry e = state.setAnimation(0, "idle", true);
        stateData.setMix("hit", "idle", 0.5F);
        e.setTimeScale(ANIMATION_SPEED);
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    public void damage(DamageInfo info) {
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output - currentBlock > 0) {
            AnimationState.TrackEntry e = state.setAnimation(0, "hit", false);
            AnimationState.TrackEntry e2 = state.addAnimation(0, "idle", true, 0.0F);
            e.setTimeScale(ANIMATION_SPEED);
            e2.setTimeScale(ANIMATION_SPEED);
        }

        super.damage(info);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                75, 75, 0, 99, 5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            retVal.add(Strike.ID);
        }
        for (int i = 0; i < 4; i++) {
            retVal.add(Defend.ID);
        }
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(MutationCatalyst.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("MAW_DEATH", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    private static final String[] orbTextures = {
            makeCharacterPath("mainChar/orb/layer1.png"),
            makeCharacterPath("mainChar/orb/layer2.png"),
            makeCharacterPath("mainChar/orb/layer3.png"),
            makeCharacterPath("mainChar/orb/layer4.png"),
            makeCharacterPath("mainChar/orb/layer5.png"),
            makeCharacterPath("mainChar/orb/layer6.png"),
            makeCharacterPath("mainChar/orb/layer1d.png"),
            makeCharacterPath("mainChar/orb/layer2d.png"),
            makeCharacterPath("mainChar/orb/layer3d.png"),
            makeCharacterPath("mainChar/orb/layer4d.png"),
            makeCharacterPath("mainChar/orb/layer5d.png"),
    };

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "MAW_DEATH";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return ABERRANT_COLOUR;
    }

    @Override
    public Color getCardTrailColor() {
        return characterColor.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontPurple;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        System.out.println("YOU NEED TO SET getStartCardForEvent() in your " + getClass().getSimpleName() + " file!");
        return null;
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheAberrant(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return characterColor.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return characterColor.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    @Override
    public String getSensoryStoneText() {
        return TEXT[3];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_ABERRANT;
        @SpireEnum(name = "OCEAN_ABERRANT_COLOUR")
        public static AbstractCard.CardColor ABERRANT_COLOUR;
        @SpireEnum(name = "OCEAN_ABERRANT_COLOUR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }
}
