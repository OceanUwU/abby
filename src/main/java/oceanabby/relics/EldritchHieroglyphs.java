package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import oceanabby.characters.TheAberrant;
import oceanabby.mechanics.Mutations;

public class EldritchHieroglyphs extends AbstractAbbyRelic {
    public static final String ID = makeID("EldritchHieroglyphs");
    private static int POWER = 4;

    public EldritchHieroglyphs() {
        super(ID, RelicTier.BOSS, LandingSound.MAGICAL, TheAberrant.Enums.ABERRANT_COLOUR);
    }

    public String getUpdatedDescription() {
        if (POWER == 1) return DESCRIPTIONS[0];
        return DESCRIPTIONS[1] + POWER + DESCRIPTIONS[2];
    }

    public void doThing() {
        flash();
        for (int i = 0; i < POWER; i++)
            if (adp().drawPile.size() > i)
                Mutations.mutate(adp().drawPile.getNCardFromTop(i));
    }

    @SpirePatch(clz=ShuffleAction.class, method="update")
    public static class ShuffleActionPatch {
        public static void Postfix() {
            actT(() -> {
                for (AbstractRelic r : adp().relics)
                    if (r instanceof EldritchHieroglyphs)
                        ((EldritchHieroglyphs)r).doThing();
            });
        }
    }

    @SpirePatch(clz=EmptyDeckShuffleAction.class, method="update")
    public static class EmptyDeckShuffleActionPatch {
        @SpireInsertPatch(rloc=20)
        public static void Insert() {
            actT(() -> {
                for (AbstractRelic r : adp().relics)
                    if (r instanceof EldritchHieroglyphs)
                        ((EldritchHieroglyphs)r).doThing();
            });
        }
    }
}
