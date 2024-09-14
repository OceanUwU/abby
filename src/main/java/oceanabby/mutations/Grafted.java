package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static oceanabby.util.Wiz.*;

import basemod.helpers.CardModifierManager;

public class Grafted extends AbstractMutation {
    public Grafted() {
        super("Grafted", false, 8);
    }
    
    public boolean canMutate(AbstractCard c) {
        return !c.hasTag(CardTags.HEALING) && (c.exhaust || c.type == CardType.POWER);
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractCard copy = card.makeStatEquivalentCopy();
        CardModifierManager.removeModifiersById(copy, id, true);
        shuffleIn(copy);
    }
}