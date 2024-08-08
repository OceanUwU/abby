package oceanabby.mutations;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static oceanabby.AbbyMod.makeID;

public abstract class AbstractMutation extends AbstractCardModifier {
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
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        List<TooltipInfo> tips = new ArrayList<>();
        if (strings[1].length() > 0)
            tips.add(new TooltipInfo(name, description));
        return tips;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard c) {
        String keyword = "*" + name + ".";
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

    protected void scab() {
        addToBot(null);
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
}