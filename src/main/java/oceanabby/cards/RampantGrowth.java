package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class RampantGrowth extends AbstractEasyCard {
    public final static String ID = makeID("RampantGrowth");

    public RampantGrowth() {
        super(ID, -1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        
    }
}