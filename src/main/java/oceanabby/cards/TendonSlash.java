package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class TendonSlash extends AbstractAbbyCard {
    public final static String ID = makeID("TendonSlash");

    public TendonSlash() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(4, +2);
        setSecondMagic(baseDamage + 1);
        setBlock(4, +2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (evod)
            atb(new GainBlockAction(m, secondMagic));
        atb(action(m));
    }

    @Override
    public void update() {
        super.update();
        secondMagic = damage + 1;
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        secondMagic = damage + 1;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        secondMagic = damage + 1;
    }

    @Override
    public void upgrade() {
        super.upgrade();
        baseSecondMagic = secondMagic = secondMagic + 1;
    }

    private AbstractGameAction action(AbstractMonster m) {
        return actionify(() -> {
            actT(() -> {
                if (m != null && m.currentBlock > 0)
                    att(action(m));
            });
            blckTop();
            dmgTop(getRandomEnemy(), AttackEffect.SLASH_HORIZONTAL);
        });
    }
}