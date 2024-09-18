package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class EasyPrey extends AbstractAbbyCard {
    public final static String ID = makeID("EasyPrey");

    public EasyPrey() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(15, +7);
        setSecondDamage(6, +1);
        setMagic(50);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            if (m != null) {
                if (m.currentHealth < magicNumber)
                    dmg(m, AttackEffect.SLASH_DIAGONAL);
                else
                    altDmg(m, AttackEffect.BLUNT_LIGHT);
            }
        });
    }

    @Override public void evo() {
        upgradeMagicNumber(+15);
    }

    @Override public void devo() {
        upgradeMagicNumber(-15);
    }
}