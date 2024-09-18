package oceanabby.cards;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Flatten extends AbstractAbbyCard {
    public final static String ID = makeID("Flatten");

    public Flatten() {
        this(0);
    }

    public Flatten(int upgrades) {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setBlock(4);
        setDamage(5);
        timesUpgraded = upgrades;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        dmg(m, null);
        if (evod) {
            actB(() -> { upgrade(); superFlash(); });
            atb(new WaitAction(0.3f));
        }
    }

    @Override public void upgrade() {
        upgradeBlock(2);
        upgradeDamage(1 + timesUpgraded);
        timesUpgraded++;
        upgraded = true;
        name = cardStrings.NAME + "+" + timesUpgraded;
    }
  
    @Override public boolean canUpgrade() {
        return true;
    }
  
    public AbstractCard makeCopy() {
        return new Flatten(timesUpgraded);
    }
}