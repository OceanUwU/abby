package oceanabby.mutations;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.HandOfGreed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import java.util.ArrayList;
import oceanabby.mechanics.Mutations;

import static oceanabby.util.Wiz.*;

import basemod.BaseMod;

public class Neurotic extends AbstractMutation {
    public Neurotic() {
        super("Neurotic", true, 2);
    }
    
    protected int getPower() {
        return 3;
    }
    
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new AbstractGameAction() {
            private boolean retrieved = false;
            public void update() {
                if (duration == 0f) {
                    duration = Settings.ACTION_DUR_FAST;
                    ArrayList<AbstractCard> cards = new ArrayList<>();
                    while (cards.size() < getPower()) {
                        AbstractCard.CardRarity r;
                        boolean dupe = false;
                        int roll = AbstractDungeon.cardRandomRng.random(99);
                        if (roll < 55) r = AbstractCard.CardRarity.COMMON;
                        else if (roll < 85) r = AbstractCard.CardRarity.UNCOMMON;
                        else r = AbstractCard.CardRarity.RARE;
                        AbstractCard c = CardLibrary.getAnyColorCard(r);
                        if (c.hasTag(AbstractCard.CardTags.HEALING) || c instanceof HandOfGreed)
                            dupe = true;
                        for (AbstractCard check : cards)
                            if (c.cardID.equals(check.cardID))
                                dupe = true;
                        if (!dupe) cards.add(c.makeCopy());
                    }
                    for (AbstractCard c : cards)
                        Mutations.mutate(c);
                    AbstractDungeon.cardRewardScreen.customCombatOpen(cards, CardRewardScreen.TEXT[1], true);
                    tickDuration();
                    return;
                }
                if (!retrieved) {
                    if (AbstractDungeon.cardRewardScreen.discoveryCard != null) {
                        AbstractCard c = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                        c.current_x = -1000f * Settings.scale;
                        if (adp().hand.size() < BaseMod.MAX_HAND_SIZE) AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(c, Settings.WIDTH/2f, Settings.HEIGHT/2f));
                        else AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c, Settings.WIDTH/2f, Settings.HEIGHT/2f));
                        AbstractDungeon.cardRewardScreen.discoveryCard = null;
                    }
                    retrieved = true;
                }
                tickDuration();
                if (duration <= 0f) isDone = true;
            }
        });
        scab(card);
    }
}