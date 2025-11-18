package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Tendrils extends AbstractAbbyCard {
    public final static String ID = makeID("Tendrils");

    public Tendrils() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        misc = 4;
        setDamage(misc);
        setMagic(2, +1);
        setExhaust(true);
        setSecondMagic(25);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        if (evod) {
            baseBlock = damage * secondMagic / 100;
            super.applyPowers();
        }
        if (baseBlock == -1)
            block = -1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (!c.uuid.equals(uuid))
                    continue; 
                c.misc += magicNumber;
                c.applyPowers();
                c.baseDamage = c.misc;
                c.isDamageModified = false;
            } 
            for (AbstractCard c : GetAllInBattleInstances.get(uuid)) {
                c.misc += magicNumber;
                c.applyPowers();
                c.baseDamage = c.misc;
            } 
        });
        dmg(m, AttackEffect.SLASH_DIAGONAL);
        if (evod)
            blck();
    }

    @Override
    public void onLoadedMisc() {
        baseDamage = damage = misc;
    }
    
    @Override
    public void devo() {
        setBlock(-1);
    }
}