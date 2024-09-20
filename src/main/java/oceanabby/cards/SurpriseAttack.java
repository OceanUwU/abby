package oceanabby.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class SurpriseAttack extends AbstractAbbyCard {
    public final static String ID = makeID("SurpriseAttack");

    public SurpriseAttack() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        setDamage(3);
        setMagic(1, + 1);
        setSecondMagic(2);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < magicNumber; i++)
            dmg(m, AttackEffect.BLUNT_LIGHT);
        atb(new MoveCardsAction(p.hand, p.discardPile, magicNumber, cards -> cards.forEach(c ->att(Mutations.action(c)))));
    }

    @Override
    public void evo() {
        upgradeSecondMagic(1);
    }

    @Override
    public void devo() {
        upgradeSecondMagic(-1);
    }
}