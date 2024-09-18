package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.mechanics.Adaptations;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class BodyHorror extends AbstractAbbyCard {
    public final static String ID = makeID("BodyHorror");

    public BodyHorror() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(4, +2);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public void atStartOfTurn() {
                flash();
                for (int i = 0; i < Adaptations.adaptations.size(); i++)
                    att(new GainBlockAction(m, amount));
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
        if (evod)
            for (AbstractAdaptation a : Adaptations.adaptations)
                a.counter += secondMagic;
    }
}