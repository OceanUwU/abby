package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Pounce extends AbstractAbbyCard {
    public final static String ID = makeID("Pounce");

    public Pounce() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(12, +3);
        setMagic(2, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.BLUNT_HEAVY);
        atb(new DrawCardAction(magicNumber), new Evo.SelectCardInHandToEvo(false, c -> DrawCardAction.drawnCards.contains(c), cards -> {
            if (!evod)
                for (AbstractCard c : DrawCardAction.drawnCards)
                    if (!cards.contains(c))
                        att(new DiscardSpecificCardAction(c, p.hand));
        }));
    }

    @Override public String baseDesc() {
        return evod ? exDesc[0] : super.baseDesc();
    }
}