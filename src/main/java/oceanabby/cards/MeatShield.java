package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class MeatShield extends AbstractAbbyCard {
    public final static String ID = makeID("MeatShield");

    public MeatShield() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setHaunted(8);
        setBlock(15, +5);
        setMagic(3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        if (evod)
            actB(() -> {
                baseBlock += magicNumber;
                applyPowers();
            });
    }
}