package oceanabby.mechanics;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.patches.ExtraIconsPatch.ExtraIconsField;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.IconPayload;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import oceanabby.cards.AbstractAbbyCard;
import oceanabby.util.TexLoader;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.AbbyMod.makeImagePath;
import static oceanabby.util.Wiz.*;

import basemod.abstracts.CustomSavable;

public class Evo {
    protected static String[] sharedStrings = null;
    private static Texture icon = TexLoader.getTexture(makeImagePath("ui/evo.png"));

    public static boolean shouldEvo(AbstractCard c) {
        return c.cost > -2;
    }

    public static void evo(AbstractCard c) {
        if (sharedStrings == null)
            sharedStrings = CardCrawlGame.languagePack.getCardStrings(makeID("Evo")).EXTENDED_DESCRIPTION;
        if (Field.evod.get(c))
            return;
        Field.evod.set(c, true);
        if (!ExtraIconsField.extraIcons.get(c).stream().anyMatch(i -> i.getTexture() == icon))
            ExtraIconsField.extraIcons.get(c).add(new IconPayload(ExtraIcons.icon(icon))); //render in SCV
        if (c instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard)c).evod = true;
            ((AbstractAbbyCard)c).evo();
        }
        if (isInCombat())
            c.applyPowers();
        c.initializeDescription();
    }

    public static void devo(AbstractCard c) {
        if (!Field.evod.get(c))
            return;
        Field.evod.set(c, false);
        if (c instanceof AbstractAbbyCard) {
            ((AbstractAbbyCard)c).evod = false;
            ((AbstractAbbyCard)c).devo();
        }
        if (CardCrawlGame.isInARun() && AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT)
            c.applyPowers();
        c.initializeDescription();
    }

    public static AbstractGameAction action(AbstractCard c) {
        return actionify(() -> {
            if (shouldEvo(c) && !Field.evod.get(c))
                c.superFlash();
            evo(c);
        });
    }

    public static class SelectCardInHandToEvo extends AbstractGameAction {
        private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("SelectCardInHandToEvo"));
        public static boolean evoing = false;
        public static boolean upgrading = false;

        private boolean upgrade;
        private Predicate<AbstractCard> available;
        private Consumer<ArrayList<AbstractCard>> callback;
        private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();

        public SelectCardInHandToEvo(boolean upgrade, Predicate<AbstractCard> available) {
            this.upgrade = upgrade;
            this.available = available;
            actionType = ActionType.CARD_MANIPULATION;
            duration = Settings.ACTION_DUR_FAST;
        }

        public SelectCardInHandToEvo(boolean upgrade, Predicate<AbstractCard> available, Consumer<ArrayList<AbstractCard>> callback) {
            this(upgrade, available);
            this.callback = callback;
        }

        @Override
        public void update() {
            if (duration == Settings.ACTION_DUR_FAST) {
                for (AbstractCard c : adp().hand.group)
                    if (!available.test(c))
                        cannotUpgrade.add(c); 
                if (cannotUpgrade.size() == adp().hand.group.size()) {
                    isDone = true;
                    return;
                } 
                if (adp().hand.group.size() - cannotUpgrade.size() == 1)
                    for (AbstractCard c : adp().hand.group) {
                        if (available.test(c)) {
                            evo(c);
                            c.superFlash();
                            if (upgrade && c.canUpgrade())
                                c.upgrade();
                            c.applyPowers();
                            isDone = true;
                            ArrayList<AbstractCard> canUpgrade = new ArrayList<>();
                            for (AbstractCard c2 : adp().hand.group) {
                                if (available.test(c2))
                                    canUpgrade.add(c2);
                            }
                            if (callback != null)
                                callback.accept(canUpgrade);
                            return;
                        }
                    }
                adp().hand.group.removeAll(cannotUpgrade);
                if (adp().hand.group.size() > 1) {
                    AbstractDungeon.handCardSelectScreen.open(uiStrings.TEXT[upgrade ? 1 : 0], 1, false, false, false, true);
                    evoing = true;
                    upgrading = upgrade;
                    tickDuration();
                    return;
                }
              } 
              if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                    for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                        evo(c);
                        c.superFlash();
                        if (upgrade && c.canUpgrade())
                            c.upgrade();
                        c.applyPowers();
                        adp().hand.addToTop(c);
                    } 
                    returnCards();
                    AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                    if (callback != null)
                        callback.accept(new ArrayList<>(AbstractDungeon.handCardSelectScreen.selectedCards.group));
                    AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
                    isDone = true;
              } 
              tickDuration();
        }
  
        private void returnCards() {
            for (AbstractCard c : cannotUpgrade)
                adp().hand.addToTop(c); 
            adp().hand.refreshHandLayout();
        }

        @SpirePatch(clz=HandCardSelectScreen.class, method="prep")
        public static class Reset {
            public static void Postfix() {
                evoing = false;
                upgrading = false;
            }
        }

        private static void evoIt(HandCardSelectScreen __instance) {
            if (evoing) {
                __instance.upgradePreviewCard = __instance.selectedCards.group.get(0).makeStatEquivalentCopy();
                evo(__instance.upgradePreviewCard);
                if (upgrading) {
                    __instance.upgradePreviewCard.upgrade();
                    __instance.upgradePreviewCard.displayUpgrades();
                }
            }
        }

        @SpirePatch(clz=HandCardSelectScreen.class, method="selectHoveredCard")
        public static class EvoIt {
            @SpireInsertPatch(rloc=26)
            public static void Insert(HandCardSelectScreen __instance) {
                evoIt(__instance);
            }
        }

        @SpirePatch(clz=HandCardSelectScreen.class, method="updateMessage")
        public static class EvoIt2 {
            @SpireInsertPatch(rloc=31)
            public static void Insert(HandCardSelectScreen __instance) {
                evoIt(__instance);
            }
        }
    }

    @SpirePatch(clz=AbstractCard.class, method=SpirePatch.CLASS)
    public static class Field {
        public static SpireField<Boolean> evod = new SpireField<>(() -> false);
    }

    public static CustomSavable<ArrayList<Boolean>> evoSavable = new CustomSavable<ArrayList<Boolean>>() {
        public void onLoad(ArrayList<Boolean> allEvod) {
            if (allEvod == null) return;
            int i = 0;
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if (i > allEvod.size())
                    return;
                if (allEvod.get(i++))
                    evo(c);
            }
        }

        public ArrayList<Boolean> onSave() {
            ArrayList<Boolean> allEvod = new ArrayList<>();
            for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
                allEvod.add(Field.evod.get(c));
            return allEvod;
        }
    };

    @SpirePatch(clz=AbstractCard.class, method="renderCard")
    public static class RenderIcon {
        public static void Prefix(AbstractCard __instance) {
            if (Field.evod.get(__instance) && !ExtraIconsField.extraIcons.get(__instance).stream().anyMatch(i -> i.getTexture() == icon))
                ExtraIconsField.extraIcons.get(__instance).add(new IconPayload(ExtraIcons.icon(icon)));
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="initializeDescription")
    public static class AddEffectToDesc {
        private static String store;

        @SpireInsertPatch(rloc=14)
        public static void Insert(AbstractCard __instance) {
            if (Field.evod.get(__instance) && !(__instance instanceof AbstractAbbyCard)) {
                store = __instance.rawDescription;
                __instance.rawDescription += sharedStrings[0];
            }
        }

        public static void Postfix(AbstractCard __instance) {
            if (Field.evod.get(__instance) && !(__instance instanceof AbstractAbbyCard)) {
                __instance.rawDescription = store;
            }
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="useCard")
    public static class DoEffect {
        @SpireInsertPatch(rloc=19)
        public static void Insert(AbstractPlayer __instance, AbstractCard c) {
            if (Field.evod.get(c) && !(c instanceof AbstractAbbyCard))
                atb(new DrawCardAction(1));
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="makeStatEquivalentCopy")
    public static class CopyEvo {
        @SpireInsertPatch(rloc=2, localvars = { "card" })
        public static void Insert(AbstractCard __instance, AbstractCard card) {
            if (Field.evod.get(__instance))
                evo(card);
        }
    }
}
