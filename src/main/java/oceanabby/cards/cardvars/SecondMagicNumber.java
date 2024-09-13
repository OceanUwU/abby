package oceanabby.cards.cardvars;

import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanabby.cards.AbstractAbbyCard;

import static oceanabby.AbbyMod.makeID;

public class SecondMagicNumber extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("m2");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).isSecondMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).secondMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard) card).isSecondMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).baseSecondMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).upgradedSecondMagic;
        }
        return false;
    }
}