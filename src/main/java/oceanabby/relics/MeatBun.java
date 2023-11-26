package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class MeatBun extends AbstractEasyRelic {
    public static final String ID = makeID("MeatBun");

    public MeatBun() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
