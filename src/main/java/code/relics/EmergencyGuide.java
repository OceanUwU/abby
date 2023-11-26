package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class EmergencyGuide extends AbstractEasyRelic {
    public static final String ID = makeID("EmergencyGuide");

    public EmergencyGuide() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
