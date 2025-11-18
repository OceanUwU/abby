package oceanabby.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;

public class MarrowAdaptation extends AbstractAdaptation {
    public final static String ID = makeID("MarrowAdaptation");

    public MarrowAdaptation() {
        super(ID);
        setMagic(1);
        cardsToPreview = new Marrow();
    }

    @Override
    public void onThrob() {
        this.addToTop(new MakeTempCardInHandAction(cardsToPreview.makeStatEquivalentCopy(), magicNumber, false));
        pulse(Color.YELLOW, Color.WHITE);
    }

    @Override
    public void upp() {
        cardsToPreview.upgrade();
    }

    @Override
    public void evo() {
        Evo.evo(cardsToPreview);
    }

    @Override
    public void devo() {
        Evo.devo(cardsToPreview);
    }
}