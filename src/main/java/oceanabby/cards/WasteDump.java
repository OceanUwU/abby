package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.Acid;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class WasteDump extends AbstractAbbyCard {
    public final static String ID = makeID("WasteDump");

    public WasteDump() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public void atStartOfTurn() {
                flash();
                forAllMonstersLiving(mo -> applyToEnemy(mo, new Acid(mo, amount)));
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
    }

    @Override
    public void evo() {
        upgradeMagicNumber(1);
    }

    @Override
    public void devo() {
        upgradeMagicNumber(-1);
    }
}