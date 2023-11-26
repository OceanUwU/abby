package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class SneckoSpine extends AbstractEasyRelic {
    public static final String ID = makeID("SneckoSpine");

    public SneckoSpine() {
        super(ID, RelicTier.UNCOMMON, LandingSound.HEAVY, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
