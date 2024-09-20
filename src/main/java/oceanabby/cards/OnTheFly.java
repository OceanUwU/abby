package oceanabby.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class OnTheFly extends AbstractAdaptation {
    public final static String ID = makeID("OnTheFly");

    public OnTheFly() {
        super(ID);
        setMagic(50, +10);
        setSecondMagic(1);
    }

    @Override
    public void onThrob() {
        AbstractDungeon.onModifyPower();
    }

    @Override
    public void onEnd() {
        actB(() -> AbstractDungeon.onModifyPower());
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageType.NORMAL)
            return damage * (1f - ((float)magicNumber / 100f));
        return damage;
    }

    @Override
    public void evo() {
        applyToSelf(new ArtifactPower(adp(), secondMagic));
    }
}