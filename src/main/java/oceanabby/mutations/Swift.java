package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static oceanabby.util.Wiz.*;

public class Swift extends AbstractMutation {
    public Swift() {
        super("Swift", true, 7);
    }
    
    public boolean canMutate(AbstractCard c) {
        return c.cost > 0;
    }
    
    protected int getPower() {
        return 1;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new DrawCardAction(getPower()));
    }
}