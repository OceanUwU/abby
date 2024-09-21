package oceanabby.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import oceanabby.actions.FlexibleDiscoveryAction;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class Mimicry extends AbstractAbbyCard {
    public final static String ID = makeID("Mimicry");

    public Mimicry() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setUpgradedCost(0);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        actB(() -> {
            ArrayList<AbstractCard> cards = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                AbstractCard.CardColor color;
                switch (i) {
                    case 0:
                        color = AbstractCard.CardColor.RED;
                        break;
                    case 1:
                        color = AbstractCard.CardColor.GREEN;
                        break;
                    default:
                        color = AbstractCard.CardColor.BLUE;
                        break;
                }
                AbstractCard.CardRarity cardRarity;
                int roll = AbstractDungeon.cardRandomRng.random(99);
                if (roll < 55)
                    cardRarity = AbstractCard.CardRarity.COMMON;
                else if (roll < 85)
                    cardRarity = AbstractCard.CardRarity.UNCOMMON;
                else
                    cardRarity = AbstractCard.CardRarity.RARE;
                Object[] availableCards = CardLibrary.getAllCards().stream().filter(c -> c.color == color && c.rarity == cardRarity && !c.hasTag(CardTags.HEALING)).toArray();
                cards.add(((AbstractCard)availableCards[AbstractDungeon.cardRng.random(availableCards.length - 1)]).makeCopy());
            }
            if (evod)
                for (AbstractCard c : cards)
                    c.upgrade();
            att(new FlexibleDiscoveryAction(cards, true));
        });
    }
}