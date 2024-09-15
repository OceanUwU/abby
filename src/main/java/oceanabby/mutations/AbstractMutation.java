package oceanabby.mutations;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.patches.ExtraIconsPatch.ExtraIconsField;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.IconPayload;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import oceanabby.mechanics.Mutations;
import oceanabby.util.TexLoader;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.AbbyMod.makeImagePath;
import static oceanabby.util.Wiz.actB;

public abstract class AbstractMutation extends AbstractCardModifier {
    private static Texture icon = TexLoader.getTexture(makeImagePath("ui/mutation.png"));
    private static final Map<String, String> allStrings = CardCrawlGame.languagePack.getUIString(makeID("Mutations")).TEXT_DICT;
    public String id;
    protected String[] strings;
    private String name, description;
    private String[] keywords = {};
    private boolean after;
    public int rarity;

    public AbstractMutation(String id, boolean after, int rarity) {
        this.id = makeID(id);
        this.after = after;
        this.rarity = rarity;
        strings = allStrings.get(this.id).split("\\|");
        name = strings[0];
        if (strings.length > 1)
            description = strings[1].replace("!P!", String.valueOf(getPower()));
        if (strings.length > 2)
            keywords = strings[2].split(",");
    }

    public boolean canMutate(AbstractCard c) {
        return true;
    };

    protected int getPower() {
        return -1;
    }

    @Override
    public void onRender(AbstractCard c, SpriteBatch sb) {
        ArrayList<IconPayload> icons = ExtraIconsField.extraIcons.get(c);
        if (icons.stream().anyMatch(i -> i.getTexture() == icon))
            return;
        long numMods = Mutations.countMutations(c);
        icons.add(new IconPayload(ExtraIcons.icon(icon).text(numMods > 1 ? String.valueOf(numMods) : null)));
    }

    @Override
    public void onSingleCardViewRender(AbstractCard c, SpriteBatch sb) {
        onRender(c, sb);
    }

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        List<TooltipInfo> tips = new ArrayList<>();
        if (strings.length > 1 && strings[1].length() > 0)
            tips.add(new TooltipInfo(name, description));
        return tips;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard c) {
        String keyword = "*" + name + LocalizedStrings.PERIOD;
        return (after ? "" : keyword + " ") + rawDescription + (after ? " " + keyword : "");
    }

    @Override
    public AbstractCardModifier makeCopy() {
        try {
            return getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException("Abby failed to auto-generate makeCopy for Mutation: " + id);
        }
    }

    protected void scab(AbstractCard from) {
        actB(() -> CardModifierManager.removeSpecificModifier(from, this, true));
    }

    @SpirePatch(clz=AbstractCard.class, method="initializeDescription")
    public static class AddAdditionalKeywordsPatch {
        public static void Postfix(AbstractCard __instance) {
            for (AbstractCardModifier modifier : CardModifierManager.modifiers(__instance))
                if (modifier instanceof AbstractMutation)
                    for (String keyword : ((AbstractMutation)modifier).keywords)
                        if (!__instance.keywords.contains(keyword))
                            __instance.keywords.add(keyword);
        }
    }

    @Override
    public String identifier(AbstractCard card) {
       return id;
    }
}