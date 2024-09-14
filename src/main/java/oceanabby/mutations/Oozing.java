package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.util.Wiz.*;

public class Oozing extends AbstractMutation {
    public Oozing() {
        super("Oozing", true, 10);
    }

    protected int getPower() {
        return 5;
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        actB(() -> {
            AbstractMonster target2 = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (target2 != null)
                att(new DamageAction(target2, new DamageInfo(AbstractDungeon.player, getPower(), DamageType.HP_LOSS), AbstractGameAction.AttackEffect.POISON));
        });
    }
}