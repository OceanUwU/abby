package oceanabby.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import oceanabby.mechanics.Adaptations;
import oceanabby.powers.Acid;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ToxicGlands extends AbstractAdaptation {
    public final static String ID = makeID("ToxicGlands");

    public ToxicGlands() {
        super(ID);
        setMagic(50, +25);
        setSecondMagic(10);
    }

    @Override
    public void onEnd() {
        if (evod) {
            pulse(Color.LIME, Color.GREEN);
            forAllMonstersLiving(mo -> applyToEnemy(mo, new Acid(mo, secondMagic)));
        }
    }

    @SpirePatch(
        clz = AbstractMonster.class,
        method = "damage",
        paramtypez = { DamageInfo.class }
    )
    public static class ToxicGlandsPatch {
        @SpireInsertPatch( locator = Locator.class )
        public static void Insert(AbstractMonster __instance, DamageInfo info) {
            if (info.type == DamageInfo.DamageType.NORMAL && __instance.lastDamageTaken > 0)
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