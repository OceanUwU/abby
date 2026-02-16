package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import oceanabby.characters.TheAberrant;

public class TheSymbiont extends AbstractAbbyRelic {
    public static final String ID = makeID("TheSymbiont");
    private static int POWER = 2;

    public TheSymbiont() {
        super(ID, RelicTier.BOSS, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
        counter = POWER;
        grayscale = true;
    }

    @Override
    public void atBattleStart() {
        grayscale = counter > 0;
    }

    public String getUpdatedDescription() {
        if (POWER == 1) return DESCRIPTIONS[0];
        return DESCRIPTIONS[1] + POWER + DESCRIPTIONS[2];
    }
  
    public void onUnequip() {
        if (counter <= 0)
            AbstractDungeon.player.energy.energyMaster--;
    }

    @SpirePatch(clz=AbstractRelic.class, method="obtain")
    @SpirePatch(clz=AbstractRelic.class, method="instantObtain", paramtypez = {})
    @SpirePatch(clz=AbstractRelic.class, method="instantObtain", paramtypez = { AbstractPlayer.class, int.class, boolean.class })
    public static class ObtainPatch {
        public static SpireReturn<Void> Prefix() {
            if (adp() != null) {
                AbstractRelic r = adp().getRelic(ID);
                if (r != null && r.counter > 0) {
                    if (--r.counter <= 0) {
                        r.counter = -1;
                        r.grayscale = false;
                        AbstractDungeon.player.energy.energyMaster++;
                    }
                    r.flash();
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }
}
