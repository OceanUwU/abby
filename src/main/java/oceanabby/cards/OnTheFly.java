package oceanabby.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;
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
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage * (1f - ((float)magicNumber / 100f));
    }

    @Override
    public void evo() {
        applyToSelf(new ArtifactPower(adp(), secondMagic));
    }
}