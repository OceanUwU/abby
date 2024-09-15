package oceanabby.mutations;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import oceanabby.cards.AbstractAbbyCard;

public class Retain extends AbstractMutation {
    public Retain() {
        super("Retain", false, 2);
    }
    
    public boolean canMutate(AbstractCard c) {
        AbstractCard copy = c.makeStatEquivalentCopy();
        copy.upgrade();
        return !c.isEthereal && !c.selfRetain && !copy.selfRetain && !(c instanceof AbstractAbbyCard && ((AbstractAbbyCard)c).haunted >= 0);
    }
    
    @Override
    public void onInitialApplication(AbstractCard card) {
        card.selfRetain = true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.selfRetain = false;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard c) {
        return capitalize(GameDictionary.RETAIN.NAMES[0]) + LocalizedStrings.PERIOD + " NL " + rawDescription;
    }

    public static String capitalize(String str) {
        if (str.isEmpty()) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }
}