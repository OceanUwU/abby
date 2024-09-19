package oceanabby.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Adaptations;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Mutate extends AbstractAbbyCard {
    public final static String ID = makeID("Mutate");

    public Mutate() {
        super(ID, -1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            for (AbstractAdaptation a : Adaptations.adaptations) {
                if (!a.upgraded)
                    a.superFlash();
                a.upgrade();
            }
        });
        if (upgraded)
            actB(() -> {
                for (AbstractAdaptation a : Adaptations.adaptations) {
                    if (!Evo.Field.evod.get(a))
                        a.superFlash();
                    Evo.evo(a);
                }
            });
        if (evod)
            actB(() -> {
                for (AbstractAdaptation a : Adaptations.adaptations) {
                    a.superFlash();
                    a.counter += magicNumber;
                }
            });
    }
}