package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class SplayYourGuts extends AbstractAbbyCard {
    public final static String ID = makeID("SplayYourGuts");

    public SplayYourGuts() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(8, +1);
        setMagic(1);
        cardsToPreview = new Marrow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.SLASH_VERTICAL);
        shuffleIn(cardsToPreview, magicNumber);
        if (evod)
            atb(new MakeTempCardInDiscardAction(cardsToPreview, magicNumber));
    }

    @Override
    public void upp() {
        super.upp();
        cardsToPreview.upgrade();
    }
}