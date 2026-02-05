package oceanabby.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.mechanics.Evo;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ESP extends AbstractAbbyCard {
    public final static String ID = makeID("ESP");

    public ESP() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(3, +1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
                if (type == DamageInfo.DamageType.NORMAL && Evo.Field.evod.get(card))
                    return damage + amount; 
                return damage;
            }
  
            @Override public float modifyBlock(float blockAmount, AbstractCard card) {
                if (Evo.Field.evod.get(card))
                    return blockAmount + amount; 
                return modifyBlock(blockAmount);
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[2] + amount + strings[3];
            }
        });
        if (evod)
            actB(() -> p.hand.group.forEach(c -> att(Evo.action(c))));
    }
}