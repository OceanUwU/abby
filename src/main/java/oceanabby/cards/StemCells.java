package oceanabby.cards;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import oceanabby.mechanics.Evo;
import oceanabby.mechanics.Mutations;
import oceanabby.powers.LambdaPower;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class StemCells extends AbstractAbbyCard {
    public final static String ID = makeID("StemCells");

    public StemCells() {
        super(ID, 1, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF);
        setMagic(1);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (!evod)
            applyToSelf(new LambdaPower(ID, exDesc, exDesc[0], PowerType.BUFF, false, p, magicNumber) {
                @Override public void atStartOfTurnPostDraw() {
                    flash();
                    atb(new SelectCardsInHandAction(amount, strings[6], c -> Mutations.canMutate(c), cards -> cards.forEach(c -> Mutations.mutate(c))));
                }

                public void updateDescription() {
                    description = strings[2] + amount + strings[amount == 1 ? 4 : 5];
                }
            });
        else
            applyToSelf(new LambdaPower(ID + "Evo", exDesc, exDesc[1], PowerType.BUFF, false, p, magicNumber) {
                @Override public void atStartOfTurnPostDraw() {
                    flash();
                    atb(new SelectCardsInHandAction(amount, strings[7], c -> Mutations.canMutate(c) || Evo.shouldEvo(c), cards -> cards.forEach(c -> {Mutations.mutate(c); Evo.evo(c);})));
                }

                public void updateDescription() {
                    description = strings[3] + amount + strings[amount == 1 ? 4 : 5];
                }
            });
        if (upgraded) {
            if (!evod)
                atb(new SelectCardsInHandAction(magicNumber, exDesc[6], c -> Mutations.canMutate(c), cards -> cards.forEach(c -> Mutations.mutate(c))));
            else
                atb(new SelectCardsInHandAction(magicNumber, exDesc[7], c -> Mutations.canMutate(c) || Evo.shouldEvo(c), cards -> cards.forEach(c -> {Mutations.mutate(c); Evo.evo(c);})));
        }
    }
}