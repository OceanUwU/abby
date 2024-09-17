package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class TheSymbiont extends AbstractAbbyRelic {
    public static final String ID = makeID("TheSymbiont");
    private static int POWER = 3;

    public TheSymbiont() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }

    public String getUpdatedDescription() {
        if (POWER == 1) return DESCRIPTIONS[0];
        return DESCRIPTIONS[1] + POWER + DESCRIPTIONS[2];
    }
}
