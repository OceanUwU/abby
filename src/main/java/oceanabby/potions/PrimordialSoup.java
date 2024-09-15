package oceanabby.potions;

import basemod.BaseMod;
import oceanabby.AbbyMod;
import oceanabby.cards.AbstractAbbyCard;
import oceanabby.characters.TheAberrant;
import oceanabby.mechanics.Mutations;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.screens.CardRewardScreen;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import java.util.ArrayList;
import java.util.Collections;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class PrimordialSoup extends AbstractAbbyPotion {
    public static String ID = makeID("PrimordialSoup");

    public PrimordialSoup() {
        super(ID, PotionRarity.UNCOMMON, PotionSize.BOTTLE, new Color(50/255f, 168/255f, 82/255f, 1f), new Color(121/255f, 50/255f, 168/255f, 1f), null, TheAberrant.Enums.THE_ABERRANT, AbbyMod.characterColor);
    }

    public int getPotency(int ascensionlevel) {
        return 1;
    }

    public void use(AbstractCreature creature) {
        atb(new DiscoverMutatedAction(getPotency()));
    }

    public String getDescription() {
        return potency == 1 ? strings.DESCRIPTIONS[0] : strings.DESCRIPTIONS[1] + potency + strings.DESCRIPTIONS[2];
    }

    public void addAdditionalTips() {
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("mutated")), BaseMod.getKeywordDescription(makeID("mutated"))));
    }

    public static class DiscoverMutatedAction extends AbstractGameAction {
        private boolean retrieveCard = false;
        private int count;

        public DiscoverMutatedAction(int count) {
            actionType = ActionType.CARD_MANIPULATION;
            duration = Settings.ACTION_DUR_FAST;
            this.count = count;
        }

        public void update() {
            if (duration == Settings.ACTION_DUR_FAST) {
                AbstractDungeon.cardRewardScreen.customCombatOpen(generateCardChoices(), CardRewardScreen.TEXT[1], true);
                tickDuration();
            } else {
                if (!retrieveCard) {
                    if (AbstractDungeon.cardRewardScreen.discoveryCard != null)
                        for (int i = 0; i < count; i++) {
                            AbstractCard disCard = AbstractDungeon.cardRewardScreen.discoveryCard.makeStatEquivalentCopy();
                            disCard.setCostForTurn(0);
                            disCard.current_x = -1000.0F * Settings.scale;
                            if (AbstractDungeon.player.hand.size() < 10)
                                AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                            else
                                AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(disCard, (float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        }
                    AbstractDungeon.cardRewardScreen.discoveryCard = null;
                    retrieveCard = true;
                }
                tickDuration();
            }
        }

        private ArrayList<AbstractCard> generateCardChoices() {
            ArrayList<AbstractCard> cardsList = new ArrayList<>();
            ArrayList<AbstractCard> selectionsList = new ArrayList<>();
            for (AbstractCard q : CardLibrary.getAllCards())
                if (q instanceof AbstractAbbyCard && !q.hasTag(AbstractCard.CardTags.HEALING) && !(q.color == AbstractCard.CardColor.COLORLESS || q.color == AbstractCard.CardColor.CURSE || q.rarity == AbstractCard.CardRarity.BASIC || q.rarity == AbstractCard.CardRarity.SPECIAL)) {
                    AbstractCard r = q.makeCopy();
                    cardsList.add(r);
                }
            Collections.shuffle(cardsList, AbstractDungeon.cardRandomRng.random);
            for (int i = 0; i < 3; i++)
                selectionsList.add(cardsList.get(i));
            for (AbstractCard c : selectionsList)
                Mutations.mutate(c);
            return selectionsList;
        }
    }
}