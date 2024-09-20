package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static oceanabby.AbbyMod.makeID;

public class Phantasmagoria extends AbstractAbbyCard {
    public final static String ID = makeID("Phantasmagoria");

    public Phantasmagoria() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(13, +6);
        setHaunted(5, -1);
        setMagic(3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SMASH);
        if (evod && m != null) {
            addToBot((AbstractGameAction)new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber), -magicNumber, true, AbstractGameAction.AttackEffect.NONE)); 
            if (!m.hasPower("Artifact"))
                addToBot((AbstractGameAction)new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber), magicNumber, true, AbstractGameAction.AttackEffect.NONE)); 
        }
    }
}