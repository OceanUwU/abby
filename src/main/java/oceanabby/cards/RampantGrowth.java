package oceanabby.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class RampantGrowth extends AbstractAbbyCard {
    public final static String ID = makeID("RampantGrowth");

    public RampantGrowth() {
        super(ID, 4, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            for (AbstractCard c : p.hand.group) Mutations.mutate(c);
            for (AbstractCard c : p.drawPile.group) Mutations.mutate(c);
            for (AbstractCard c : p.discardPile.group) Mutations.mutate(c);
            for (AbstractCard c : p.exhaustPile.group) Mutations.mutate(c);
        });
    }

    @Override
    public void upp() {
        upgradeBaseCost(evod ? 2 : 3);
    }

    @Override
    public void evo() {
        upgradeBaseCost(upgraded ? 2 : 3);
    }

    @Override
    public void devo() {
        upgradeBaseCost(upgraded ? 3 : 4);
    }
}