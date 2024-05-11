package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class TheSymbiont extends AbstractEasyRelic {
    public static final String ID = makeID("TheSymbiont");

    public TheSymbiont() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
