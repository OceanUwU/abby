package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Overpower extends AbstractAbbyCard {
    public final static String ID = makeID("Overpower");

    public Overpower() {
        super(ID, 2, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(20, +6);
        setBlock(20, +6);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.BLUNT_HEAVY);
        actB(() -> {
            if (evod) {
                for (AbstractMonster mo : getEnemies())
                    if (p.currentHealth > mo.currentHealth) {
                        blckTop();
                        break;
                    }
            } else if (m != null && p.currentHealth > m.currentHealth)
                blckTop(); 
        });
    }
}