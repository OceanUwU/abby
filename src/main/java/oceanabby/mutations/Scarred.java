package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static oceanabby.util.Wiz.*;

public class Scarred extends AbstractMutation {
    public Scarred() {
        super("Scarred", true, 4);
    }
    
    protected int getPower() {
        return 3;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        applyToSelf(new PlatedArmorPower(adp(), getPower()));
        scab(card);
    }
}