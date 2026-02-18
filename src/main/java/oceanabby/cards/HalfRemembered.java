package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.mechanics.Evo;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class HalfRemembered extends AbstractAbbyCard {
    public final static String ID = makeID("HalfRemembered");

    public HalfRemembered() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1, +1);
        setSecondMagic(1);
        setThirdMagic(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.DEBUFF, false, p, -1) {
            private boolean triggered = false;
            @Override public void onUseCard(AbstractCard c, UseCardAction action) {
                if (!triggered && Evo.Field.evod.get(c)) {
                    actB(() -> Evo.devo(c));
                    flash();
                    triggered = true;
                    updateDescription();
                }
            }

            @Override
            public void atStartOfTurn() {
                if (triggered) {
                    triggered = false;
                    updateDescription();
                }
            }

            public void updateDescription() {
                description = strings[1] + strings[triggered ? 12 : 11];
            }
        });
        applyToSelf(new LambdaPower(ID + "D", exDesc, exDesc[2], PowerType.BUFF, false, p, magicNumber) {
            private boolean triggered = false;

            @Override public void onUseCard(AbstractCard c, UseCardAction action) {
                if (!triggered && Evo.Field.evod.get(c)) {
                    flash();
                    atb(new DrawCardAction(amount));
                    triggered = true;
                    updateDescription();
                }
            }

            @Override
            public void atStartOfTurn() {
                if (triggered) {
                    triggered = false;
                    updateDescription();
                }
            }

            public void updateDescription() {
                description = strings[3] + amount + strings[amount == 1 ? 4 : 5] + strings[triggered ? 12 : 11];
            }
        });
        applyToSelf(new LambdaPower(ID + "E", exDesc, exDesc[6], PowerType.BUFF, false, p, secondMagic) {
            private boolean triggered = false;

            @Override public void onUseCard(AbstractCard c, UseCardAction action) {
                if (!triggered && Evo.Field.evod.get(c)) {
                    flash();
                    atb(new GainEnergyAction(amount));
                    triggered = true;
                    updateDescription();
                }
            }

            @Override
            public void atStartOfTurn() {
                if (triggered) {
                    triggered = false;
                    updateDescription();
                }
            }

            public void updateDescription() {
                description = strings[7];
                for (int i = 0; i < amount; i++)
                    description += "[E] ";
                description += LocalizedStrings.PERIOD + strings[triggered ? 12 : 11];
            }
        });
        if (evod)
            applyToSelf(new LambdaPower(ID + "B", exDesc, exDesc[8], PowerType.BUFF, false, p, thirdMagic) {
                private boolean triggered = false;

                @Override public void onUseCard(AbstractCard c, UseCardAction action) {
                    if (!triggered && Evo.Field.evod.get(c)) {
                        flash();
                        atb(new GainBlockAction(owner, amount));
                    triggered = true;
                    updateDescription();
                    }
                }

                @Override
                public void atStartOfTurn() {
                    if (triggered) {
                        triggered = false;
                        updateDescription();
                    }
                }

                public void updateDescription() {
                    description = strings[9] + amount + strings[10] + strings[triggered ? 12 : 11];
                }
            });
    }
}