package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Delirium extends AbstractAbbyCard {
    public final static String ID = makeID("Delirium");

    public Delirium() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(2, +1);
        setSecondMagic(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new DrawCardAction(magicNumber, actionify(() -> {
            for (AbstractCard card : DrawCardAction.drawnCards) {
                if (card.cost >= 0) {
                    int newCost = AbstractDungeon.cardRandomRng.random(3);
                    if (card.cost != newCost) {
                        card.cost = newCost;
                        card.costForTurn = card.cost;
                        card.isCostModified = true;
                    }
                }
            }
        })));
        if (evod) {
            atb(new GainEnergyAction(secondMagic));
            actB(() -> Evo.devo(this));
        }
    }
}