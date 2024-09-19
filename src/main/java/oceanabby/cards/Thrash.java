package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Adaptations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Thrash extends AbstractAbbyCard {
    public final static String ID = makeID("Thrash");

    public Thrash() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(25, +8);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (evod)
            actB(() -> {
                if (Adaptations.adaptations.size() > 0) {
                    AbstractAdaptation a = Adaptations.adaptations.get(0);
                    a.counter -= secondMagic;
                    dmgTop(m, AttackEffect.SLASH_HEAVY);
                    if (a.counter <= 0)
                        Adaptations.remove(a);
                }
            });
        else
            actB(() -> {
                if (Adaptations.adaptations.size() > 0) {
                    dmgTop(m, AttackEffect.SLASH_HEAVY);
                    Adaptations.remove(Adaptations.adaptations.get(0));
                }
            });
    }
}