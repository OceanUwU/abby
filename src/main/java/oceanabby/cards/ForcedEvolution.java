package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ForcedEvolution extends AbstractAbbyCard {
    public final static String ID = makeID("ForcedEvolution");

    public ForcedEvolution() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean uppem = evod;
        atb(new DrawCardAction(magicNumber, actionify(() -> DrawCardAction.drawnCards.forEach(c -> {
            if (uppem)
                c.upgrade();
            Evo.evo(c);
            c.superFlash();
        }))));
    }
}