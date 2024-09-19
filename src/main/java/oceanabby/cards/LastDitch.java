package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class LastDitch extends AbstractAbbyCard {
    public final static String ID = makeID("LastDitch");

    private static int applied = 0;

    public LastDitch() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(8, +1);
        setMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.SLASH_VERTICAL);
        AbstractPower po = new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public void onAdapt(AbstractAdaptation a) {
                flash();
                a.counter += amount;
                att(new RemoveSpecificPowerAction(owner, owner, this));
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
            }
        };
        po.ID += String.valueOf(applied++);
        applyToSelf(po);
    }

    @Override public void evo() {
        upgradeMagicNumber(1);
    }

    @Override public void devo() {
        upgradeMagicNumber(-1);
    }
}