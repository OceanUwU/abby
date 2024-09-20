package oceanabby.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ToTheLimit extends AbstractAbbyCard {
    public final static String ID = makeID("ToTheLimit");

    public ToTheLimit() {
        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        setHaunted(20, -5);
        setMagic(10, +2);
        setSecondMagic(3);
        setThirdMagic(1);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new StrengthPower(p, magicNumber));
        applyToSelf(new LoseStrengthPower(p, magicNumber));
        if (evod) {
            applyToSelf(new DexterityPower(p, secondMagic));
            applyToSelf(new LoseDexterityPower(p, secondMagic));
        }
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.DEBUFF, false, p, thirdMagic) {
            private boolean done = false;
            
            @Override public void wasHPLost(DamageInfo info, int damageAmount) {
                if (!done && (AbstractDungeon.getCurrRoom()).phase == RoomPhase.COMBAT && damageAmount > 0) {
                    done = true;
                    flash();
                    att(new LoseHPAction(owner, owner, 99999));
                    att(new VFXAction(new LightningEffect(this.owner.hb.cX, this.owner.hb.cY)));
                }
            }
  
            public void atStartOfTurn() {
                atb(new ReducePowerAction(owner, owner, this, 1));
            }

            public void updateDescription() {
                description = amount == 1 ? strings[3] : (strings[1] + amount + strings[2]);
            }
        });
    }
}