package oceanabby.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Psionics extends AbstractAbbyCard {
    public final static String ID = makeID("Psionics");

    public Psionics() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(5);
        setMagic(4, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (evod) {
            for (int i = 0; i < magicNumber; i++)
                dmgTop(m, AttackEffect.BLUNT_LIGHT);
            queueDevo();
        } else
            atb(new SelectCardsInHandAction(1, exDesc[0], false, false, c -> Evo.Field.evod.get(c), cards -> {
                cards.forEach(c -> Evo.devo(c));
                if (cards.size() > 0)
                    for (int i = 0; i < magicNumber; i++)
                        dmgTop(m, AttackEffect.BLUNT_LIGHT);
            }));
    }
}