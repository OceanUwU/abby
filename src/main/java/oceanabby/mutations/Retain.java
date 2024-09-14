package oceanabby.mutations;

import com.megacrit.cardcrawl.cards.AbstractCard;
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
}