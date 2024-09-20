package oceanabby.cards;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import oceanabby.mechanics.Adaptations;
import oceanabby.powers.Acid;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class SurvivalOfTheFittest extends AbstractAbbyCard {
    public final static String ID = makeID("SurvivalOfTheFittest");

    public SurvivalOfTheFittest() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(34, +16);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
        if (evod)
            applyToSelf(new LambdaPower(ID + "EVO", exDesc, exDesc[3], PowerType.BUFF, false, p, secondMagic) {
                public void updateDescription() {
                    description = strings[4] + amount + strings[5];
                }
            });
    }

    @SpirePatch(
        clz = AbstractMonster.class,
        method = "damage",
        paramtypez = { DamageInfo.class }
    )
    public static class SurvivalPatch {
        @SpireInsertPatch( locator = Locator.class )
        public static void Insert(AbstractMonster __instance, DamageInfo info) {
            if (__instance.lastDamageTaken > 0) {
                if (adp().hasPower(ID)) {
                    adp().getPower(ID).flashWithoutSound();
                    applyToSelfTop(new VigorPower(adp(), __instance.lastDamageTaken * 100 / pwrAmt(adp(), ID)));
                }
                if (adp().hasPower(ID + "EVO")) {
                    adp().getPower(ID + "EVO").flashWithoutSound();
                    att(new GainBlockAction(adp(), pwrAmt(__instance, ID + "Evo")));
                }
            }
                for (AbstractAdaptation a : Adaptations.adaptations)
                    if (a instanceof ToxicGlands)
                        applyToEnemy(__instance, new Acid(__instance, __instance.lastDamageTaken * 100 / a.magicNumber));
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "lastDamageTaken");
                return new int[]{LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher)[0]+1};
            }
        }
    }
}