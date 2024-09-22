package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.stream.Collectors;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class WrithingTentacles extends AbstractAbbyCard {
    public final static String ID = makeID("WrithingTentacles");

    public WrithingTentacles() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(6, +3);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        actB(() -> {
            ArrayList<AbstractCard> cards = new ArrayList<>(adp().hand.group.stream().filter(c -> Mutations.canMutate(c)).collect(Collectors.toList()));
            if (cards.size() > 0)
                att(Mutations.action(cards.get(AbstractDungeon.cardRng.random(cards.size() - 1))));
        });
    }

    @Override
    public void evo() {
        upgradeMagicNumber(1);
    }

    @Override
    public void devo() {
        upgradeMagicNumber(-1);
    }
}