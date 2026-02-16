package oceanabby.patches;

import oceanabby.characters.TheAberrant;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.relics.FusionHammer;

import static oceanabby.AbbyMod.makeID;

@SpirePatch(clz=FusionHammer.class, method="setDescription")
public class FusionHammerDescriptionPatch {
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getRelicStrings(makeID("FusionHammer")).DESCRIPTIONS;

    public static SpireReturn<String> Prefix(FusionHammer __instance, AbstractPlayer.PlayerClass c) {
        if (c == TheAberrant.Enums.THE_ABERRANT)
            return SpireReturn.Return(DESCRIPTIONS[1] + DESCRIPTIONS[0]);
        return SpireReturn.Continue();
    }
}