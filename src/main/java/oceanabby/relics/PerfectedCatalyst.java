package oceanabby.relics;

import static oceanabby.AbbyMod.makeID;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import oceanabby.characters.TheAberrant;
import org.apache.commons.codec.binary.StringUtils;

public class PerfectedCatalyst extends AbstractAbbyRelic {
    public static final String ID = makeID("PerfectedCatalyst");

    public PerfectedCatalyst() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheAberrant.Enums.ABERRANT_COLOUR);
    }
    
    public void obtain() {
        if (AbstractDungeon.player.hasRelic(MutationCatalyst.ID)) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); ++i)
                if (StringUtils.equals(AbstractDungeon.player.relics.get(i).relicId, MutationCatalyst.ID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
        } else
            super.obtain();
    }
  
    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic(MutationCatalyst.ID);
    }
}
