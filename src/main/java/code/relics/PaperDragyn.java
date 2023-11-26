package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class PaperDragyn extends AbstractEasyRelic {
    public static final String ID = makeID("PaperDragyn");

    public PaperDragyn() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
