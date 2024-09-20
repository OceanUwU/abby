package oceanabby.cards;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

import basemod.ReflectionHacks;

public class ChemicalWarfare extends AbstractAbbyCard {
    public final static String ID = makeID("ChemicalWarfare");

    public ChemicalWarfare() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(7);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!evod)
            applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
                public void atStartOfTurn() {
                    atb(new RemoveSpecificPowerAction(owner, owner, this));
                }

                public void updateDescription() {
                    description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
                }
            });
        else
            applyToSelf(new LambdaPower(ID + "Evo", exDesc, exDesc[4], PowerType.BUFF, false, p, magicNumber) {
                public void atStartOfTurn() {
                    atb(new RemoveSpecificPowerAction(owner, owner, this));
                }

                public void updateDescription() {
                    description = strings[5] + amount + strings[amount == 1 ? 2 : 3];
                }
            });
    }

    @SpirePatch(clz=PotionPopUp.class, method="updateTargetMode")
    public static class OnThrow {
        @SpireInsertPatch(rloc=26)
        public static void Insert(PotionPopUp __instance) {
            if (adp().hasPower(ID) || adp().hasPower(ID + "Evo")) {
                AbstractPotion potion = ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "potion");
                AbstractMonster m = ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "hoveredMonster");
                if (adp().hasPower(ID))
                    adp().getPower(ID).flash();
                if (adp().hasPower(ID + "Evo"))
                    adp().getPower(ID + "Evo").flash();
                for (int i = 0; i < pwrAmt(adp(), ID) + pwrAmt(adp(), ID + "Evo"); i++)
                    potion.use(m == null ? null : m);
                if (adp().hasPower(ID))
                    att(new RemoveSpecificPowerAction(adp(), adp(), ID));
            }
        }
    }

    @SpirePatch(clz=PotionPopUp.class, method="updateInput")
    public static class OnDrink {
        @SpireInsertPatch(rloc=20)
        public static void Insert(PotionPopUp __instance) {
            if (adp().hasPower(ID) || adp().hasPower(ID + "Evo")) {
                AbstractPotion potion = ReflectionHacks.getPrivate(__instance, PotionPopUp.class, "potion");
                if (adp().hasPower(ID))
                    adp().getPower(ID).flash();
                if (adp().hasPower(ID + "Evo"))
                    adp().getPower(ID + "Evo").flash();
                for (int i = 0; i < pwrAmt(adp(), ID) + pwrAmt(adp(), ID + "Evo"); i++)
                    potion.use(null);
                if (adp().hasPower(ID))
                    att(new RemoveSpecificPowerAction(adp(), adp(), ID));
            }
        }
    }
}