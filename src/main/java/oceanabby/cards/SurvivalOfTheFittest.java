package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class SurvivalOfTheFittest extends AbstractEasyCard {
    public final static String ID = makeID("SurvivalOfTheFittest");

    public SurvivalOfTheFittest() {
        super(ID, -1, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE);
        
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        
    }
}