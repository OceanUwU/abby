package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import oceanabby.powers.Acid;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class SalivaSlick extends AbstractEasyCard {
    public final static String ID = makeID("SalivaSlick");

    public SalivaSlick() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        setMagic(5, +1);
        setSecondMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new WeakPower(m, secondMagic, false));
        applyToEnemy(m, new Acid(m, magicNumber));
    }
}