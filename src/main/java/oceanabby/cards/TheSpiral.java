package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.applyToSelf;

public class TheSpiral extends AbstractAbbyCard {
    public final static String ID = makeID("TheSpiral");

    public TheSpiral() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, +1);
        setSecondMagic(1);
        cardsToPreview = new HardShell();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (evod)
            applyToSelf(new PlatedArmorPower(p, secondMagic));
        addAptation();
    }
}