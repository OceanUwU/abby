package oceanabby.mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.ExtraIconsPatch.ExtraIconsField;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.IconPayload;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import java.util.ArrayList;
import oceanabby.cards.AbstractAbbyCard;
import oceanabby.util.TexLoader;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.AbbyMod.makeImagePath;
import static oceanabby.util.Wiz.*;

import basemod.abstracts.CustomSavable;

public class Evo {
    protected static String[] sharedStrings = null;
    private static Texture icon = TexLoader.getTexture(makeImagePath("ui/evo.png"));

    public static void evo(AbstractCard c) {
        if (sharedStrings == null)
            sharedStrings = CardCrawlGame.languagePack.getCardStrings(makeID("Evo")).EXTENDED_DESCRIPTION;
        if (Field.evod.get(c))
            return;
        Field.evod.set(c, true);
        ExtraIconsField.extraIcons.get(c).add(new IconPayload(ExtraIcons.icon(icon))); //render in SCV
        if (c instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard)c).evod = true;
            ((AbstractAbbyCard)c).evo();
        }
        if (CardCrawlGame.isInARun() && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT)
            c.applyPowers();
        c.initializeDescription();
    }

    public static void devo(AbstractCard c) {
        if (!Field.evod.get(c))
            return;
        Field.evod.set(c, false);
        if (c instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard)c).evod = false;
            ((AbstractAbbyCard)c).devo();
        }
        if (CardCrawlGame.isInARun() && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT)
            c.applyPowers();
        c.initializeDescription();
    }

    public static AbstractGameAction action(AbstractCard c) {
        ((AbstractAbbyCard)c).evod = false;
        return actionify(() -> evo(c));
    }

    @SpirePatch(clz=AbstractCard.class, method=SpirePatch.CLASS)
    public static class Field {
        public static SpireField<Boolean> evod = new SpireField<>(() -> false);
    }

    public static CustomSavable<ArrayList<Boolean>> evoSavable = new CustomSavable<ArrayList<Boolean>>() {
        public void onLoad(ArrayList<Boolean> allEvod) {
            if (allEvod == null) return;
            int i = 0;
            System.out.println(allEvod);
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                System.out.println(i);
                System.out.println(allEvod.get(i));
                if (i > allEvod.size())
                    return;
                if (allEvod.get(i++))
                    evo(c);
            }
        }

        public ArrayList<Boolean> onSave() {
            ArrayList<Boolean> allEvod = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                allEvod.add(Field.evod.get(c));
            return allEvod;
        }
    };

    @SpirePatch(clz=AbstractCard.class, method="renderCard")
    public static class RenderIcon {
        public static void Prefix(AbstractCard __instance) {
            if (Field.evod.get(__instance))
                ExtraIconsField.extraIcons.get(__instance).add(new IconPayload(ExtraIcons.icon(icon)));
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="initializeDescription")
    public static class AddEffectToDesc {
        private static String store;

        @SpireInsertPatch(rloc=14)
        public static void Insert(AbstractCard __instance) {
            if (Field.evod.get(__instance) && !(__instance instanceof AbstractAbbyCard)) {
                store = __instance.rawDescription;
                __instance.rawDescription += sharedStrings[0];
            }
        }

        public static void Postfix(AbstractCard __instance) {
            if (Field.evod.get(__instance) && !(__instance instanceof AbstractAbbyCard)) {
                __instance.rawDescription = store;
            }
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="useCard")
    public static class DoEffect {
        @SpireInsertPatch(rloc=19)
        public static void Insert(AbstractPlayer __instance, AbstractCard c) {
            if (Field.evod.get(c) && !(c instanceof AbstractAbbyCard))
                atb(new DrawCardAction(1));
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="makeStatEquivalentCopy")
    public static class CopyEvo {
        @SpireInsertPatch(rloc=2, localvars = { "card" })
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            if (Field.evod.get(__instance))
                evo(card);
        }
    }
}
