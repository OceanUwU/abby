package oceanabby.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class HardShell extends AbstractAdaptation {
    public final static String ID = makeID("HardShell");

    public HardShell() {
        super(ID);
        setBlock(7, +4);
        setMagic(1);
    }

    @Override
    public void onThrob() {
        block = baseBlock;
        blckTop();
        if (evod)
            applyToSelfTop(new PlatedArmorPower(adp(), magicNumber));
        pulse(Color.CYAN, Color.SKY);
    }
}