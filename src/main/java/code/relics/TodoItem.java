package code.relics;

import static code.AbbyMod.makeID;

import code.characters.TheAberrant;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
}
