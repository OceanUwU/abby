package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import basemod.helpers.CardPowerTip;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import oceanabby.cards.ChargedMarrow;
import oceanabby.characters.TheAberrant;

public class EmergencyGuide extends AbstractAbbyRelic {
    public static final String ID = makeID("EmergencyGuide");
    private static int POWER = 2;

    public EmergencyGuide() {
        super(ID, RelicTier.SHOP, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
        tips.add(new CardPowerTip(new ChargedMarrow()));
    }

    public String getUpdatedDescription() {
        if (POWER == 1) return DESCRIPTIONS[0];
        return DESCRIPTIONS[1] + POWER + DESCRIPTIONS[2];
    }
  
    public void atBattleStart() {
        flash();
        addToBot((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
        addToBot((AbstractGameAction)new MakeTempCardInDrawPileAction((AbstractCard)new ChargedMarrow(), POWER, true, true));
    }
}
