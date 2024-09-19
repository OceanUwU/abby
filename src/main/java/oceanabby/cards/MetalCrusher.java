package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;

public class MetalCrusher extends AbstractAbbyCard {
    public final static String ID = makeID("MetalCrusher");

    public MetalCrusher() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(6, +2);
        setMagic(2, +1);
        setSecondMagic(2);
        cardsToPreview = new Razorspine();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.SLASH_DIAGONAL);
        addAptation();
    }

    @Override public void evo() {
        cardsToPreview.baseMagicNumber += 2;
        cardsToPreview.magicNumber = cardsToPreview.baseMagicNumber;
    }

    @Override public void devo() {
        cardsToPreview.baseMagicNumber -= 2;
        cardsToPreview.magicNumber = cardsToPreview.baseMagicNumber;
    }
}