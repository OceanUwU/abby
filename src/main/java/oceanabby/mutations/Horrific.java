package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.WeakPower;

import static oceanabby.util.Wiz.*;

public class Horrific extends AbstractMutation {
    public Horrific() {
        super("Horrific", true, 6);
    }
    
    protected int getPower() {
        return 1;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        forAllMonstersLiving(m -> applyToEnemy(m, new WeakPower(m, getPower(), false)));
    }
}