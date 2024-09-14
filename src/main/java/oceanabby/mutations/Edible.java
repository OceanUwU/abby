package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static oceanabby.util.Wiz.*;

public class Edible extends AbstractMutation {
    public Edible() {
        super("Edible", true, 4);
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new GainEnergyAction(1));
    }
}