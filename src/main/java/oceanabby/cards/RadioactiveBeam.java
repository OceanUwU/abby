package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.powers.Acid;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class RadioactiveBeam extends AbstractAbbyCard {
    public final static String ID = makeID("RadioactiveBeam");

    public RadioactiveBeam() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setMagic(3);
        setDamage(8, +8);
        setSecondMagic(4);
        cardsToPreview = new ToxicGlands();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addAptation();
        dmg(m, AttackEffect.POISON);
        if (evod)
            applyToEnemy(m, new Acid(m, secondMagic));
    }
}