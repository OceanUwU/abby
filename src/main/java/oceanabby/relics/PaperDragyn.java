package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.PaperCrane;
import com.megacrit.cardcrawl.relics.PaperFrog;
import oceanabby.characters.TheAberrant;

public class PaperDragyn extends AbstractAbbyRelic {
    public static final String ID = makeID("PaperDragyn");
    private static final float DROP_DELAY = 0.5f;
    private int dropped = 0;
    private float lifetime = 0f;
    private boolean dropping = false;

    public PaperDragyn() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
        tips.addAll(new PaperCrane().tips);
        tips.addAll(new PaperFrog().tips);
    }

    public void onEquip() {
        dropping = true;
    }

    @Override public void update() {
        super.update();
        if (dropping) {
            lifetime += Gdx.graphics.getDeltaTime();
            if (lifetime > DROP_DELAY) {
                lifetime -= DROP_DELAY;
                AddIt.toAdd = dropped++ == 0 ? new PaperCrane() : new PaperFrog();
                if (dropped >= 2)
                    dropping = false;
            }
        }
    }

    @SpirePatch(clz=OverlayMenu.class, method="update")
    public static class AddIt {
        private static AbstractRelic toAdd;

        public static void Postfix() {
            if (toAdd != null) {
                AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH * 0.28F, Settings.HEIGHT / 2.0F, toAdd);
                toAdd = null;
            }
        }
    }
}
