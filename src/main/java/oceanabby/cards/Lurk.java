package oceanabby.cards;

import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Lurk extends AbstractAbbyCard {
    public final static String ID = makeID("Lurk");

    public Lurk() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setBlock(3, +3);
        setMagic(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new ScryAction(magicNumber));
    }

    @Override public void evo() {
        upgradeMagicNumber(2);
    }

    @Override public void devo() {
        upgradeMagicNumber(-2);
    }
}