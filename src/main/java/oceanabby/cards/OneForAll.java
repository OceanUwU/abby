package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.powers.Acid;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

import basemod.ReflectionHacks;

public class OneForAll extends AbstractAbbyCard {
    public final static String ID = makeID("OneForAll");

    public OneForAll() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(2, +1);
        setMagic(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            actB(() -> {
                if (p.isDying)
                    return;

                if (m == null)
                    return;

                calculateCardDamage(m);
                m.damage(new DamageInfo(p, damage, damageTypeForTurn));

                if (m.lastDamageTaken > 0) {
                    ApplyPowerAction instant = new ApplyPowerAction(m, p, new Acid(m, m.lastDamageTaken), m.lastDamageTaken);
                    ReflectionHacks.setPrivate(instant, ApplyPowerAction.class, "startingDuration", 0.01f);
                    ReflectionHacks.setPrivate(instant, AbstractGameAction.class, "duration", 0.01f);
                    att(instant);
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                    AbstractDungeon.actionManager.clearPostCombatActions();
            });
    }

    @Override public void evo() {
        upgradeMagicNumber(1);
    }

    @Override public void devo() {
        upgradeMagicNumber(-1);
    }
}