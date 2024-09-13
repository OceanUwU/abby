package oceanabby.cards.cardvars;

import com.megacrit.cardcrawl.cards.AbstractCard;
import oceanabby.cards.AbstractAbbyCard;

import static oceanabby.AbbyMod.makeID;

public class SecondDamage extends AbstractEasyDynamicVariable {

    @Override
    public String key() {
        return makeID("sd");
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).isSecondDamageModified;
        }
        return false;
    }

    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard) card).isSecondDamageModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).secondDamage;
        }
        return -1;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).baseSecondDamage;
        }
        return -1;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractAbbyCard) {
            return ((AbstractAbbyCard) card).upgradedSecondDamage;
        }
        return false;
    }
}