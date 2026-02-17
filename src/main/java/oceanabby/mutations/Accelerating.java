package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.stream.Collectors;
import oceanabby.mechanics.Evo;

import static oceanabby.util.Wiz.*;

public class Accelerating extends AbstractMutation {
    public Accelerating() {
        super("Accelerating", true, 3);
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        actB(() -> {
            ArrayList<AbstractCard> cards = new ArrayList<>(adp().hand.group.stream().filter(c -> Evo.shouldEvo(c) && !Evo.isEvod(c)).collect(Collectors.toList()));
            if (cards.size() > 0)
                Evo.evo(cards.get(AbstractDungeon.cardRng.random(cards.size() - 1)));
        });
    }
}