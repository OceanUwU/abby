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
        adaptation.counter = turns;
        Adaptations.addAptation((AbstractAdaptation)adaptation.makeStatEquivalentCopy());
        isDone = true;
    }
}
