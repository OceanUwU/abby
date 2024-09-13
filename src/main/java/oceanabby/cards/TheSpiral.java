package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;

public class TheSpiral extends AbstractAbbyCard {
    public final static String ID = makeID("TheSpiral");

    public TheSpiral() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, +1);
        cardsToPreview = new HardShell();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addAptation();
    }
}