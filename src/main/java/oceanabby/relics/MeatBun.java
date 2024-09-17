package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class MeatBun extends AbstractAbbyRelic {
    public static final String ID = makeID("Meatbun");
    private static int POWER = 20;

    public MeatBun() {
        super(ID, RelicTier.SPECIAL, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
}
