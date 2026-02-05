package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.actions.AddAptationAction;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class LastDitch extends AbstractAbbyCard {
    public final static String ID = makeID("LastDitch");


    public LastDitch() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        //setMagic(1);
        setSecondMagic(0, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, 1) {
            @Override public void onAdapt(AbstractAdaptation a) {
                flash();
                AbstractAdaptation copy = (AbstractAdaptation)a.makeStatEquivalentCopy();
                if (p.hasPower(ID + "Turn")) {
                    AbstractPower pwr = p.getPower(ID + "Turn");
                    pwr.flash();
                    copy.counter += pwr.amount;
                }
                att(
                    new ReducePowerAction(owner, owner, this, 1),
                    new RemoveSpecificPowerAction(owner, owner, ID + "Turn"),
                    new AddAptationAction(copy, copy.counter));
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
            }
        });
        if (secondMagic > 0)
            applyToSelf(new LambdaPower(ID + "Turn", exDesc, exDesc[0], PowerType.BUFF, false, p, secondMagic) {
                public void updateDescription() {
                    description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
                }
            });
    }

    @Override public void evo() {
        upgradeSecondMagic(1);
    }

    @Override public void devo() {
        upgradeSecondMagic(-1);
    }
}