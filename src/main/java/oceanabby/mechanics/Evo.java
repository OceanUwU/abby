package oceanabby.mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.ExtraIconsPatch.ExtraIconsField;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.IconPayload;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanabby.cards.AbstractAbbyCard;
import oceanabby.util.TexLoader;

import static oceanabby.AbbyMod.makeImagePath;
import static oceanabby.util.Wiz.*;

public class Evo {
    private static Texture icon = TexLoader.getTexture(makeImagePath("ui/evo.png"));

    public static void evo(AbstractCard c) {
        if (Field.evod.get(c))
            return;
        Field.evod.set(c, false);
        ExtraIconsField.extraIcons.get(c).add(new IconPayload(ExtraIcons.icon(icon))); //render in SCV
        if (c instanceof AbstractAbbyCard)
            ((AbstractAbbyCard)c).evo();
    }

    public static void devo(AbstractCard c) {
        if (!Field.evod.get(c))
            return;
        Field.evod.set(c, false);
        if (c instanceof AbstractAbbyCard)
            ((AbstractAbbyCard)c).devo();
    }

    public static AbstractGameAction action(AbstractCard c) {
        return actionify(() -> evo(c));
    }

    @SpirePatch(clz=AbstractCard.class, method=SpirePatch.CLASS)
    public static class Field {
        public static SpireField<Boolean> evod = new SpireField<>(() -> false);
    }

    @SpirePatch(clz=AbstractCard.class, method="renderCard")
    public static class RenderIcon {
        public static void Prefix(AbstractCard __instance) {
            if (Field.evod.get(__instance))
                ExtraIconsField.extraIcons.get(__instance).add(new IconPayload(ExtraIcons.icon(icon)));
        }
    }
}
