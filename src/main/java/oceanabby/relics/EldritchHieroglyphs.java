package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class EldritchHieroglyphs extends AbstractEasyRelic {
    public static final String ID = makeID("EldritchHieroglyphs");

    public EldritchHieroglyphs() {
        super(ID, RelicTier.SPECIAL, LandingSound.MAGICAL, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
