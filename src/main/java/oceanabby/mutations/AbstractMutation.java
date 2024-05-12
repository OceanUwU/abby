package oceanabby.mutations;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
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
    private String name;
    private boolean after;
    public int rarity;

    public AbstractMutation(String id, boolean after, int rarity) {
        this.id = makeID(id);
        this.after = after;
        this.rarity = rarity;
        strings = allStrings.get(this.id).split("\\|");
        name = strings[0];
    }

    public abstract boolean canMutate(AbstractCard c);

    @Override
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        List<TooltipInfo> tips = new ArrayList<>();
        if (strings[1].length() > 0)
            tips.add(new TooltipInfo(name, strings[1]));
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
}