package oceanabby.cards;


import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.actB;

public class NoMercy extends AbstractAbbyCard {
    public final static String ID = makeID("NoMercy");

    public NoMercy() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        setMagic(2, +1);
        cardsToPreview = new MarrowAdaptation();
        MultiCardPreview.add(this, new Marrow());
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addAptation();
        if (evod)
            actB(() -> {
                for (AbstractCard c : p.hand.group)
                    if (c instanceof Marrow)
                        Evo.evo(c);
            });
    }

    @Override public void evo() {
        AbstractCard c = new Marrow();
        Evo.evo(c);
        MultiCardPreview.add(this, c);
    }

    @Override public void devo() {
        MultiCardPreview.multiCardPreview.get(this).removeIf(c -> c instanceof Marrow && Evo.isEvod(c));
    }
}

/*
old version that gave you a power

AbstractAbbyPower po = new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, true, p, magicNumber) {
    public boolean evoIt = evod;

    public void atStartOfTurn() {
        flash();
        AbstractCard c = new Marrow();
        if (evoIt)
            Evo.evo(c);
        makeInHand(c, amount2);
        atb(new ReducePowerAction(p, m, this, 1));
    }

    public void updateDescription() {
        description = strings[1] + amount + strings[amount == 1 ? 2 : 3] + amount2 + strings[evoIt ? (amount2 == 1 ? 6 : 7) : (amount2 == 1 ? 4 : 5)] + strings[8];
    }
};
*/