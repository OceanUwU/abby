package oceanabby.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClawEffect;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Claw extends AbstractAbbyCard {
    public final static String ID = makeID("Claw");

    public Claw() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(3);
        setMagic(1, +1);
        Evo.evo(this);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            vfx(new ClawEffect(m.hb.cX, m.hb.cY, Color.CYAN, Color.WHITE), 0.1F); 
        dmg(m, AttackEffect.NONE);
        if (evod)
            actB(() -> {
                baseDamage += magicNumber;
                applyPowers();
                for (AbstractCard c : AbstractDungeon.player.discardPile.group)
                    if (c.type == CardType.ATTACK && Evo.Field.evod.get(c)) {
                        c.baseDamage += magicNumber;
                        c.applyPowers();
                    }
                for (AbstractCard c : AbstractDungeon.player.drawPile.group)
                    if (c.type == CardType.ATTACK && Evo.Field.evod.get(c)) {
                        c.baseDamage += magicNumber;
                        c.applyPowers();
                    }
                for (AbstractCard c : AbstractDungeon.player.hand.group)
                    if (c.type == CardType.ATTACK && Evo.Field.evod.get(c)) {
                        c.baseDamage += magicNumber;
                        c.applyPowers();
                    }
            });
    }
}