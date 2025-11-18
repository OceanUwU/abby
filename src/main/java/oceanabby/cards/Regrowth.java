package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Regrowth extends AbstractAbbyCard {
    public final static String ID = makeID("Regrowth");

    public Regrowth() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setHaunted(12);
        setMagic(5, +3);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> p.increaseMaxHp(magicNumber, false));
    }

    @Override
    public void evo() {
        upgradeHaunted(-11);
    }

    @Override
    public void devo() {
        upgradeHaunted(11);
    }
}