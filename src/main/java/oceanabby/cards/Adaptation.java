package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Adaptation extends AbstractAbbyCard {
    public final static String ID = makeID("Adaptation");

    public Adaptation() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(1, -1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        if (block > 0)
            blck();
        actB(() -> p.hand.group.forEach(c -> att(Evo.action(c))));
        if (!upgraded)
            atb(new DiscardAction(p, p, magicNumber, true));
    }
  
    @Override public void applyPowers() {
        if (evod)
            baseBlock = (int)adp().hand.group.stream().filter(c -> c != this && !Evo.Field.evod.get(c)).count();
        super.applyPowers();
        if (!evod)
            baseBlock = block = -1;
    }

    @Override public void evo() {
        setBlock(-1);
        target = CardTarget.SELF;
    }

    @Override public void devo() {
        setBlock(-1);
        target = CardTarget.NONE;
    }
}