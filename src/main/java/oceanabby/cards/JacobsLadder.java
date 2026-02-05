package oceanabby.cards;

import static oceanabby.AbbyMod.makeID;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class JacobsLadder extends AbstractAbbyCard {
    public final static String ID = makeID("JacobsLadder");

    public JacobsLadder() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setBlock(5, +2);
        setSecondBlock(4);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        if (evod)
            altBlck();
        for (int i = 0; i < magicNumber; i++)
            blck();
        baseMagicNumber = magicNumber = -1;
    }
  
    public void applyPowers() {
        baseMagicNumber = magicNumber = (int)AbstractDungeon.actionManager.cardsPlayedThisTurn.stream().filter(c -> c.type == CardType.ATTACK).count();
        super.applyPowers();
    }
}