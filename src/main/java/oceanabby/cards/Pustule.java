package oceanabby.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Pustule extends AbstractAdaptation {
    public final static String ID = makeID("Pustule");

    public Pustule() {
        super(ID);
        setMagic(4, +3);
        setSecondMagic(3);
    }

    public void onThrob() {
        att(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        pulse(Color.RED, Color.FIREBRICK);
    }

    @Override public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if (evod)
            return damage + secondMagic;
        return damage;
    }
}