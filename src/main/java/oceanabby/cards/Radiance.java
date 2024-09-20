package oceanabby.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Radiance extends AbstractAbbyCard {
    public final static String ID = makeID("Radiance");

    public Radiance() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        setMagic(2, +1);
        cardsToPreview = new Marrow();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
            @Override public void onAfterCardPlayed(AbstractCard usedCard) {
                if (usedCard instanceof Marrow) {
                    flash();
                    applyToSelf(new LambdaPower(makeID("Bones"), strings, strings[3], PowerType.BUFF, false, adp(), amount) {
                        @Override public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
                            if (card instanceof Marrow)
                                return damage + amount; 
                            return damage;
                        }
            
                        public void updateDescription() {
                            description = strings[4] + amount + strings[5];
                        }
                    });
                }
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[2];
            }
        });
        
    }

    @Override
    public void evo() {
        upgradeBaseCost(1);
    }

    @Override
    public void devo() {
        upgradeBaseCost(2);
    }
}