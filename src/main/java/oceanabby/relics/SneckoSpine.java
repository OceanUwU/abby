package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import oceanabby.characters.TheAberrant;

public class SneckoSpine extends AbstractAbbyRelic {
    public static final String ID = makeID("SneckoSpine");

    public SneckoSpine() {
        super(ID, RelicTier.SPECIAL, LandingSound.HEAVY, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
