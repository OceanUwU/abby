package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class CosmicHorror extends AbstractAbbyCard {
    public final static String ID = makeID("CosmicHorror");

    public CosmicHorror() {
        super(ID, 1, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(1);
        setUpgradedCost(0);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public void onAdapt(AbstractAdaptation a) {
                flash();
                a.counter += amount;
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
            }
        });
        if (evod)
            applyToSelf(new LambdaPower(ID + "E", exDesc, exDesc[4], PowerType.BUFF, false, p, magicNumber) {
                @Override public void onAdaptationEnd(AbstractAdaptation a) {
                    flash();
                    atb(new GainEnergyAction(amount));
                }

                public void updateDescription() {
                    description = strings[5];
                    for (int i = 0; i < amount; i++)
                        description += "[E] ";
                    description += LocalizedStrings.PERIOD;
                }
            });
    }
}