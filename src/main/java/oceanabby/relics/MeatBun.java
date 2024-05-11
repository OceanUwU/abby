package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class MeatBun extends AbstractEasyRelic {
    public static final String ID = makeID("Meatbun");

    public MeatBun() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
