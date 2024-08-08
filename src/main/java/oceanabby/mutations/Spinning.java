package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static oceanabby.util.Wiz.*;

public class Spinning extends AbstractMutation {
    public Spinning() {
        super("Spinning", true, 10);
    }

    public int getPower() {
        return 5;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new GainBlockAction(adp(), getPower()));
    }
}