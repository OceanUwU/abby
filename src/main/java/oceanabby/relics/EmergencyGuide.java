package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class EmergencyGuide extends AbstractAbbyRelic {
    public static final String ID = makeID("EmergencyGuide");

    public EmergencyGuide() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
