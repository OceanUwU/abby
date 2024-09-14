package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static oceanabby.util.Wiz.*;

public class Foreseeing extends AbstractMutation {
    public Foreseeing() {
        super("Foreseeing", true, 4);
    }

    @Override
    protected int getPower() {
        return 3;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new ScryAction(getPower()));
    }
}