package oceanabby.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import oceanabby.mechanics.Evo;
import oceanabby.mechanics.Mutations;

import static oceanabby.AbbyMod.makeID;
import static oceanabby.util.Wiz.*;

public class ItFollows extends AbstractAbbyCard {
    public final static String ID = makeID("ItFollows");

    public ItFollows() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        setInnate(true);
        setMagic(4);
        setExhaust(true);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new SelectCardsAction(p.drawPile.group, magicNumber, exDesc[evod ? (upgraded ? 3 : 1) : (upgraded ? 2 : 0)], cards -> {
            cards.forEach(c -> {
                Mutations.mutate(c);
                if (upgraded)
                    Evo.evo(c);
                if (evod)
                    c.upgrade();
                att(new DiscardSpecificCardAction(c, p.drawPile));
            });
        }));
    }

    private static class SelectCardsAction extends AbstractGameAction {
        private Consumer<List<AbstractCard>> callback;
        private String text;
        private boolean anyNumber;
        private CardGroup selectGroup;

        @SuppressWarnings("all")
        public SelectCardsAction(ArrayList<AbstractCard> group, int amount, String textForSelect, boolean anyNumber, Predicate<AbstractCard> cardFilter, Consumer<List<AbstractCard>> callback) {
            this.amount = amount;
            this.duration = this.startDuration = Settings.ACTION_DUR_XFAST;
            this.text = textForSelect;
            this.anyNumber = anyNumber;
            this.callback = callback;
            this.selectGroup = new CardGroup(CardGroupType.UNSPECIFIED);
            this.selectGroup.group.addAll((Collection)group.stream().distinct().filter(cardFilter).collect(Collectors.toList()));
        }

        public SelectCardsAction(ArrayList<AbstractCard> group, int amount, String textForSelect, Consumer<List<AbstractCard>> callback) {
            this(group, amount, textForSelect, false, (c) -> {
                return true;
            }, callback);
        }

        public void update() {
            if (this.duration == this.startDuration) {
                if (this.selectGroup.size() == 0 || this.callback == null) {
                    this.isDone = true;
                    return;
                }

                if (this.selectGroup.size() <= this.amount && !this.anyNumber) {
                    this.callback.accept(this.selectGroup.group);
                    this.isDone = true;
                    return;
                }

                AbstractDungeon.gridSelectScreen.open(this.selectGroup, this.amount, this.anyNumber, this.text);
                AbstractDungeon.gridSelectScreen.forClarity = false;
                this.tickDuration();
            }

            if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
                this.callback.accept(AbstractDungeon.gridSelectScreen.selectedCards);
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
                this.isDone = true;
            } else {
                this.tickDuration();
            }
        }
    }
}