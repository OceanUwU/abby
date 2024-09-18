package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class GiganticGrowth extends AbstractAbbyCard {
    public final static String ID = makeID("GiganticGrowth");

    public GiganticGrowth() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(3, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        
    }
}