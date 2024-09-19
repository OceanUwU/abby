package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;

public class Marrow extends AbstractAbbyCard {
    public final static String ID = makeID("Marrow");

    public Marrow() {
        this(ID);
    }

    public Marrow(String id) {
        super(id, 0, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        setDamage(6, +3);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        if (evod)
            blck();
    }

    @Override
    public void evo() {
        setBlock(3);
    }

    @Override
    public void devo() {
        setBlock(-1);
    }
}