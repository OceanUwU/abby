package oceanabby.potions;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import oceanabby.AbbyMod;
import oceanabby.characters.TheAberrant;
import oceanabby.mechanics.Evo;
import oceanabby.util.TexLoader;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.AbbyMod.makeImagePath;
import static oceanabby.util.Wiz.*;

public class RadioactiveSpider extends AbstractAbbyPotion {
    public static String ID = makeID("RadioactiveSpider");

    public RadioactiveSpider() {
        super(ID, PotionRarity.COMMON, PotionSize.GHOST, new Color(0f, 0f, 0f, 1f), Color.WHITE, null, TheAberrant.Enums.THE_ABERRANT, AbbyMod.characterColor);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", TexLoader.getTexture(makeImagePath("potions/RadioactiveSpider.png")));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "outlineImg", TexLoader.getTexture(makeImagePath("potions/RadioactiveSpiderOutline.png")));
    }

    public int getPotency(int ascensionlevel) {
        return 1;
    }

    public void use(AbstractCreature creature) {
        actB(() -> {
            for (AbstractCard c : adp().hand.group)
                att(Evo.action(c));
        });
    }

    public String getDescription() {
        return strings.DESCRIPTIONS[0];
    }

    public void addAdditionalTips() {
        tips.add(new PowerTip(BaseMod.getKeywordTitle(makeID("evo")), BaseMod.getKeywordDescription(makeID("evo"))));
    }
}