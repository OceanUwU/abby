package oceanabby.cards.cardvars;

import oceanabby.cards.AbstractAbbyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static oceanabby.AbbyMod.makeID;

public class Haunted extends AbstractEasyDynamicVariable {
    @Override
    public String key() {
        return makeID("h");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).isHauntedModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard) card).isHauntedModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).haunted;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).baseHaunted;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).upgradedHaunted;
        }
        return false;
    }
}