package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DuplicationPower;
import oceanabby.util.Wiz;

public class Conjoined extends AbstractMutation {
    public Conjoined() {
        super("Conjoined", true, 1);
    }

    public boolean canMutate(AbstractCard c) {
        return !c.hasTag(AbstractCard.CardTags.HEALING);
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        new DuplicationPower(Wiz.adp(), 1).onUseCard(card, action);
    }
}