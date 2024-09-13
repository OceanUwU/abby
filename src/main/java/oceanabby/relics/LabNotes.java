package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import oceanabby.cards.AbstractAbbyCard;
import oceanabby.characters.TheAberrant;
import oceanabby.mechanics.Evo;

public class LabNotes extends AbstractAbbyRelic {
    public static final String ID = makeID("LabNotes");

    public LabNotes() {
        super(ID, RelicTier.COMMON, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }
  
    public void onEquip() {
        ArrayList<AbstractCard> evoableCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
        if (c instanceof AbstractAbbyCard && !((AbstractAbbyCard)c).evod)
            evoableCards.add(c); 
        } 
        Collections.shuffle(evoableCards, new Random(AbstractDungeon.miscRng.randomLong()));
        if (!evoableCards.isEmpty()) {}
        if (evoableCards.size() == 1) {
            Evo.evo(evoableCards.get(0));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)evoableCards
                .get(0)).makeStatEquivalentCopy()));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        } else {
            Evo.evo(evoableCards.get(0));
            Evo.evo(evoableCards.get(1));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)evoableCards
                
                .get(0)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)evoableCards
                
                .get(1)).makeStatEquivalentCopy(), Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
        }  
    }
}
