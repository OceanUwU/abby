package oceanabby.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Razorspine extends AbstractAdaptation {
    public final static String ID = makeID("Razorspine");

    public Razorspine() {
        super(ID);
        setMagic(5, +3);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != adp() && damageAmount > 0) {
            if (evod)
                addToTop(new DamageAction(info.owner, new DamageInfo(adp(), magicNumber, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
            addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(magicNumber, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            pulse(Color.BROWN, Color.GOLD);
        }
        return damageAmount;
    }
}