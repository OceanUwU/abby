package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import oceanabby.characters.TheAberrant;

public class MeatBun extends AbstractAbbyRelic {
    public static final String ID = makeID("Meatbun");
    private static int POWER = 20;

    public MeatBun() {
        super(ID, RelicTier.UNCOMMON, LandingSound.FLAT, TheAberrant.Enums.ABERRANT_COLOUR);
    }

    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + POWER + DESCRIPTIONS[1];
    }
  
    public void justEnteredRoom(AbstractRoom room) {
    	if (room.getMapSymbol() != null && room.getMapSymbol().equals("?") && room instanceof com.megacrit.cardcrawl.rooms.MonsterRoom) {
            flash();
            addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
            AbstractDungeon.player.heal(POWER);
        } 
    }
    
    public boolean canSpawn() {
        return (Settings.isEndless || AbstractDungeon.floorNum <= 47);
    }
}
