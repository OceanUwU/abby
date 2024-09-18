package oceanabby.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Deformation extends AbstractAbbyCard {
    public final static String ID = makeID("Deformation");

    public Deformation() {
        super(ID, 0, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        cardsToPreview = new Splice();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!evod)
            applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, 1) {
                @Override public void atStartOfTurn() {
                    flash();
                    makeInHand(new Splice());
                }

                public void updateDescription() {
                    description = strings[1] + amount + strings[2];
                }
            });
        else
            applyToSelf(new LambdaPower(ID + "Evo", exDesc, exDesc[0], PowerType.BUFF, false, p, 1) {
                @Override public void atStartOfTurn() {
                    flash();
                    AbstractCard c = new Splice();
                    c.upgrade();
                    makeInHand(c);
                }

                public void updateDescription() {
                    description = strings[1] + amount + strings[3];
                }
            });
        if (upgraded)
            makeInHand(cardsToPreview);
    }

    @Override public void evo() {
        cardsToPreview.upgrade();
    }

    @Override public void devo() {
        cardsToPreview = new Splice();
    }
}