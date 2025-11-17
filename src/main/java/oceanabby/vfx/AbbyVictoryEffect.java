package oceanabby.vfx;

import static oceanabby.AbbyMod.makeImagePath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.util.ArrayList;
import oceanabby.util.TexLoader;

public class AbbyVictoryEffect extends AbstractGameEffect {
    private static final float DROP_INTERVAL_MIN = 0.02f, DROP_INTERVAL_MAX = 0.16f;

    private ArrayList<AcidDrop> drops = new ArrayList<>();
    private float dropTimer;
    private float timer;

    public void update() {
        float delta = Gdx.graphics.getDeltaTime();

        timer += delta;
        dropTimer -= delta;
        if (dropTimer <= 0f) {
            dropTimer += MathUtils.random(DROP_INTERVAL_MIN, DROP_INTERVAL_MAX);
            drops.add(new AcidDrop());
        }
        for (AcidDrop drop : drops)
            drop.update(delta);
        drops.removeIf(drop -> drop.y < -200f);
        System.out.println(drops.size());
    }

    public void render(SpriteBatch sb) {
        sb.setColor(new Color(1f, 1f, 1f, Math.min(timer, 1f)));
        sb.setBlendFunction(770, 771);
        for (AcidDrop drop : drops)
            drop.render(sb);
        sb.setBlendFunction(770, 1);
    }

    public void dispose() {};

    private static class AcidDrop {
        private static final int W = 256;
        private static final int H = W;
        private static final TextureAtlas.AtlasRegion IMG = new TextureAtlas.AtlasRegion(TexLoader.getTexture(makeImagePath("vfx/droplet.png")), 0, 0, W, H);
        private static final float gravity = 100f;

        private float x = MathUtils.random(-60f, Settings.WIDTH + 60f);
        private float y = Settings.HEIGHT + H * 0.5f * Settings.scale;
        private float scale = MathUtils.random(0.25f, 0.8f) * Settings.scale;
        private float dY = 0;

        public void update(float delta) {
            dY += gravity * delta * 0.5f;
            y -= dY * delta * Settings.scale;
            dY += gravity * delta * 0.5f;
        }

        public void render(SpriteBatch sb) {
            sb.draw(IMG, x - W/2, y - H/2, W/2, H/2, W, H, scale, scale, 0);
        }
    }
}
