package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class MutationCatalyst extends AbstractEasyRelic {
    public static final String ID = makeID("MutationCatalyst");

    public MutationCatalyst() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
