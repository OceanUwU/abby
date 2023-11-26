package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class PerfectedCatalyst extends AbstractEasyRelic {
    public static final String ID = makeID("PerfectedCatalyst");

    public PerfectedCatalyst() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
