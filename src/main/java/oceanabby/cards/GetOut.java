package oceanabby.cards;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class GetOut extends AbstractAbbyCard {
    public final static String ID = makeID("GetOut");

    public GetOut() {
        super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY);
        setMagic(3, +1);
        setSecondMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (evod)
            forAllMonstersLiving(mo -> applyToEnemy(mo, new VulnerablePower(mo, magicNumber, false)));
        else
            applyToEnemy(m, new VulnerablePower(m, magicNumber, false));
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, secondMagic) {
            @Override public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
                return 0;
            }

            @Override public void onAfterCardPlayed(AbstractCard usedCard) {
                if (usedCard.type == CardType.ATTACK)
                    att(new ReducePowerAction(p, p, this, 1));
            }

            @Override public void updateDescription() {
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
            }
        });
    }

    @Override public void evo() {
        target = CardTarget.ALL_ENEMY;
    }

    @Override public void devo() {
        target = CardTarget.ENEMY;
    }
}