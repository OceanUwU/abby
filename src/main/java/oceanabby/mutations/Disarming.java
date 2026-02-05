package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static oceanabby.util.Wiz.*;

public class Disarming extends AbstractMutation {
    public Disarming() {
        super("Disarming", true, 3);
    }
    
    protected int getPower() {
        return 2;
    }
    
    public boolean canMutate(AbstractCard c) {
        return c.target == AbstractCard.CardTarget.ENEMY || c.target == AbstractCard.CardTarget.SELF_AND_ENEMY;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        if (target != null && target instanceof AbstractMonster)
            applyToEnemy((AbstractMonster)target, new StrengthPower(target, -getPower()));
        scab(card);
    }
}