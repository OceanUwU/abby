package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class LabNotes extends AbstractEasyRelic {
    public static final String ID = makeID("LabNotes");

    public LabNotes() {
        super(ID, RelicTier.RARE, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
