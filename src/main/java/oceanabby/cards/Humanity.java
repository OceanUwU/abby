package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Mutations;
import oceanabby.mutations.Swift;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Humanity extends AbstractAbbyCard {
    public final static String ID = makeID("Humanity");

    public Humanity() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(3, +1);
        setSecondMagic(1);
        cardsToPreview = new Marrow();
        Mutations.mutateWith(cardsToPreview, new Swift());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!evod || magicNumber > secondMagic)
            shuffleIn(cardsToPreview, evod ? magicNumber - secondMagic : magicNumber);
        if (evod)
            makeInHand(cardsToPreview, secondMagic);
    }
}