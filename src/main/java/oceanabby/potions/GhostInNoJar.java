package oceanabby.potions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import oceanabby.AbbyMod;
import oceanabby.cards.AbstractAbbyCard;
import oceanabby.characters.TheAberrant;
import oceanabby.util.TexLoader;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.AbbyMod.makeImagePath;
import static oceanabby.util.Wiz.*;

public class GhostInNoJar extends AbstractAbbyPotion {
    public static String ID = makeID("GhostInNoJar");

    public GhostInNoJar() {
        super(ID, PotionRarity.RARE, PotionSize.GHOST, Color.WHITE, Color.WHITE, null, TheAberrant.Enums.THE_ABERRANT, AbbyMod.characterColor);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", TexLoader.getTexture(makeImagePath("potions/None.png")));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "outlineImg", TexLoader.getTexture(makeImagePath("potions/GhostOutline.png")));
    }

    public int getPotency(int ascensionlevel) {
        return 8;
    }

    public void use(AbstractCreature creature) {
        atb(new SelectCardsInHandAction(100, strings.DESCRIPTIONS[2] + potency + strings.DESCRIPTIONS[3], true, true, c -> c instanceof AbstractAbbyCard, cards -> cards.forEach(c -> {
            ((AbstractAbbyCard)c).baseHaunted = ((AbstractAbbyCard)c).haunted = getPotency();
            c.initializeDescription();
            c.superFlash();
        })));
    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0] + potency + strings.DESCRIPTIONS[1];
    }

    public void addAdditionalTips() {
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("todo")), BaseMod.getKeywordDescription(makeID("todo"))));
    }
}