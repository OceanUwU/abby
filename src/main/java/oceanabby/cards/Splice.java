package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Splice extends AbstractAbbyCard {
    public final static String ID = makeID("Splice");

    public Splice() {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, CardColor.COLORLESS);
        setMagic(4, +1);
        setSecondMagic(1);
        setEthereal(true);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new DexterityPower(p, -secondMagic));
        applyToSelf(new StrengthPower(p, magicNumber));
        if (evod)
            makeInHand(cardsToPreview);
    }

    @Override
    public void upp() {
        super.upp();
        if (cardsToPreview != null)
            cardsToPreview.upgrade();
    }

    @Override
    public void evo() {
        cardsToPreview = new Splice();
        if (upgraded)
            cardsToPreview.upgrade();
    }

    @Override
    public void devo() {
        cardsToPreview = null;
    }
}