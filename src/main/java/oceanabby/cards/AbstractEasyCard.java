package oceanabby.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.StSLib;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.PurgeField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.function.Consumer;
import oceanabby.actions.AddAptationAction;
import oceanabby.characters.TheAberrant;
import oceanabby.util.CardArtRoller;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.AbbyMod.makeImagePath;
import static oceanabby.AbbyMod.modID;
import static oceanabby.util.Wiz.*;

public abstract class AbstractEasyCard extends CustomCard {
    protected static String[] sharedStrings = null;
    protected final CardStrings cardStrings;

    public int secondMagic;
    public int baseSecondMagic;
    public boolean upgradedSecondMagic;
    public boolean isSecondMagicModified;

    public int thirdMagic;
    public int baseThirdMagic;
    public boolean upgradedThirdMagic;
    public boolean isThirdMagicModified;

    public int secondDamage;
    public int baseSecondDamage;
    public boolean upgradedSecondDamage;
    public boolean isSecondDamageModified;

    public int secondBlock;
    public int baseSecondBlock;
    public boolean upgradedSecondBlock;
    public boolean isSecondBlockModified;

    public int haunted = -1;
    public int baseHaunted = -1;
    public boolean upgradedHaunted;
    public boolean isHauntedModified;

    private boolean upgradesDamage = false;
    private int damageUpgrade;
    private boolean upgradesBlock = false;
    private int blockUpgrade;
    private boolean upgradesMagic = false;
    private int magicUpgrade;
    private boolean upgradesSecondMagic = false;
    private int secondMagicUpgrade;
    private boolean upgradesThirdMagic = false;
    private int thirdMagicUpgrade;
    private boolean upgradesSecondDamage = false;
    private int secondDamageUpgrade;
    private boolean upgradesSecondBlock = false;
    private int secondBlockUpgrade;
    private boolean upgradesHaunted = false;
    private int hauntedUpgrade;
    private boolean upgradesCost = false;
    private int costUpgrade;
    private boolean upgradesExhaust = false;
    private boolean upgradedExhaust;
    private boolean upgradesEthereal = false;
    private boolean upgradedEthereal;
    private boolean upgradesInnate = false;
    private boolean upgradedInnate;
    private boolean upgradesRetain = false;
    private boolean upgradedRetain;

    private boolean needsArtRefresh = false;
    private boolean needsDescRefresh = true;

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target) {
        this(cardID, cost, type, rarity, target, TheAberrant.Enums.ABERRANT_COLOUR);
    }

    public AbstractEasyCard(final String cardID, final int cost, final CardType type, final CardRarity rarity, final CardTarget target, final CardColor color) {
        super(cardID, "", getCardTextureString(cardID.replace(modID + ":", ""), type),
                cost, "", type, color, rarity, target);
        cardStrings = CardCrawlGame.languagePack.getCardStrings(this.cardID);
        if (sharedStrings == null)
            sharedStrings = CardCrawlGame.languagePack.getCardStrings(makeID("Card")).EXTENDED_DESCRIPTION;
        rawDescription = cardStrings.DESCRIPTION;
        name = originalName = cardStrings.NAME;
        initializeTitle();
        initializeDescription();

        if (textureImg.contains("ui/missing.png")) {
            if (CardLibrary.cards != null && !CardLibrary.cards.isEmpty()) {
                CardArtRoller.computeCard(this);
            } else
                needsArtRefresh = true;
        }
    }

    @Override public void initializeDescription() {
        if (cardStrings != null) {
            rawDescription = cardStrings.DESCRIPTION;
            if (haunted >= 0)
                rawDescription = sharedStrings[0] + rawDescription;
            if (isEthereal)
                rawDescription = sharedStrings[2] + rawDescription;
            if (isInnate)
                rawDescription = sharedStrings[4] + rawDescription;
            if (selfRetain)
                rawDescription = sharedStrings[3] + rawDescription;
            if (exhaust)
                rawDescription += sharedStrings[1];
        }
        super.initializeDescription();
        if (cardsToPreview != null)
            for (String keyword : cardsToPreview.keywords)
                if (!keywords.contains(keyword))
                    keywords.add(keyword);
    }

    @Override
    protected Texture getPortraitImage() {
        if (textureImg.contains("ui/missing.png")) {
            return CardArtRoller.getPortraitTexture(this);
        } else {
            return super.getPortraitImage();
        }
    }

    public static String getCardTextureString(final String cardName, final AbstractCard.CardType cardType) {
        String textureString;

        switch (cardType) {
            case ATTACK:
            case POWER:
            case SKILL:
                textureString = makeImagePath("cards/" + cardName + ".png");
                break;
            default:
                textureString = makeImagePath("ui/missing.png");
                break;
        }

        FileHandle h = Gdx.files.internal(textureString);
        if (!h.exists()) {
            textureString = makeImagePath("ui/missing.png");
        }
        return textureString;
    }

    @Override
    public void applyPowers() {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.applyPowers();

            secondDamage = damage;
            baseDamage = tmp;

            super.applyPowers();

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.applyPowers();
    }

    @Override
    protected void applyPowersToBlock() {
        if (baseSecondBlock > -1) {
            secondBlock = baseSecondBlock;

            int tmp = baseBlock;
            baseBlock = baseSecondBlock;

            super.applyPowersToBlock();

            secondBlock = block;
            baseBlock = tmp;

            super.applyPowersToBlock();

            isSecondBlockModified = (secondBlock != baseSecondBlock);
        } else super.applyPowersToBlock();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        if (baseSecondDamage > -1) {
            secondDamage = baseSecondDamage;

            int tmp = baseDamage;
            baseDamage = baseSecondDamage;

            super.calculateCardDamage(mo);

            secondDamage = damage;
            baseDamage = tmp;

            super.calculateCardDamage(mo);

            isSecondDamageModified = (secondDamage != baseSecondDamage);
        } else super.calculateCardDamage(mo);
    }

    public void resetAttributes() {
        super.resetAttributes();
        secondMagic = baseSecondMagic;
        isSecondMagicModified = false;
        thirdMagic = baseThirdMagic;
        isThirdMagicModified = false;
        secondDamage = baseSecondDamage;
        isSecondDamageModified = false;
        secondBlock = baseSecondBlock;
        isSecondBlockModified = false;
        haunted = baseHaunted;
        isHauntedModified = false;
    }

    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedSecondMagic) {
            secondMagic = baseSecondMagic;
            isSecondMagicModified = true;
        }
        if (upgradedThirdMagic) {
            thirdMagic = baseThirdMagic;
            isThirdMagicModified = true;
        }
        if (upgradedSecondDamage) {
            secondDamage = baseSecondDamage;
            isSecondDamageModified = true;
        }
        if (upgradedSecondBlock) {
            secondBlock = baseSecondBlock;
            isSecondBlockModified = true;
        }
    }

    protected void upgradeSecondMagic(int amount) {
        baseSecondMagic += amount;
        secondMagic = baseSecondMagic;
        upgradedSecondMagic = true;
    }

    protected void upgradeThirdMagic(int amount) {
        baseThirdMagic += amount;
        thirdMagic = baseThirdMagic;
        upgradedThirdMagic = true;
    }

    protected void upgradeSecondDamage(int amount) {
        baseSecondDamage += amount;
        secondDamage = baseSecondDamage;
        upgradedSecondDamage = true;
    }

    protected void upgradeSecondBlock(int amount) {
        baseSecondBlock += amount;
        secondBlock = baseSecondBlock;
        upgradedSecondBlock = true;
    }

    protected void upgradeHaunted(int amount) {
        baseHaunted += amount;
        haunted = baseHaunted;
        upgradedHaunted = true;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upp();
            initializeDescription();
        }
    }

    protected void setDamage(int base, int up) {
        setDamage(base);
        upgradesDamage = true;
        damageUpgrade = up;
    }

    protected void setDamage(int base) {
        baseDamage = base; 
    }

    protected void setBlock(int base, int up) {
        setBlock(base);
        upgradesBlock = true;
        blockUpgrade = up;
    }

    protected void setBlock(int base) {
        baseBlock = base; 
    }

    protected void setMagic(int base, int up) {
        setMagic(base);
        upgradesMagic = true;
        magicUpgrade = up;
    }

    protected void setMagic(int base) {
        baseMagicNumber = magicNumber = base; 
    }

    protected void setSecondMagic(int base, int up) {
        setSecondMagic(base);
        upgradesSecondMagic = true;
        secondMagicUpgrade = up;
    }

    protected void setSecondMagic(int base) {
        baseSecondMagic = secondMagic = base; 
    }

    protected void setThirdMagic(int base, int up) {
        setThirdMagic(base);
        upgradesThirdMagic = true;
        thirdMagicUpgrade = up;
    }

    protected void setThirdMagic(int base) {
        baseThirdMagic = thirdMagic = base; 
    }

    protected void setSecondDamage(int base, int up) {
        setSecondDamage(base);
        upgradesSecondDamage = true;
        secondDamageUpgrade = up;
    }

    protected void setSecondDamage(int base) {
        baseSecondDamage = secondDamage = base; 
    }

    protected void setSecondBlock(int base, int up) {
        setSecondDamage(base);
        upgradesSecondBlock = true;
        secondBlockUpgrade = up;
    }

    protected void setSecondBlock(int base) {
        baseSecondBlock = secondBlock = base; 
    }

    protected void setExhaust(boolean exhausts, boolean exhaustsWhenUpgraded) {
        setExhaust(exhausts);
        upgradesExhaust = true;
        upgradedExhaust = exhaustsWhenUpgraded;
    }

    protected void setExhaust(boolean exhausts) {
        exhaust = exhausts;
    }

    protected void setEthereal(boolean ethereal, boolean etherealWhenUpgraded) {
        setEthereal(ethereal);
        upgradesEthereal = true;
        upgradedEthereal = etherealWhenUpgraded;
    }

    protected void setEthereal(boolean exhausts) {
        isEthereal = exhausts;
    }

    protected void setHaunted(int haunted, int up) {
        setHaunted(haunted);
        upgradesHaunted = true;
        hauntedUpgrade = up;
    }

    protected void setHaunted(int haunted) {
        baseHaunted = this.haunted = haunted;
    }

    protected void setInnate(boolean innate, boolean innateWhenUpgraded) {
        setInnate(innate);
        upgradesInnate = true;
        upgradedInnate = innateWhenUpgraded;
    }

    protected void setInnate(boolean innate) {
        isInnate = innate;
    }

    protected void setRetain(boolean retains, boolean retainsWhenUpgraded) {
        setRetain(retains);
        upgradesRetain = true;
        upgradedRetain = retainsWhenUpgraded;
    }

    protected void setRetain(boolean retains) {
        selfRetain = retains;
    }

    protected void setUpgradedCost(int newCost) {
        upgradesCost = true;
        costUpgrade = newCost;
    }

    public void upp() {
        if (upgradesDamage)
            upgradeDamage(damageUpgrade);
        if (upgradesBlock)
            upgradeBlock(blockUpgrade);
        if (upgradesMagic)
            upgradeMagicNumber(magicUpgrade);
        if (upgradesSecondMagic)
            upgradeSecondMagic(secondMagicUpgrade);
        if (upgradesThirdMagic)
            upgradeThirdMagic(thirdMagicUpgrade);
        if (upgradesSecondDamage)
            upgradeSecondDamage(secondDamageUpgrade);
        if (upgradesSecondBlock)
            upgradeSecondBlock(secondBlockUpgrade);
        if (upgradesCost)
            upgradeBaseCost(costUpgrade);
        if (upgradesExhaust)
            exhaust = upgradedExhaust;
        if (upgradesEthereal)
            isEthereal = upgradedEthereal;
        if (upgradesHaunted)
            upgradeHaunted(hauntedUpgrade);
        if (upgradesInnate)
            isInnate = upgradedInnate;
        if (upgradesRetain)
            selfRetain = upgradedRetain;
    };

    public void update() {
        super.update();
        if (needsDescRefresh) {
            initializeDescription();
            needsDescRefresh = false;
        }
        if (needsArtRefresh)
            CardArtRoller.computeCard(this);
    }

    public AbstractCard makeStatEquivalentCopy() {
        AbstractEasyCard c = (AbstractEasyCard)super.makeStatEquivalentCopy();
        c.baseSecondDamage = c.secondDamage = baseSecondDamage;
        c.baseSecondBlock = c.secondBlock = baseSecondBlock;
        c.baseSecondMagic = c.secondMagic = baseSecondMagic;
        c.baseThirdMagic = c.thirdMagic = baseThirdMagic;
        return c;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        super.triggerOnEndOfPlayerTurn();
        if (haunted >= 0)
            att(actionify(() -> PurgeField.purge.set(this, true)),
                new ExhaustSpecificCardAction(this, adp().hand),
                actionify(() -> {
                    adp().exhaustPile.removeCard(this);
                    AbstractCard c = StSLib.getMasterDeckEquivalent(this);
                    if (c != null)
                        AbstractDungeon.player.masterDeck.removeCard(c);
                }),
                new LoseHPAction(adp(), adp(), haunted));
    }

    // These shortcuts are specifically for cards. All other shortcuts that aren't specifically for cards can go in Wiz.
    protected void dmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    protected void dmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
    }

    private AbstractGameAction dmgRandomAction(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget, Consumer<AbstractMonster> effectBefore) {
        return actionify(() -> {
            AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
            if (target != null) {
                calculateCardDamage(target);
                if (extraEffectToTarget != null)
                    extraEffectToTarget.accept(target);
                att(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, damageTypeForTurn), fx));
                if (effectBefore != null)
                    effectBefore.accept(target);
            }
        });
    }

    protected void dmgRandom(AbstractGameAction.AttackEffect fx) {
        dmgRandom(fx, null, null);
    }

    protected void dmgRandom(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget, Consumer<AbstractMonster> effectBefore) {
        atb(dmgRandomAction(fx, extraEffectToTarget, effectBefore));
    }

    protected void dmgRandomTop(AbstractGameAction.AttackEffect fx) {
        dmgRandomTop(fx, null, null);
    }

    protected void dmgRandomTop(AbstractGameAction.AttackEffect fx, Consumer<AbstractMonster> extraEffectToTarget, Consumer<AbstractMonster> effectBefore) {
        att(dmgRandomAction(fx, extraEffectToTarget, effectBefore));
    }

    protected void allDmg(AbstractGameAction.AttackEffect fx) {
        atb(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void allDmgTop(AbstractGameAction.AttackEffect fx) {
        att(new DamageAllEnemiesAction(AbstractDungeon.player, multiDamage, damageTypeForTurn, fx));
    }

    protected void altDmg(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        atb(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void altDmgTop(AbstractMonster m, AbstractGameAction.AttackEffect fx) {
        att(new DamageAction(m, new DamageInfo(AbstractDungeon.player, secondDamage, damageTypeForTurn), fx));
    }

    protected void blck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void blckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, block));
    }

    protected void altBlck() {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, secondBlock));
    }

    protected void altBlckTop() {
        att(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, secondBlock));
    }

    protected void addAptation() {
        if (cardsToPreview instanceof AbstractAdaptation)
            atb(new AddAptationAction((AbstractAdaptation)cardsToPreview, magicNumber));
    }

    public String cardArtCopy() {
        return null;
    }

    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return null;
    }

    protected void upMagic(int x) {
        upgradeMagicNumber(x);
    }

    protected void upSecondMagic(int x) {
        upgradeSecondMagic(x);
    }

    protected void upThirdMagic(int x) {
        upgradeThirdMagic(x);
    }

    protected void upSecondDamage(int x) {
        upgradeSecondDamage(x);
    }

    protected void upSecondBlock(int x) {
        upgradeSecondBlock(x);
    }
}
