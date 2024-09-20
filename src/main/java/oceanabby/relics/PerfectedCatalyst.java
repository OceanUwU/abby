package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.vfx.campfire.CampfireSmithEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import oceanabby.characters.TheAberrant;
import oceanabby.mechanics.Evo;
import oceanabby.mechanics.Mutations;
import oceanabby.mutations.AbstractMutation;
import org.apache.commons.codec.binary.StringUtils;

public class PerfectedCatalyst extends AbstractAbbyRelic {
    public static final String ID = makeID("PerfectedCatalyst");

    public PerfectedCatalyst() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheAberrant.Enums.ABERRANT_COLOUR);
    }
    
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(MutationCatalyst.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, MutationCatalyst.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
        } else
            super.obtain();
    }
  
    public void onEquip() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (Mutations.isMutated(c)) {
                for (AbstractMutation m : Mutations.getMutations(c))
                    CardModifierManager.removeSpecificModifier(c, m, true);
                AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c.makeStatEquivalentCopy(), MathUtils.random(0.1F, 0.9F) * Settings.WIDTH, MathUtils.random(0.2F, 0.8F) * Settings.HEIGHT));
            }
    }
  
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(MutationCatalyst.ID);
    }

    @SpirePatch(clz=CampfireSmithEffect.class, method="update")
    public static class SmithPatch {
        @SpireInsertPatch(rloc=13, localvars = { "c" })
        public static void Insert(CampfireSmithEffect __instance, AbstractCard c) {
            if (AbstractDungeon.player.hasRelic(ID)) {
                AbstractDungeon.player.getRelic(ID).flash();
                Evo.evo(c);
            }
        }
    }

    @SpirePatch(clz=CampfireSmithEffect.class, method="update")
    public static class PreviewPatch {
        @SpireInsertPatch(rloc=33)
        public static void Insert(CampfireSmithEffect __instance) {
            if (AbstractDungeon.player.hasRelic(ID))
                SmithPrEvoiew.evoing = true;
        }
    }

    public static class SmithPrEvoiew {
        public static boolean evoing = false;

        @SpirePatch(clz=GridCardSelectScreen.class, method="callOnOpen")
        public static class Reset {
            public static void Postfix() {
                evoing = false;
            }
        }

        @SpirePatch(clz=GridCardSelectScreen.class, method="update")
        public static class EvoIt2 {
            @SpireInsertPatch(rloc=88)
            public static void Insert(GridCardSelectScreen __instance) {
                if (evoing)
                    Evo.evo(__instance.upgradePreviewCard);
            }
        }
    }
}
