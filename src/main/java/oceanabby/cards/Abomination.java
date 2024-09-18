package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Abomination extends AbstractAbbyCard {
    public final static String ID = makeID("Abomination");

    public Abomination() {
        super(ID, 2, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        setDamage(5);
        setMagic(2);
        cardsToPreview = new Marrow();
        Evo.evo(cardsToPreview);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        for (int i = 0; i < magicNumber; i++) {
            AbstractCard c = cardsToPreview.makeStatEquivalentCopy();
            if (upgraded)
                Mutations.mutate(c);
            shuffleIn(c);
        }
    }

    @Override public void evo() {
        cardsToPreview.upgrade();
    }

    @Override public void devo() {
        cardsToPreview = new Marrow();
        Evo.evo(cardsToPreview);
    }
}