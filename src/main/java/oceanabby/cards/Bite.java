package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import oceanabby.powers.Acid;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Bite extends AbstractAbbyCard {
    public final static String ID = makeID("Bite");

    public Bite() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        setDamage(8, +2);
        setMagic(6, +2);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        forAllMonstersLiving(mo -> vfx(new BiteEffect(mo.hb.cX, mo.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR.cpy()), 0));
        atb(new WaitAction(0.1f));
        allDmg(AttackEffect.NONE);
        if (evod)
            forAllMonstersLiving(mo -> applyToEnemy(mo, new VulnerablePower(mo, secondMagic, false)));
        forAllMonstersLiving(mo -> applyToEnemy(mo, new Acid(mo, magicNumber)));
    }
}