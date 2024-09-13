package oceanabby.cards.cardvars;

import oceanabby.cards.AbstractAbbyCard;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static oceanabby.AbbyMod.makeID;

public class ThirdMagicNumber extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("m3");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).isThirdMagicModified;
        }
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).thirdMagic;
        }
        return -1;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard) card).isThirdMagicModified = v;
        }
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).baseThirdMagic;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).upgradedThirdMagic;
        }
        return false;
    }
}