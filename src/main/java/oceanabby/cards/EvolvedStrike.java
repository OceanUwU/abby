package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import oceanabby.mechanics.Evo;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class EvolvedStrike extends AbstractAbbyCard {
    public final static String ID = makeID("EvolvedStrike");

    public EvolvedStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        setDamage(6);
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
        boolean up = upgraded;
        atb(new Evo.SelectCardInHandToEvo(up, c -> up ? !Evo.Field.evod.get(c) || !c.upgraded : !Evo.Field.evod.get(c)));
    }

    public void evo() {
        upgradeDamage(4);
    }

    public void devo() {
        upgradeDamage(-4);
    }
}