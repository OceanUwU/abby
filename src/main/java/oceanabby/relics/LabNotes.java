package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class LabNotes extends AbstractEasyRelic {
    public static final String ID = makeID("LabNotes");

    public LabNotes() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
