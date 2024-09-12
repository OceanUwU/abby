package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static oceanabby.util.Wiz.*;

public class Bulky extends AbstractMutation {
    public Bulky() {
        super("Bulky", true, 5);
    }

    public int getPower() {
        return 1;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        applyToSelf(new StrengthPower(adp(), getPower()));
        scab(card);
    }
}