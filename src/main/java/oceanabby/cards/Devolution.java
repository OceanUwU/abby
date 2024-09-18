package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.AbstractAbbyPower;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Devolution extends AbstractAbbyCard {
    public final static String ID = makeID("Devolution");

    private static int applied = 0;

    public Devolution() {
        super(ID, -1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        setMagic(10, +2);
        setSecondMagic(5);
        setThirdMagic(3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, -secondMagic));
        AbstractAbbyPower po = new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, true, p, magicNumber) {
            @Override public void atStartOfTurn() {
                if (this.amount2 <= 1) {
                    flash();
                    applyToSelf(new StrengthPower(owner, amount2));
                    atb(new RemoveSpecificPowerAction(owner, owner, this));
                } else {
                    actT(() -> {
                        amount2--;
                        fontScale = 8.0F;
                        updateDescription();
                        AbstractDungeon.onModifyPower();
                    });
                } 
            }

            @Override public void updateDescription() {
                description = (amount2 == 1 ? strings[1] : (strings[2] + amount2 + strings[3])) + amount + strings[4];
            }
        };
        po.ID += String.valueOf(applied++);
        po.amount2 = thirdMagic;
        po.isTwoAmount = true;
        po.updateDescription();
        applyToSelf(po);
    }

    @Override public void evo() {
        upgradeThirdMagic(-1);
    }

    @Override public void devo() {
        upgradeThirdMagic(1);
    }
}