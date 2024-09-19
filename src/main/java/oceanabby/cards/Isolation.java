package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Isolation extends AbstractAbbyCard {
    public final static String ID = makeID("Isolation");

    public Isolation() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setMagic(2, +1);
        setSecondMagic(1);
        setThirdMagic(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new GainEnergyAction(magicNumber));
        if (evod) {
            atb(new DrawCardAction(thirdMagic));
            queueDevo();
        }
        applyToSelf(new DrawReductionPower(p, secondMagic));
    }
}