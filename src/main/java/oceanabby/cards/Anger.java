package oceanabby.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

import basemod.helpers.CardModifierManager;

public class Anger extends AbstractAbbyCard {
    public final static String ID = makeID("Anger");

    public Anger() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(9, +3);
        setMagic(4, +1);
        setSecondMagic(2);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        atb(new SelectCardsInHandAction(exDesc[0], c -> Mutations.isMutated(c), cards -> {
            cards.forEach(c -> Mutations.getMutations(c).forEach(mut -> CardModifierManager.removeSpecificModifier(c, mut, true)));
            if (cards.size() > 0) {
                if (evod)
                    applyToEnemyTop(m, new WeakPower(m, secondMagic, false));
                applyToEnemyTop(m, new StrengthPower(m, -magicNumber));
            }
        }));
    }
}