package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.unique.MadnessAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static oceanabby.util.Wiz.*;

public class Relaxed extends AbstractMutation {
    public Relaxed() {
        super("Relaxed", true, 4);
    }
    
    public boolean canMutate(AbstractCard c) {
        return c.exhaust;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new MadnessAction());
    }
}