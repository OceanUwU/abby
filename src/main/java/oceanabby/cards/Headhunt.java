package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Headhunt extends AbstractAbbyCard {
    public final static String ID = makeID("Headhunt");

    private ArrayList<AbstractMonster> monsters;

    public Headhunt() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        setDamage(5, +3);
        setSecondDamage(50);
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> monsters = getEnemies());
        allDmg(AttackEffect.BLUNT_LIGHT);
        actB(() -> {
            for (AbstractMonster mo : monsters) {
                if (!getEnemies().contains(mo)) {
                    att(new DamageAllEnemiesAction(AbstractDungeon.player, baseSecondDamage, damageTypeForTurn, AttackEffect.BLUNT_HEAVY));
                    break;
                }
            }
            monsters = null;
        });
    }

    @Override public void evo() {
        upgradeDamage(3);
    }

    @Override public void devo() {
        upgradeDamage(-3);
    }
}