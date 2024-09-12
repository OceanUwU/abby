package oceanabby.cards.cardvars;

import oceanabby.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static oceanabby.AbbyMod.makeID;

public class Haunted extends AbstractEasyDynamicVariable {
    @Override
    public String key() {
        return makeID("h");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).isHauntedModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractEasyCard) {
            ((AbstractEasyCard) card).isHauntedModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).haunted;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).baseHaunted;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractEasyCard) {
            return ((AbstractEasyCard) card).upgradedHaunted;
        }
        return false;
    }
}