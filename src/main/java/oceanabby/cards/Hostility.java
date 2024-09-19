package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Hostility extends AbstractAbbyCard {
    public final static String ID = makeID("Hostility");

    public Hostility() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(15, +5);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!evod)
            applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {  
                @Override public int onAttacked(DamageInfo info, int damageAmount) {
                    if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount > 0) {
                        flash();
                        addToTop(new DamageAction(info.owner, new DamageInfo(owner, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL, true));
                    } 
                    return damageAmount;
                }

                public void updateDescription() {
                    description = strings[2] + amount + strings[3];
                }
            });
        else;
            applyToSelf(new LambdaPower(ID, exDesc, exDesc[1], PowerType.BUFF, false, p, magicNumber) {  
                @Override public int onAttacked(DamageInfo info, int damageAmount) {
                    if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount > 0) {
                        flash();
                        addToTop(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                    } 
                    return damageAmount;
                }

                public void updateDescription() {
                    description = strings[2] + amount + strings[4];
                }
            });
    }
}