package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Chomp extends AbstractAbbyCard {
    public final static String ID = makeID("Chomp");

    public Chomp() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(7, +3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyPowers();
        actB(() -> {
            for (int i = 0; i < magicNumber; i++) {
                dmgTop(m, AttackEffect.NONE);
                if (m != null)
                    vfxTop(new BiteEffect(m.hb.cX, m.hb.cY - 40.0F * Settings.scale, Settings.GOLD_COLOR.cpy()), 0.1F);
            }
            baseMagicNumber = magicNumber = -1;
        });
    }
  
    @Override public void applyPowers() {
        baseMagicNumber = magicNumber = (int)adp().hand.group.stream().filter(c -> c != this && (!c.upgraded || (evod && !Evo.Field.evod.get(c)))).count();
        super.applyPowers();
    }
}