package oceanabby.mechanics;

import static oceanabby.AbbyMod.makeImagePath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.ExtraIconsPatch.ExtraIconsField;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.IconPayload;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import java.util.ArrayList;
import java.util.stream.Collectors;
import oceanabby.cards.AbstractAbbyCard;
import oceanabby.util.TexLoader;

public class Evo {
    private static Texture icon = TexLoader.getTexture(makeImagePath("ui/evo.png"));

    public static void evo(AbstractCard c) {
        if (Field.evod.get(c))
            return;
        Field.evod.set(c, false);
        ExtraIconsField.extraIcons.get(c).add(new IconPayload(ExtraIcons.icon(icon)));
        if (c instanceof AbstractAbbyCard)
            ((AbstractAbbyCard)c).evo();
    }

    public static void devo(AbstractCard c) {
        if (!Field.evod.get(c))
            return;
        Field.evod.set(c, false);
        ExtraIconsField.extraIcons.set(c, new ArrayList<IconPayload>(ExtraIconsField.extraIcons.get(c).stream().filter(i -> i.getTexture() != icon).collect(Collectors.toList())));
        if (c instanceof AbstractAbbyCard)
            ((AbstractAbbyCard)c).devo();
    }

    @SpirePatch(clz=AbstractCard.class, method=SpirePatch.CLASS)
    public static class Field {
        public static SpireField<Boolean> evod = new SpireField<>(() -> false);
    }
}
