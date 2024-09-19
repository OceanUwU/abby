package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mutations.AbstractMutation;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Stalker extends AbstractAbbyCard {
    public final static String ID = makeID("Stalker");

    public Stalker() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        setDamage(9);
        setMagic(6, +3);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AttackEffect.SLASH_HEAVY);
    }

    @Override
    public void onMutate(AbstractMutation mutation) {
        att(new ModifyDamageAction(uuid, magicNumber));
    }

    @Override
    public void evo() {
        upgradeDamage(magicNumber);
    }
}