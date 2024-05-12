package oceanabby.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import oceanabby.cards.AbstractAdaptation;
import oceanabby.mechanics.Adaptations;

public class AddAptationAction extends AbstractGameAction {
    private AbstractAdaptation adaptation;
    private int turns;

    public AddAptationAction(AbstractAdaptation adaptation, int turns) {
        this.adaptation = adaptation;
        this.turns = turns;
    }

    @Override
    public void update() {
        AbstractAdaptation a = (AbstractAdaptation)adaptation.makeStatEquivalentCopy();
        a.counter = turns;
        Adaptations.addAptation(a);
        isDone = true;
    }
}
