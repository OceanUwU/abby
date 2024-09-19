package oceanabby.mechanics;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.ArrayList;
import oceanabby.cards.AbstractAdaptation;
import oceanabby.powers.AbstractAbbyPower;

import static oceanabby.util.Wiz.*;

public class Adaptations {
    public static ArrayList<AbstractAdaptation> adaptations = new ArrayList<>();

    private static void update() {
        for (int i = 0; i < adaptations.size(); i++)
            adaptations.get(i).update(i, adaptations.size());
    }

    private static void render(SpriteBatch sb) {
        for (AbstractAdaptation a : adaptations)
            if (!a.hovered)
                a.render(sb);
        for (AbstractAdaptation a : adaptations)
            if (a.hovered)
                a.render(sb);
    }

    private static void throb() {
        for (AbstractAdaptation a : adaptations)
            actB(() -> {
                if (--a.counter <= 0)
                    remove(a);
                a.fontScale *= 2f;
                a.onThrob();
            });
    }

    public static void remove(AbstractAdaptation a) {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        group.addToTop(a);
        a.onEnd();
        for (AbstractPower p : adp().powers)
            if (p instanceof AbstractAbbyPower)
                ((AbstractAbbyPower)p).onAdaptationEnd(a);
        att(new ExhaustSpecificCardAction(a, group));
        actT(() -> adaptations.remove(a));
    }

    public static void addAptation(AbstractAdaptation a) {
        a.drawScale = 0.001f;
        adaptations.add(a);
        for (AbstractPower p : adp().powers)
            if (p instanceof AbstractAbbyPower)
                ((AbstractAbbyPower)p).onAdapt(a);
        a.onThrob();
    }




    
    @SpirePatch(clz=AbstractDungeon.class, method="resetPlayer")
    public static class ResetPatch {
        public static void Prefix() {
            adaptations.clear();
        }
    }
    
    @SpirePatch(clz=AbstractPlayer.class, method="combatUpdate")
    public static class UpdatePatch {
        public static void Prefix() {
            update();
        }
    }
    
    @SpirePatch(clz=AbstractPlayer.class, method="render")
    public static class RenderPatch {
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
            render(sb);
        }
    }
    
    @SpirePatch(clz=AbstractCreature.class, method="applyStartOfTurnPostDrawPowers")
    public static class ThrobPatch {
        public static void Postfix(AbstractCreature __instance) {
            if (__instance == AbstractDungeon.player)
                throb();
        }
    }
}