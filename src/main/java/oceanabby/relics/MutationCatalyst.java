package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import oceanabby.characters.TheAberrant;
import oceanabby.mechanics.Mutations;

public class MutationCatalyst extends AbstractAbbyRelic {
    public static final String ID = makeID("MutationCatalyst");
    private static int POWER = 2;

    public MutationCatalyst() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK, TheAberrant.Enums.ABERRANT_COLOUR);
        counter = POWER;
    }

    public String getUpdatedDescription() {
        if (POWER == 1) return DESCRIPTIONS[2];
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }

    @Override
    public void onMasterDeckChange() {
        if (counter > 0) {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                if (Mutations.isMutated(c)) {
                    counter--;
                    flash();
                    if (counter <= 0)
                        counter = -2;
                }
        }
    }

    @SpirePatch(clz=RewardItem.class, method=SpirePatch.CONSTRUCTOR, paramtypez={})
    @SpirePatch(clz=RewardItem.class, method=SpirePatch.CONSTRUCTOR, paramtypez={AbstractCard.CardColor.class})
    public static class MutateEm {
        public static void Postfix(RewardItem __instance) {
            if (__instance.cards.size() > 0) {
                for (AbstractRelic r : AbstractDungeon.player.relics)
                    if (r.relicId.equals(ID) && r.counter > 0) {
                        if (--r.counter <= 0)
                            r.counter = -1;
                        r.flash();
                        for (AbstractCard c : __instance.cards)
                            Mutations.mutate(c);
                    }
            }
        }
    }

    @SpirePatch(clz=RewardItem.class, method="claimReward")
    public static class FlashIt {
        @SpireInsertPatch(rloc=54)
        public static void Insert(RewardItem __instance) {
            if (__instance.cards != null && __instance.cards.size() > 0 && Mutations.isMutated(__instance.cards.get(0)))
                if (adp().hasRelic(ID))
                    adp().getRelic(ID).flash();                
        }
    }
}
