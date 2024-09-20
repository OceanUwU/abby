package oceanabby.powers;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import oceanabby.cards.Spew;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Acid extends AbstractAbbyPower implements HealthBarRenderPower {
    private static final Color colour = new Color(204/255f, 1f, 51/255f, 1f);
    public static final String POWER_ID = makeID("Acid");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public Acid(AbstractCreature owner, int amount) {
        super(POWER_ID, powerStrings.NAME, PowerType.DEBUFF, true, owner, amount);
    }

    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_POISON", 0.05F);
    }

    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1];
    }
  
    public void atStartOfTurn() {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flashWithoutSound();
            atb(new DamageAction(owner, new DamageInfo(adp(), amount, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.POISON),
                new ReducePowerAction(owner, owner, this, (int)Math.ceil(amount / 2d)),
                checkSpew());
        } 
    }

    public AbstractGameAction checkSpew() {
        return actionify(() -> {
            if (!owner.hasPower(ID) && owner.hasPower(Spew.ID)) {
                AbstractPower po = owner.getPower(Spew.ID);
                po.flash();
                atb(new ApplyPowerAction(owner, AbstractDungeon.player, new Acid(owner, po.amount), po.amount));
                atb(new RemoveSpecificPowerAction(owner, owner, po));
            }
        });
    }
        
    public int getHealthBarAmount() { return amount; }
    public Color getColor() { return colour; }
}