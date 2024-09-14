package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.powers.Acid;

import static oceanabby.util.Wiz.*;

public class Toxic extends AbstractMutation {
    public Toxic() {
        super("Toxic", true, 3);
    }
    
    @Override
    protected int getPower() {
        return 4;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractMonster m = getRandomEnemy();
        if (m != null) applyToEnemy(m, new Acid(m, getPower()));
    }
}