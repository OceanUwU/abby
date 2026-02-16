package oceanabby.ui;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.AbbyMod.makeImagePath;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.FusionHammer;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

import java.util.ArrayList;
import oceanabby.cards.AbstractAbbyCard;
import oceanabby.characters.TheAberrant;
import oceanabby.mechanics.Evo;
import oceanabby.ui.EvoCampfireEffect.EvoingField;


public class EvoCampfireOption extends AbstractCampfireOption {
    private static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getUIString(makeID("EvoCampfireOption")).TEXT;
    private static final Texture TEXTURE = new Texture(makeImagePath("ui/evocampfire.png"));
    private static final Texture DISABLED_TEXTURE = new Texture(makeImagePath("ui/evocampfiredisabled.png"));


    public EvoCampfireOption() {
        label = DESCRIPTIONS[0];
        setUsable();
    }

    private void setUsable() {
        usable = !AbstractDungeon.player.hasRelic(FusionHammer.ID) && getAttunableCards().group.size() > 0;
        description = usable ? DESCRIPTIONS[1] : DESCRIPTIONS[2];
        img = usable ? TEXTURE : DISABLED_TEXTURE;
    }

    public static CardGroup getAttunableCards() {
        CardGroup validCards = new CardGroup(CardGroupType.UNSPECIFIED);
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group)
            if (Evo.shouldEvo(c) && !Evo.isEvod(c) && c instanceof AbstractAbbyCard)
                validCards.group.add(c);
        return validCards;
    }

    public void useOption() {
        setUsable();
        if (usable)
            AbstractDungeon.effectList.add(0, new EvoCampfireEffect());
    }

    @SpirePatch(clz=CampfireUI.class, method="initializeButtons")
    public static class AddOption {
        @SpireInsertPatch(loc=97)
        public static void Insert(CampfireUI __instance) {
            EvoingField.evoing.set(AbstractDungeon.gridSelectScreen, false);
            if (AbstractDungeon.player instanceof TheAberrant)
                ((ArrayList<AbstractCampfireOption>)ReflectionHacks.getPrivate(__instance, CampfireUI.class, "buttons")).add(new EvoCampfireOption());
        }
    }
}