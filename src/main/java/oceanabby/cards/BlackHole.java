package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import oceanabby.actions.EasyXCostAction;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class BlackHole extends AbstractAbbyCard {
    public final static String ID = makeID("BlackHole");

    public BlackHole() {
        super(ID, -1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        setBlock(8, +2);
        setMagic(15);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new EasyXCostAction(this, (power, params) -> {
            for (int i = 0; i < power; i++)
                blckTop();
            return true;
        }));
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.DEBUFF, false, p, magicNumber) {
            private boolean done = false;
            
            @Override public void wasHPLost(DamageInfo info, int damageAmount) {
                if (!done && (AbstractDungeon.getCurrRoom()).phase == RoomPhase.COMBAT && damageAmount > 0) {
                    done = true;
                    flash();
                    att(new LoseHPAction(owner, owner, amount));
                    atb(new RemoveSpecificPowerAction(owner, owner, this));
                }
            }
  
            public void atStartOfTurn() {
                atb(new RemoveSpecificPowerAction(owner, owner, this));
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
    }

    @Override public void evo() {
        upgradeMagicNumber(-7);
    }

    @Override public void devo() {
        upgradeMagicNumber(7);
    }
}