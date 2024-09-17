package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.applyToEnemy;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import oceanabby.characters.TheAberrant;
import oceanabby.powers.Acid;

public class SneckoSpine extends AbstractAbbyRelic {
    public static final String ID = makeID("SneckoSpine");
    private boolean shouldDisable;
    public boolean enabled;

    public SneckoSpine() {
        super(ID, RelicTier.COMMON, LandingSound.HEAVY, TheAberrant.Enums.ABERRANT_COLOUR);
    }
  
    public void atPreBattle() {
        flash();
        beginPulse();
        pulse = true;
        shouldDisable = false;
        enabled = true;
    }
  
    public void onVictory() {
        grayscale = false;
        pulse = false;
        shouldDisable = false;
        enabled = false;
    }
  
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (shouldDisable)
            enabled = false;
        if (card.type == AbstractCard.CardType.ATTACK && !grayscale) {
            flash();
            shouldDisable = true;
            pulse = false;
            grayscale = true;
        } 
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "damage",
            paramtypez = { DamageInfo.class }
    )
    public static class MedievalHelmetPatch {
        @SpireInsertPatch( locator = Locator.class )
        public static void Insert(AbstractMonster __instance, DamageInfo info) {
            if (AbstractDungeon.player.hasRelic(ID) && info.type == DamageInfo.DamageType.NORMAL && ((SneckoSpine)AbstractDungeon.player.getRelic(ID)).enabled && __instance.lastDamageTaken > 0)
                applyToEnemy(__instance, new Acid(__instance, __instance.lastDamageTaken));

        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "lastDamageTaken");
                return new int[]{LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher)[0]+1};
            }
        }
    }
}
