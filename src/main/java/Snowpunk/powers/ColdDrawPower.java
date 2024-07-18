package Snowpunk.powers;

import Snowpunk.actions.CondenseAction;
import Snowpunk.ui.EvaporatePanel;
import Snowpunk.util.Wiz;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Snowpunk.SnowpunkMod.makeID;

public class ColdDrawPower extends AbstractEasyPower {
    public static final String POWER_ID = makeID(ColdDrawPower.class.getSimpleName());
    public static final String NAME = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).NAME;
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(POWER_ID).DESCRIPTIONS;

    public ColdDrawPower(AbstractCreature owner, int num) {
        super(POWER_ID, NAME, PowerType.BUFF, false, owner, num);
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        int curCondense = 0;
        for (int i = 0; i < amount; i++) {
            if (EvaporatePanel.evaporatePile.group.size() > curCondense) {
                Wiz.atb(new CondenseAction(1));
                curCondense++;
            } else
                Wiz.atb(new DrawCardAction(amount));
        }
        Wiz.atb(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ColdDrawPower(owner, amount);
    }
}
