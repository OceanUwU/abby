package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.Acid;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Spew extends AbstractAbbyCard {
    public final static String ID = makeID("Spew");

    public Spew() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        setMagic(6, +2);
        setSecondMagic(8);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new Acid(m, magicNumber));
        if (evod)
            applyToEnemy(m, new LambdaPower(ID, exDesc, exDesc[0], PowerType.DEBUFF, false, m, secondMagic) {
                @Override public void updateDescription() {
                    description = strings[1] + amount + strings[2];
                }
            });
    }
}