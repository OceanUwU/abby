package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ChargedMarrow extends Marrow {
    public final static String ID = makeID("ChargedMarrow");

    public ChargedMarrow() {
        super(ID);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        super.use(p, m);
        atb(new GainEnergyAction(magicNumber));
    }
}