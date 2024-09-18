package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.Acid;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Bile extends AbstractAbbyCard {
    public final static String ID = makeID("Bile");

    public Bile() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(7, +2);
        setMagic(6, +2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.POISON);
        if (m.hasPower(Acid.POWER_ID) || (evod && m.powers.stream().anyMatch(pow -> pow.type == PowerType.DEBUFF)))
            applyToEnemy(m, new Acid(m, magicNumber));
    }
}