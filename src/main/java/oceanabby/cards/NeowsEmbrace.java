package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.AbstractAbbyPower;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class NeowsEmbrace extends AbstractAbbyCard {
    public final static String ID = makeID("NeowsEmbrace");

    public NeowsEmbrace() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        setMagic(3, +2);
        setSecondMagic(15);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        forAllMonstersLiving(mo -> {
            AbstractAbbyPower po = new LambdaPower(ID, exDesc, exDesc[0], PowerType.DEBUFF, false, mo, magicNumber) {
                public float atDamageGive(float damage, DamageInfo.DamageType type) {
                    if (type == DamageInfo.DamageType.NORMAL)
                        return damage - amount; 
                    return damage;
                }

                public void atStartOfTurn() {
                    if (owner instanceof AbstractMonster)
                        if (((AbstractMonster)owner).getIntentBaseDmg() > 0)
                            amount2--;
                }

                public void atEndOfTurn(boolean isPlayer) {
                    if (isPlayer || amount2 <= 0)
                        atb(new RemoveSpecificPowerAction(owner, owner, this));
                }

                public void updateDescription() {
                    description = strings[1] + amount + strings[2];
                }
            };
            po.amount2 = 1;
            applyToEnemy(m, po);
            if (evod) {
                atb(new DamageAction(mo, new DamageInfo(p, secondMagic, DamageType.HP_LOSS), AttackEffect.BLUNT_LIGHT));
                AbstractAbbyPower po2 = new LambdaPower(ID + "Evo", exDesc, exDesc[0], PowerType.BUFF, false, mo, secondMagic) {
                    public void atStartOfTurn() {
                        if (owner instanceof AbstractMonster)
                            if (((AbstractMonster)owner).getIntentBaseDmg() > 0)
                                amount2--;
                    }
    
                    public void atEndOfTurn(boolean isPlayer) {
                        if (isPlayer || amount2 <= 0) {
                            atb(new HealAction(m, m, amount));
                            atb(new RemoveSpecificPowerAction(owner, owner, this));
                        }
                    }
    
                    public void updateDescription() {
                        description = strings[1] + amount + strings[2];
                    }
                };
                po2.amount2 = 1;
                applyToEnemy(m, po2);
            }
        });
    }
}