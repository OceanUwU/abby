package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class PaperDragyn extends AbstractEasyRelic {
    public static final String ID = makeID("PaperDragyn");

    public PaperDragyn() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
