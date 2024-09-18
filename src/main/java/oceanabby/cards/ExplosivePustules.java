package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.actions.AddAptationAction;
import oceanabby.powers.AbstractAbbyPower;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ExplosivePustules extends AbstractAbbyCard {
    public final static String ID = makeID("ExplosivePustules");

    private static int applied = 0;

    public ExplosivePustules() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2);
        cardsToPreview = new Pustule();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractAbbyPower po = new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public void atStartOfTurn() {
                flash();
                atb(new AddAptationAction(new Pustule(), amount));
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        };
        po.ID += String.valueOf(applied++);
        applyToSelf(po);
        if (evod)
            addAptation();
    }

    @Override public void evo() {
        upgradeMagicNumber(1);
    }

    @Override public void devo() {
        upgradeMagicNumber(-1);
    }
}