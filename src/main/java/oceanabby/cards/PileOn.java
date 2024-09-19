package oceanabby.cards;

import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;

public class PileOn extends AbstractAbbyCard {
    public final static String ID = makeID("PileOn");

    public PileOn() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        MultiCardPreview.add(this, new Marrow());
        Evo.evo(MultiCardPreview.multiCardPreview.get(this).get(0));
        MultiCardPreview.add(this, new Dazed());
        setMagic(2, +1);
        setSecondMagic(1);
        setThirdMagic(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        makeInHand(MultiCardPreview.multiCardPreview.get(this).get(0), magicNumber);
        shuffleIn(new Dazed(), secondMagic);
    }

    @Override public void evo() {
        MultiCardPreview.multiCardPreview.get(this).get(0).baseDamage += thirdMagic;
        MultiCardPreview.multiCardPreview.get(this).get(0).damage = MultiCardPreview.multiCardPreview.get(this).get(0).baseDamage;
    }

    @Override public void devo() {
        MultiCardPreview.multiCardPreview.get(this).get(0).baseDamage -= thirdMagic;
        MultiCardPreview.multiCardPreview.get(this).get(0).damage = MultiCardPreview.multiCardPreview.get(this).get(0).baseDamage;
    }
}