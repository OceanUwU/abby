package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class BurnVictim extends AbstractAbbyCard {
    public final static String ID = makeID("BurnVictim");

    public BurnVictim() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ALL_ENEMY);
        setDamage(10);
        setMagic(4, +1);
        setSecondMagic(2);
        setThirdMagic(10);
        cardsToPreview = new Burn();
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            allDmg(AttackEffect.FIRE);
        forAllMonstersLiving(mo -> applyToEnemy(mo, new VulnerablePower(mo, secondMagic, false)));
        shuffleIn(cardsToPreview, thirdMagic);
    }

    @Override public void evo() {
        upgradeMagicNumber(-5);
    }

    @Override public void devo() {
        upgradeMagicNumber(5);
    }
}