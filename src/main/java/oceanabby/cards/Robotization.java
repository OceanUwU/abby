package oceanabby.cards;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import oceanabby.mechanics.Adaptations;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Robotization extends AbstractAdaptation {
    public final static String ID = makeID("Robotization");

    public Robotization() {
        super(ID);
        setMagic(1);
        setSecondMagic(2);
    }

    @Override
    public void onThrob() {
        for (int i = 0; i < magicNumber; i++)
            att(new ChannelAction(new Lightning()));
        pulse(Color.YELLOW, Color.WHITE);
    }

    @SpirePatch(clz=AbstractOrb.class, method="applyFocus")
    public static class IncreaseLightningDamage {
        public static void Postfix(AbstractOrb __instance) {
            if (__instance instanceof Lightning)
                for (AbstractAdaptation a : Adaptations.adaptations)
                    if (a instanceof Robotization && Evo.Field.evod.get(a)) {
                        __instance.passiveAmount += a.secondMagic;
                        __instance.evokeAmount += a.secondMagic;
                    }
        }
    }
}