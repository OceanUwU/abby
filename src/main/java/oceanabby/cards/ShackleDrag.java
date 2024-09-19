package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.colorless.DarkShackles;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ShackleDrag extends AbstractAbbyCard {
    public final static String ID = makeID("ShackleDrag");

    public ShackleDrag() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setMagic(2, +1);
        setSecondMagic(1);
        setThirdMagic(2);
        cardsToPreview = new DarkShackles();
        Evo.evo(cardsToPreview);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToEnemy(m, new WeakPower(m, magicNumber, false));
        if (evod) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters)
                addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)p, (AbstractPower)new StrengthPower((AbstractCreature)mo, -thirdMagic), -thirdMagic, true, AbstractGameAction.AttackEffect.NONE)); 
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!mo.hasPower("Artifact"))
                    addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)p, (AbstractPower)new GainStrengthPower((AbstractCreature)mo, thirdMagic), thirdMagic, true, AbstractGameAction.AttackEffect.NONE)); 
            } 
        }
        shuffleIn(cardsToPreview, secondMagic);
    }
}