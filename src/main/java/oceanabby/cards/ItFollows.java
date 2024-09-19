package oceanabby.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ItFollows extends AbstractAbbyCard {
    public final static String ID = makeID("ItFollows");

    public ItFollows() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setInnate(true);
        setMagic(4);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsAction(p.drawPile.group, magicNumber, exDesc[evod ? (upgraded ? 3 : 1) : (upgraded ? 2 : 0)], false, evod ? (c -> Mutations.canMutate(c) || Evo.shouldEvo(c)) : c -> Mutations.canMutate(c), cards -> {
            cards.forEach(c -> {
                Mutations.mutate(c);
                if (upgraded)
                    Evo.evo(c);
                if (evod)
                    c.upgrade();
                att(new DiscardSpecificCardAction(c, p.drawPile));
            });
        }));
    }
}