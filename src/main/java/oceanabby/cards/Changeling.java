package oceanabby.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.stream.Collectors;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Changeling extends AbstractAbbyCard {
    public final static String ID = makeID("Changeling");

    public Changeling() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.NONE);
        setMagic(1);
        setSecondMagic(1);
    }

    private AbstractGameAction reduceCost(AbstractCard c) {
        return actionify(() -> {
            if (evod && c.costForTurn > 0) {
                c.costForTurn = Math.max(0, c.costForTurn - secondMagic);
                c.isCostModifiedForTurn = true;
            }
        });
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!upgraded)
            actB(() -> {
                ArrayList<AbstractCard> cards = new ArrayList<>(adp().hand.group.stream().filter(c -> Mutations.canMutate(c)).collect(Collectors.toList()));
                for (int i = 0; i < magicNumber; i++) {
                    int chosen = AbstractDungeon.cardRng.random(cards.size() - 1);
                    att(reduceCost(cards.get(chosen)));
                    att(Mutations.action(cards.get(chosen)));
                    cards.remove(chosen);
                    if (cards.size() <= 0)
                        break;
                }
            });
        else
            atb(new SelectCardsInHandAction(exDesc[0], c -> Mutations.canMutate(c), cards -> {
                cards.forEach(c -> {
                    att(reduceCost(c));
                    att(Mutations.action(c));
                });
            }));
    }
}