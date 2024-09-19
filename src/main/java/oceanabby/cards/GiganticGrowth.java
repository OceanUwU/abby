package oceanabby.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.List;
import java.util.stream.Collectors;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class GiganticGrowth extends AbstractAbbyCard {
    public final static String ID = makeID("GiganticGrowth");

    public GiganticGrowth() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(3, +1);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            List<AbstractCard> cards = p.drawPile.group.stream().filter(c -> Mutations.canMutate(c)).collect(Collectors.toList());
            for (int i = 0; i < magicNumber; i++) {
                if (cards.size() <= 0)
                    break;
                AbstractCard c = cards.get(AbstractDungeon.cardRng.random(cards.size() - 1));
                cards.remove(c);
                Mutations.mutate(c);
                if (i < secondMagic)
                    att(new MoveCardsAction(p.hand, p.drawPile, card -> card == c));
            }
        });
    }
}