package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.AbstractAbbyPower;
import oceanabby.powers.Acid;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class FluidCycle extends AbstractAbbyCard {
    public final static String ID = makeID("FluidCycle");

    public FluidCycle() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractAbbyPower po = new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
                if (power instanceof Acid && source == owner) {
                    flash();
                    att(new ReducePowerAction(owner, owner, this, 1));
                    att(new GainBlockAction(owner, power.amount));
                }
            }

            @Override public void updateDescription() {
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
            }
        };
        po.priority = 6;
        applyToSelf(po);
    }

    @Override public void evo() {
        upgradeMagicNumber(1);
    }

    @Override public void devo() {
        upgradeMagicNumber(-1);
    }
}