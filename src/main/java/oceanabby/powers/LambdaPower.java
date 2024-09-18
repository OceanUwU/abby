package oceanabby.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class LambdaPower extends AbstractAbbyPower {
    protected String[] strings;

    public LambdaPower(String ID, String[] strings, String name, PowerType powerType, boolean isTurnBased, AbstractCreature owner, int amount) {
        super(ID, name, powerType, isTurnBased, owner, amount);
        this.strings = strings;
    }

    public abstract void updateDescription();
}
