package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class MutationCatalyst extends AbstractEasyRelic {
    public static final String ID = makeID("MutationCatalyst");

    public MutationCatalyst() {
        super(ID, RelicTier.STARTER, LandingSound.CLINK, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
