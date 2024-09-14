package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import java.util.ArrayList;
import java.util.stream.Collectors;
import oceanabby.mechanics.Mutations;

import static oceanabby.util.Wiz.*;

public class Contagious extends AbstractMutation {
    public Contagious() {
        super("Contagious", true, 3);
    }
    
    protected int getPower() {
        return 3;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        applyToSelf(new VigorPower(adp(), getPower()));
        actB(() -> {
            ArrayList<AbstractCard> cards = new ArrayList<>(adp().hand.group.stream().filter(c -> Mutations.canMutateWith(c, this)).collect(Collectors.toList()));
            if (cards.size() > 0)
                Mutations.mutateWith(cards.get(AbstractDungeon.cardRng.random(cards.size() - 1)), (Contagious)makeCopy());
        });
        scab(card);
    }
}