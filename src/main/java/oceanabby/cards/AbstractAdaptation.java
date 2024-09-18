package oceanabby.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;
import oceanabby.actions.AddAptationAction;
import oceanabby.mechanics.Adaptations;

import static oceanabby.util.Wiz.*;

public abstract class AbstractAdaptation extends AbstractAbbyCard {
    private static final float unhoveredScale = 0.2f;
    private static final float hoveredScale = 0.75f;
    public float fontScale = 0.7f;
    private static final float xOffset = 132f * Settings.scale;
    private static final float yOffset = -192f * Settings.scale;
    private static final float gapBetween = 80f * Settings.scale;
    public int counter = 0;
    public boolean hovered = false;
    private BobEffect bobEffect = new BobEffect(2f * Settings.scale, 0.75f);

    public AbstractAdaptation(final String cardID) {
        super(cardID, -2, CardType.POWER, CardRarity.SPECIAL, CardTarget.NONE);
        targetDrawScale = unhoveredScale;
    }

    @Override
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard c = super.makeStatEquivalentCopy();
        ((AbstractAdaptation)c).counter = counter;
        ((AbstractAdaptation)c).movePos(Adaptations.adaptations.size(), Adaptations.adaptations.size() + 1);
        c.current_x = c.target_x;
        c.current_y = c.target_y;
        return c;
    }

    @Override
    protected String baseDesc() {
        return sharedStrings[5] + super.baseDesc();
    }

    public void onThrob() {}
    public void onEnd() {}

    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        return damage;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return damage;
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        return damageAmount;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AddAptationAction((AbstractAdaptation)makeStatEquivalentCopy(), 1));
    }

    public void movePos(int slot, int numSlots) {
        target_x = AbstractDungeon.player.drawX + (numSlots > 1 ? (gapBetween * (slot - (numSlots - 1) / 2f)) : 0f);
        target_y = AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2f + (260f + (AbstractDungeon.player.orbs.size() > 0 ? 100f : 0f)) * Settings.scale + bobEffect.y
            + (numSlots > 1 ? (((float)Math.cos((slot - (numSlots - 1) / 2f) / (numSlots - 1)) - 1f) * (float)Math.log(numSlots - 1)) * 180f : 0);
    }

    public void update(int slot, int numSlots) {
        bobEffect.update();
        movePos(slot, numSlots);
        update();
        this.hb.resize(300f * Settings.scale * Math.min(drawScale, unhoveredScale), 420f * Settings.scale * Math.min(drawScale, unhoveredScale));
        updateHoverLogic();
        fontScale = MathHelper.scaleLerpSnap(fontScale, 0.7f);
    }

    @Override
    public void render(SpriteBatch sb) {
        if (hovered)
            TipHelper.renderTipForCard(this, sb, keywords);
        super.render(sb);
        if (counter > 0)
            FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(counter), current_x + xOffset * drawScale, current_y + yOffset * drawScale, Color.WHITE, Math.min(drawScale / unhoveredScale, 1f) * fontScale);
    }

    @Override
    public void hover() {
        if (!Adaptations.adaptations.contains(this)) {
            super.hover();
            return;
        }
        if (!hovered) {
            hovered = true;
            targetDrawScale = hoveredScale;
        }
    }

    @Override
    public void unhover() {
        if (!Adaptations.adaptations.contains(this)) {
            super.unhover();
            return;
        }
        if (hovered) {
            hovered = false;
            targetDrawScale = unhoveredScale;
        }
    }

    protected void pulse(Color setColor, Color altColor) {
        vfxTop(new BetterMiracleEffect(current_x, current_y, 0.5f, setColor, altColor, ""));
    }

    private static class BetterMiracleEffect extends AbstractGameEffect {
        private float x, y, targetScale;
        private Color altColor;
        private String sfxUrl = "HEAL_3";
  
        public BetterMiracleEffect(float x, float y, float targetScale, Color setColor, Color altColor, String setSfx) {
            this.x = x - ImageMaster.CRYSTAL_IMPACT.packedWidth / 2.0F;
            this.y = y - ImageMaster.CRYSTAL_IMPACT.packedHeight / 2.0F;
            this.targetScale = targetScale * Settings.scale;
            startingDuration = 0.7F;
            duration = startingDuration;
            color = setColor.cpy();
            color.a = 0.0F;
            this.altColor = altColor.cpy();
            renderBehind = false;
            sfxUrl = setSfx;
        }
        
        public void update() {
            if (duration == startingDuration && sfxUrl != "")
                CardCrawlGame.sound.playA(this.sfxUrl, 0.5F); 
            duration -= Gdx.graphics.getDeltaTime();
            if (duration > startingDuration / 2.0F)
                color.a = Interpolation.fade.apply(1.0F, 0.01F, duration - startingDuration / 2.0F);
            else
                color.a = Interpolation.fade.apply(0.01F, 1.0F, duration / (startingDuration / 2.0F));
            scale = Interpolation.pow5In.apply(2.4F, 0.3F, duration / startingDuration) * Settings.scale * targetScale;
            if (duration < 0.0F)
                isDone = true; 
        }
        
        public void render(SpriteBatch sb) {
            sb.setBlendFunction(770, 1);
            this.altColor.a = this.color.a;
            sb.setColor(altColor);
            sb.draw(ImageMaster.CRYSTAL_IMPACT, x, y, ImageMaster.CRYSTAL_IMPACT.packedWidth / 2.0F, ImageMaster.CRYSTAL_IMPACT.packedHeight / 2.0F, ImageMaster.CRYSTAL_IMPACT.packedWidth, ImageMaster.CRYSTAL_IMPACT.packedHeight, scale * 1.1F, scale * 1.1F, 0.0F);
            sb.setColor(color);
            sb.draw(ImageMaster.CRYSTAL_IMPACT, x, y, ImageMaster.CRYSTAL_IMPACT.packedWidth / 2.0F, ImageMaster.CRYSTAL_IMPACT.packedHeight / 2.0F, ImageMaster.CRYSTAL_IMPACT.packedWidth, ImageMaster.CRYSTAL_IMPACT.packedHeight, scale * 0.9F, scale * 0.9F, 0.0F);
            sb.setBlendFunction(770, 771);
        }
        
        public void dispose() {}
    }

    @SpirePatch(clz=DamageInfo.class, method="applyPowers")
    public static class AtDamageReceivePatch {
        @SpireInsertPatch(rloc=34, localvars={"tmp"})
        public static void Insert(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp) {
            for (AbstractAdaptation adaptation : Adaptations.adaptations)
                tmp[0] = adaptation.atDamageReceive(tmp[0], __instance.type);
        }
    }

    @SpirePatch(clz=AbstractPlayer.class, method="damage")
    public static class OnAttackedPatch {
        @SpireInsertPatch(rloc=47, localvars={"damageAmount"})
        public static void Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractAdaptation adaptation : Adaptations.adaptations)
                damageAmount[0] = adaptation.onAttacked(info, damageAmount[0]);
        }
    }

    @SpirePatch(clz=AbstractCard.class, method="damage")
    public static class AtDamageGivePatch {
        @SpireInsertPatch(rloc=23, localvars={"tmp"})
        public static void Insert(AbstractCard __instance, @ByRef float[] tmp) {
            for (AbstractAdaptation adaptation : Adaptations.adaptations)
                tmp[0] = adaptation.atDamageGive(tmp[0], __instance.damageTypeForTurn, __instance);
        }
    }
}