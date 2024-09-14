package oceanabby.mechanics;

import basemod.AutoAdd;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import oceanabby.mutations.AbstractMutation;

import static oceanabby.AbbyMod.modID;
import static oceanabby.util.Wiz.*;

public class Mutations {
    private static ArrayList<AbstractMutation> mutations;

    public static void mutate(AbstractCard c) {
        AbstractMutation mutation = rollMutation(c);
        if (mutation != null) {
            CardModifierManager.addModifier(c, mutation);
            c.superFlash();
        }
    }

    public static void mutateWith(AbstractCard c, AbstractMutation mutation) {
        if (mutation != null && !CardModifierManager.hasModifier(c, mutation.identifier(c))) {
            CardModifierManager.addModifier(c, mutation);
            c.superFlash();
        }
    }

    public static AbstractGameAction action(AbstractCard c) {
        return actionify(() -> mutate(c));
    }

    public static boolean canMutate(AbstractCard c) {
        return c.cost > -2;
    }

    public static boolean canMutateWith(AbstractCard c, AbstractMutation mutation) {
        return canMutate(c) && mutation.canMutate(c) && !CardModifierManager.hasModifier(c, mutation.identifier(c));
    }

    private static AbstractMutation rollMutation(AbstractCard c) {
        initialize();
        List<AbstractMutation> availableMutations = mutations.stream().filter(m -> m.canMutate(c) && !CardModifierManager.hasModifier(c, m.identifier(c))).collect(Collectors.toList());
        if (availableMutations.size() == 0)
            return null;
        int roll = AbstractDungeon.cardRandomRng.random(0, availableMutations.stream().mapToInt(a -> a.rarity).sum());
        for (AbstractMutation m : availableMutations) {
            roll -= m.rarity;
            if (roll <= 0)
                return m;
        }
        return availableMutations.get(0);
    }

    private static void initialize() {
        if (mutations == null) {
            mutations = new ArrayList<>();
            new AutoAdd(modID).any(AbstractMutation.class, (info, mutation) -> mutations.add(mutation));
        }
    }
}