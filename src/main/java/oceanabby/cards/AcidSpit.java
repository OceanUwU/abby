package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.Acid;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class AcidSpit extends AbstractAbbyCard {
    public final static String ID = makeID("AcidSpit");

    public AcidSpit() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, +1);
        setSecondMagic(1);
    }


    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
                if (power instanceof Acid && source == owner) {
                    power.amount += amount;
                    flash();
                }
            }

            @Override public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
        if (evod)
            applyToSelf(new LambdaPower(ID + "Evo", exDesc, exDesc[3], PowerType.BUFF, false, p, magicNumber) {
                @Override public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
                    if (power instanceof Acid && source == owner) {
                        atb(new GainBlockAction(owner, amount));
                        flash();
                    }
                }

                @Override public void updateDescription() {
                    description = strings[4] + amount + strings[5];
                }
            });
    }
}