package oceanabby.patches;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import java.lang.reflect.Field;
import oceanabby.cards.AbstractAdaptation;

public class AdaptationBGPatch {    
	@SpirePatch(clz=AbstractCard.class, method="renderCardBg")
	public static class RenderBgSwitch {
		public static SpireReturn<?> Prefix(AbstractCard __instance, SpriteBatch sb, float xPos, float yPos, Color ___renderColor){
			if (!(__instance instanceof AbstractAdaptation))
				return SpireReturn.Continue();
			CustomCard card = (CustomCard) __instance;
			Texture texture = card.getBackgroundSmallTexture();
			TextureAtlas.AtlasRegion region = new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
            ReflectionHacks.privateMethod(AbstractCard.class, "renderHelper", SpriteBatch.class, Color.class, TextureAtlas.AtlasRegion.class, float.class, float.class).invoke(card, sb, ___renderColor, region, xPos, yPos);
			return SpireReturn.Return(null);
		}
	}

	@SpirePatch(clz=SingleCardViewPopup.class, method="renderCardBack")
	public static class BackgroundTexture {
		public static SpireReturn<?> Prefix(Object __obj_instance, Object sbObject) {
			try {
				SingleCardViewPopup popup = (SingleCardViewPopup) __obj_instance;
				SpriteBatch sb = (SpriteBatch) sbObject;
				Field cardField;
				cardField = popup.getClass().getDeclaredField("card");
				cardField.setAccessible(true);
				AbstractCard card = (AbstractCard) cardField.get(popup);
				if (!(card instanceof AbstractAdaptation))
				    return SpireReturn.Continue();
                sb.draw(((CustomCard) card).getBackgroundLargeTexture(), Settings.WIDTH / 2.0F - 512.0F, Settings.HEIGHT / 2.0F - 512.0F, 512.0F, 512.0F, 1024.0F, 1024.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 1024, 1024, false, false);
                return SpireReturn.Return(null);
			} catch (NoSuchFieldException | SecurityException | IllegalAccessException e) {
				e.printStackTrace();
			}
            return SpireReturn.Continue();
		}
	}
}