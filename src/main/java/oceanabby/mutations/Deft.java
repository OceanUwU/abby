package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static oceanabby.util.Wiz.*;

public class Deft extends AbstractMutation {
    public Deft() {
        super("Deft", true, 4);
    }

    public int getPower() {
        return 1;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        applyToSelf(new DexterityPower(adp(), getPower()));
        scab(card);
    }
}