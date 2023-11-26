package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class EldritchHieroglyphs extends AbstractEasyRelic {
    public static final String ID = makeID("EldritchHieroglyphs");

    public EldritchHieroglyphs() {
        super(ID, RelicTier.RARE, LandingSound.MAGICAL, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
