package oceanabby.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardAtBottomOfDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAndDeckAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.unique.BurnIncreaseAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import java.util.ArrayList;
import java.util.stream.Collectors;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class FleshHeap extends AbstractAbbyCard {
    public final static String ID = makeID("FleshHeap");

    public FleshHeap() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.ALL);
        setBlock(12);
        setMagic(1, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        forAllMonstersLiving(mo -> applyToEnemy(mo, new LambdaPower(ID, exDesc, exDesc[0], PowerType.DEBUFF, true, mo, magicNumber) {
            @Override public void atEndOfRound() {
                if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                    flashWithoutSound();
                    atb(new ReducePowerAction(owner, owner, this, 1));
                }
            }

            public void updateDescription() {
                description = amount == 1 ? strings[1] : (strings[2] + amount + strings[3]);
            }
        }));
    }

    @SpirePatch(clz=GameActionManager.class, method="getNextAction")
    public static class Patch {
        @SpireInsertPatch(rloc=210, localvars={"m"})
        public static void Insert(GameActionManager __instance, AbstractMonster m) {
            if (m.hasPower(ID)) {
                int numActions = __instance.actions.size();
                __instance.actions = new ArrayList<>(__instance.actions.stream().filter(a -> !(a instanceof MakeTempCardInDiscardAction || a instanceof MakeTempCardAtBottomOfDeckAction || a instanceof MakeTempCardInDiscardAndDeckAction || a instanceof MakeTempCardInDrawPileAction || a instanceof MakeTempCardInHandAction || a instanceof BurnIncreaseAction)).collect(Collectors.toList()));
                if (__instance.actions.size() < numActions)
                    m.getPower(ID).flash();
            }
        }
    }

    @Override public void evo() {
        upgradeBlock(4);
    }

    @Override public void devo() {
        upgradeBlock(-4);
    }
}