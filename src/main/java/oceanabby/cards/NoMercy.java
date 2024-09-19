package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.mechanics.Evo;
import oceanabby.powers.AbstractAbbyPower;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class NoMercy extends AbstractAbbyCard {
    public final static String ID = makeID("NoMercy");

    private static int applied = 0;

    public NoMercy() {
        super(ID, 0, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(5);
        setMagic(2, +1);
        setSecondMagic(1);
        cardsToPreview = new Marrow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.SLASH_VERTICAL);
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
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3] + amount2 + (evoIt ? (amount2 == 1 ? 6 : 7) : (amount2 == 1 ? 4 : 5)) + strings[8];
            }
        };
        po.ID += String.valueOf(applied++);
        po.amount2 = secondMagic;
        po.isTwoAmount = true;
        po.updateDescription();
        applyToSelf(po);
    }

    @Override public void evo() {
        Evo.evo(cardsToPreview);
    }

    @Override public void devo() {
        Evo.devo(cardsToPreview);
    }
}