package oceanabby.cards;

import com.badlogic.gdx.graphics.Color;

import static oceanabby.AbbyMod.makeID;

public class HardShell extends AbstractAdaptation {
    public final static String ID = makeID("HardShell");

    public HardShell() {
        super(ID);
        setBlock(7, +4);
    }

    @Override
    public void onThrob() {
        block = baseBlock;
        blckTop();
        pulse(Color.CYAN, Color.SKY);
    }
}