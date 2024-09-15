package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static oceanabby.util.Wiz.*;

public class Grotesque extends AbstractMutation {
    public Grotesque() {
        super("Grotesque", true, 6);
    }
    
    protected int getPower() {
        return 1;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        forAllMonstersLiving(m -> applyToEnemy(m, new VulnerablePower(m, getPower(), false)));
    }
}