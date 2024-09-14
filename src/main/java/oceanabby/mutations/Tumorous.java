package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import oceanabby.mechanics.Mutations;

import static oceanabby.util.Wiz.*;

public class Tumorous extends AbstractMutation {
    public Tumorous() {
        super("Tumorous", true, 2);
    }
    
    public boolean canMutate(AbstractCard c) {
        return !c.exhaust && c.type != AbstractCard.CardType.POWER;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(Mutations.action(card));
    }
}