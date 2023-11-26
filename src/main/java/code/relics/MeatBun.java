package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class MeatBun extends AbstractEasyRelic {
    public static final String ID = makeID("MeatBun");

    public MeatBun() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
