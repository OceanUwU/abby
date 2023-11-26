package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class TheSymbiont extends AbstractEasyRelic {
    public static final String ID = makeID("TheSymbiont");

    public TheSymbiont() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
