package oceanabby.cards;

import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.mechanics.Mutations;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

import basemod.helpers.BaseModCardTags;

public class HarbingerForm extends AbstractAbbyCard {
    public final static String ID = makeID("HarbingerForm");

    public HarbingerForm() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        tags.add(BaseModCardTags.FORM);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, 1) {
            @Override public void onUseCard(AbstractCard c, UseCardAction action) {
                if (Mutations.isMutated(c)) {
                    flash();
                    for (int i = 0; i < amount; i++)
                        att(Mutations.action(c));
                }
            }

            public void updateDescription() {
                description = strings[1] + amount + strings[amount == 1 ? 2 : 3];
            }
        });
        if (upgraded)
            applyToSelf(new LambdaPower(ID + "Up", exDesc, exDesc[4], PowerType.BUFF, false, p, -1) {
                @Override public void onPlayCard(AbstractCard c, AbstractMonster m) {
                    if (Mutations.isMutated(c)) {
                        flash();
                        c.shuffleBackIntoDrawPile = true;
                    }
                }

                public void updateDescription() {
                    description = strings[5];
                }
            });
    }
}