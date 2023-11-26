package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class PerfectedCatalyst extends AbstractEasyRelic {
    public static final String ID = makeID("PerfectedCatalyst");

    public PerfectedCatalyst() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
