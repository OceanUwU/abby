package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.powers.Acid;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class SuspiciousCure extends AbstractAbbyCard {
    public final static String ID = makeID("SuspiciousCure");

    public SuspiciousCure() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setMagic(3, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null && m.hasPower(Acid.POWER_ID)) {
            Acid a = (Acid)m.getPower(Acid.POWER_ID);
            atb(new DamageAction(m, new DamageInfo(p, a.amount * magicNumber, DamageType.HP_LOSS), AttackEffect.POISON),
                new RemoveSpecificPowerAction(m, p, a),
                a.checkSpew());
        }
    }

    @Override
    public void evo() {
        upgradeBaseCost(0);
    }

    @Override
    public void devo() {
        upgradeBaseCost(1);
    }
}