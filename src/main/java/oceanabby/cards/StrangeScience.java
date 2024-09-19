package oceanabby.cards;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class StrangeScience extends AbstractAbbyCard {
    public final static String ID = makeID("StrangeScience");

    public StrangeScience() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(2, +1);
        setSecondMagic(1);
        setThirdMagic(1);
        cardsToPreview = new Robotization();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new IncreaseMaxOrbAction(secondMagic));
        if (evod)
            for (int i = 0; i < thirdMagic; i++)
                atb(new ChannelAction(new Frost()));
        addAptation();
    }
}