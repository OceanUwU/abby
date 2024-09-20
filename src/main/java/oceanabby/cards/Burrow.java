package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Burrow extends AbstractAbbyCard {
    public final static String ID = makeID("Burrow");

    public Burrow() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setBlock(6, +1);
        setMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public int onAttacked(DamageInfo info, int damageAmount) {
                if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
                    flash();
                    applyToSelfTop(new LoseStrengthPower(p, amount));
                    applyToSelfTop(new StrengthPower(p, amount));
                } 
                return damageAmount;
            }
  
            public void atStartOfTurn() {
                atb(new RemoveSpecificPowerAction(owner, owner, this));
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
        if (evod)
            applyToSelf(new LambdaPower(ID + "Dex", exDesc, exDesc[3], PowerType.BUFF, false, p, magicNumber) {
                @Override public int onAttacked(DamageInfo info, int damageAmount) {
                    if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != this.owner) {
                        flash();
                        applyToSelfTop(new LoseDexterityPower(p, amount));
                        applyToSelfTop(new DexterityPower(p, amount));
                    } 
                    return damageAmount;
                }
  
                public void atStartOfTurn() {
                    atb(new RemoveSpecificPowerAction(owner, owner, this));
                }

                public void updateDescription() {
                    description = strings[4] + amount + strings[5];
                }
            });
    }
}