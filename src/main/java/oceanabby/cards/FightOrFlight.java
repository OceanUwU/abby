package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;

public class FightOrFlight extends AbstractAbbyCard {
    public final static String ID = makeID("FightOrFlight");

    public FightOrFlight() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        setMagic(2, +1);
        setExhaust(true);
        cardsToPreview = new OnTheFly();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addAptation();
    }

    @Override public void evo() {
        setExhaust(false);
    }

    @Override public void devo() {
        setExhaust(true);
    }
}