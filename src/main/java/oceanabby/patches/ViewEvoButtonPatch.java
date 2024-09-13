package oceanabby.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import oceanabby.cards.AbstractEasyCard;

import static oceanabby.AbbyMod.makeID;

public class ViewEvoButtonPatch {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ViewEvoButton"));
    public static Hitbox hb = new Hitbox(250f * Settings.scale, 80f * Settings.scale);
    private static boolean isViewingEvo = false;

    static {
        hb.move(Settings.WIDTH / 2f - 630f, 70f * Settings.scale);
    }

    @SpirePatch(clz=SingleCardViewPopup.class, method="close")
    public static class ClosePatch {
        public static void Prefix(SingleCardViewPopup __instance) {
            isViewingEvo = false;
            hb.hovered = false;
            hb.clickStarted = false;
        }
    }

    @SpirePatch(clz=SingleCardViewPopup.class, method="render")
    public static class EvoItPatch {
        @SpireInsertPatch(rloc=7, localvars={ "copy" })
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef AbstractCard[] copy) {
            if (isViewingEvo) {
                if (copy[0] == null)
                    copy[0] = ((AbstractCard)ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card")).makeStatEquivalentCopy();
                AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
                card.baseBlock += 10;
                card.block = card.baseBlock;
            }
        }
    }

    @SpirePatch(clz=SingleCardViewPopup.class, method="updateUpgradePreview")
    public static class UpdatePatch {
        public static void Postfix(SingleCardViewPopup __instance) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (card instanceof AbstractEasyCard) {
                hb.update();
                if (hb.hovered && InputHelper.justClickedLeft)
                    hb.clickStarted = true; 
                if (hb.clicked || CInputActionSet.proceed.isJustPressed()) {
                    CInputActionSet.proceed.unpress();
                    hb.clicked = false;
                    isViewingEvo = !isViewingEvo;
                } 
            }
        }
    }

    @SpirePatch(clz=SingleCardViewPopup.class, method="renderUpgradeViewToggle")
    public static class RenderPatch {
        public static void Prefix(SingleCardViewPopup __instance, SpriteBatch sb) {
            AbstractCard card = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if (card instanceof AbstractEasyCard) {
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.CHECKBOX, hb.cX - 80.0F * Settings.scale - 32.0F, hb.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                FontHelper.renderFont(sb, FontHelper.cardTitleFont, uiStrings.TEXT[0], hb.cX - 45.0F * Settings.scale, hb.cY + 10.0F * Settings.scale, hb.hovered ? Settings.BLUE_TEXT_COLOR : Settings.GOLD_COLOR);
                if (isViewingEvo) {
                    sb.setColor(Color.WHITE);
                    sb.draw(ImageMaster.TICK, hb.cX - 80.0F * Settings.scale - 32.0F, hb.cY - 32.0F, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
                } 
                hb.render(sb);
            }
        }
    }

    @SpirePatch(clz=SingleCardViewPopup.class, method="updateInput")
    public static class PreventClosePatch {
        private static boolean store;
        public static void Prefix(SingleCardViewPopup __instance) {
            Hitbox upgradeHb = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "upgradeHb");
            store = upgradeHb.hovered;
            upgradeHb.hovered = upgradeHb.hovered || hb.hovered;
        }

        public static void Postfix(SingleCardViewPopup __instance) {
            Hitbox upgradeHb = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "upgradeHb");
            upgradeHb.hovered = store;
        }
    }
}