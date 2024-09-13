package oceanabby.cards.cardvars;

import oceanabby.cards.AbstractAbbyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static oceanabby.AbbyMod.makeID;

public class SecondBlock extends AbstractEasyDynamicVariable {
    @Override
    public String key() {
        return makeID("sb");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).isSecondBlockModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard) card).isSecondBlockModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).secondBlock;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).baseSecondBlock;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).upgradedSecondBlock;
        }
        return false;
    }
}